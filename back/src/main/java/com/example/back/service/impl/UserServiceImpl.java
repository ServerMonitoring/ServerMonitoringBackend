package com.example.back.service.impl;

import com.example.back.config.security.components.CustomUserDetails;
import com.example.back.dto.request.AuthUserRequestDTO;
import com.example.back.dto.request.UserUpdateRequestDTO;
import com.example.back.dto.response.UserResponseDTO;
import com.example.back.exception.LoginIsMissingException;
import com.example.back.exception.PasswordIsMissingException;
import com.example.back.exception.UserAlreadyExistsException;
import com.example.back.exception.UserNotFoundException;
import com.example.back.model.Users;
import com.example.back.model.enums.Role;
import com.example.back.repository.UserRepository;
import com.example.back.service.UserService;
import com.example.back.service.security.CustomUserDetailsService;
import com.example.back.service.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, CustomUserDetailsService customUserDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public Optional<Users> findUserByLogin(String login) {
        Users user = userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException("User with login: "+login+" not found"));
        return Optional.of(user);
    }

    @Override
    public void verifyUserExistenceByLogin(String login){
        try{
            if(userRepository.findByLogin(login).isPresent()){
                throw new UserAlreadyExistsException("User is already exists");
            }
        }catch (UserNotFoundException ignored){
        }
    }

    @Override
    public UserResponseDTO getUser(String token){
        Long id = jwtService.extractId(token);
        Users user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        return UserResponseDTO.toDTO(user);
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(String token, UserUpdateRequestDTO updateRequestDTO){
        Long id = jwtService.extractId(token);
        Users user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));

        boolean loginChanged = updateRequestDTO.getLogin() != null && !user.getLogin().equals(updateRequestDTO.getLogin());

        Optional.ofNullable(updateRequestDTO.getName()).ifPresent(user::setName);
        Optional.ofNullable(updateRequestDTO.getSurname()).ifPresent(user::setSurname);
        Optional.ofNullable(updateRequestDTO.getPatronymic()).ifPresent(user::setPatronymic);
        Optional.ofNullable(updateRequestDTO.getDepartment()).ifPresent(user::setDepartment);
        Optional.ofNullable(updateRequestDTO.getPosition()).ifPresent(user::setPosition);
        Optional.ofNullable(updateRequestDTO.getLogin()).ifPresent(user::setLogin);
        Optional.ofNullable(updateRequestDTO.getPassword()).ifPresent(user::setPassword);
        Optional.ofNullable(updateRequestDTO.getPreferredLanguage()).ifPresent(user::setPreferredLanguage);
        Optional.ofNullable(updateRequestDTO.getAddInfo()).ifPresent(user::setAddInfo);

        Users updatedUser = userRepository.save(user);
        UserResponseDTO userResponseDTO = UserResponseDTO.toDTO(updatedUser);

        String newToken = null;
        if(loginChanged){
            CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(user.getLogin());
            newToken = jwtService.generateToken(userDetails);
            userResponseDTO.setJwt(newToken);
        }
        return userResponseDTO;
    }

    @Override
    public void deleteUser(String token){
        Long id = jwtService.extractId(token);
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Users registerUser(AuthUserRequestDTO requestDTO){
        Users user = new Users();


        Optional.ofNullable(requestDTO.getLogin()).ifPresentOrElse(user::setLogin, () -> {throw new LoginIsMissingException("Login is required to register a user");});
        verifyUserExistenceByLogin(requestDTO.getLogin());
        Optional.ofNullable(requestDTO.getPassword())
                .filter(password -> !password.trim().isEmpty())
                .map(passwordEncoder::encode)
                .ifPresentOrElse(user::setPassword, () -> {
                    throw new PasswordIsMissingException("Password is required to register a user");
                });

        user.setRole(requestDTO.getRole());
        user.setIsActive(true);

        Optional.ofNullable(requestDTO.getName()).ifPresent(user::setName);
        Optional.ofNullable(requestDTO.getSurname()).ifPresent(user::setSurname);
        Optional.ofNullable(requestDTO.getPatronymic()).ifPresent(user::setPatronymic);
        Optional.ofNullable(requestDTO.getDepartment()).ifPresent(user::setDepartment);
        Optional.ofNullable(requestDTO.getPosition()).ifPresent(user::setPosition);
        Optional.ofNullable(requestDTO.getAddInfo()).ifPresent(user::setAddInfo);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void createAdmin() {
        String defaultLogin = "admin";
        String defaultPassword = "admin";

        if (!userRepository.existsByRole(Role.ADMIN)) {
            Users admin = new Users();
            admin.setLogin(defaultLogin);
            admin.setPassword(passwordEncoder.encode(defaultPassword));
            admin.setRole(Role.ADMIN);
            admin.setIsActive(true);
            userRepository.save(admin);

        }
    }
}
