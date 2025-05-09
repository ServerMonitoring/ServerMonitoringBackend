package com.example.back.service.security;


import com.example.back.config.security.components.CustomUserDetails;
import com.example.back.model.enums.Role;
import com.example.back.model.jwt.JwtData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Value("${token.signing.key}")
    private String jwtSigningKey;
    private SecretKey signingKey;

    @PostConstruct
    public void init(){

        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Извлечение логина пользователя из токена
     *
     * @param token токен
     * @return элек. почта
     */
    public String extractLogin(String token){

        return extractClaim(token,claims ->  claims.get("login", String.class));
    }

    /**
     * Извлечение ID пользователя из токена
     *
     * @param token токен
     * @return ID пользователя
     */
    public Long extractId(String token) {

        return extractClaim(token, claims -> claims.get("id", Long.class));
    }

    /**
     * Извлечение ID сервера из токена
     *
     * @param token токен
     * @return ID сервера
     */
    public Long extractServerId(String token) {
        return extractClaim(token, claims -> claims.get("serverId", Long.class));
    }

    /**
     * Извлечение всех данных JwtData из токена
     *
     * @param token токен
     * @return данные JwtData
     */
    public JwtData extractData(String token){
        return JwtData.builder()
                .id(extractId(token))
                .email(extractLogin(token))
                .role(extractClaim(token, claims -> {
                    String roleStr = claims.get("role", String.class);
                    return roleStr != null ? Role.valueOf(roleStr) : null;
                }))
                .serverId(extractServerId(token))
                .createdDateTime(extractClaim(token, claims -> {
                    String cdts = claims.get("createdDateTime", String.class);
                    return cdts != null ? LocalDateTime.parse(cdts) : null;
                }))
                .build();
    }

    /**
     * Генерация токена
     *
     * @param userDetails данные пользователя
     * @return токен
     */
    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof CustomUserDetails customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("login", customUserDetails.getLogin());
            claims.put("role", customUserDetails.getRole());
            claims.put("createdDateTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
        return generateToken(claims, userDetails);
    }


    /**
     * Генерация токена для ноды
     *
     * @param serverId id сервера
     * @param userId id юзера
     * @return токен
     */
    public String generateNodeToken(Long serverId, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("serverId", serverId);
        claims.put("userId", userId);
        claims.put("role", "NODE");
        return Jwts.builder()
                .claims(claims)
                .subject(serverId.toString())
                .issuedAt(new Date())
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Является нодой
     *
     * @param token токен
     * @return boolean
     */
    public boolean isNodeToken(String token) {
        Role role = extractClaim(token, claims -> {
            String roleStr = claims.get("role", String.class);
            return roleStr != null ? Role.valueOf(roleStr) : null;
        });
        return role == Role.NODE;
    }

    /**
     * Проверка токена на валидность
     *
     * @param token       токен
     * @param userDetails данные пользователя
     * @return true, если токен валиден
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {

        final String userLogin = extractLogin(token);
        return (userLogin.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Извлечение данных из токена
     *
     * @param token           токен
     * @param claimsResolvers функция извлечения данных
     * @param <T>             тип данных
     * @return данные
     */
    private <T> T extractClaim(String token, Function<Claims,T> claimsResolvers) {

        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Генерация токена
     *
     * @param extraClaims дополнительные данные
     * @param userDetails данные пользователя
     * @return токен
     */
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {

        return Jwts.builder().claims(extraClaims).subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(getSigningKey(),Jwts.SIG.HS256).compact();
    }


    /**
     * Проверка токена на просроченность
     *
     * @param token токен
     * @return true, если токен просрочен
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Извлечение даты истечения токена
     *
     * @param token токен
     * @return дата истечения
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Извлечение всех данных из токена
     *
     * @param token токен
     * @return данные
     */
    private Claims extractAllClaims(String token){

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Получение ключа для подписи токена
     *
     * @return ключ
     */
    private SecretKey getSigningKey(){
        return signingKey;
    }
}