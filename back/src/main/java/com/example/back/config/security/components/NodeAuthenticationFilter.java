package com.example.back.config.security.components;

import com.example.back.service.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NodeAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME = "X-API-Key";

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {

        String apiKey = request.getHeader(HEADER_NAME);


        if (StringUtils.isEmpty(apiKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (StringUtils.isNotEmpty(apiKey) && SecurityContextHolder.getContext().getAuthentication() == null){
            try {

                if (jwtService.isNodeToken(apiKey)) {
                    Long nodeId = jwtService.extractServerId(apiKey);

                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_NODE"));

                    NodeAuthentication authenticationToken = new NodeAuthentication(
                            nodeId.toString(),
                            authorities
                    );

                    context.setAuthentication(authenticationToken);
                    SecurityContextHolder.setContext(context);
                }
            }catch (Exception e){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid node token");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
