package com.example.back.service.impl;

import com.example.back.dto.request.AuthUserRequestDTO;
import com.example.back.exception.LoginIsMissingException;
import com.example.back.exception.PasswordIsMissingException;
import com.example.back.exception.UserAlreadyExistsException;
import com.example.back.exception.UserNotFoundException;
import com.example.back.model.Users;
import com.example.back.model.enums.Role;
import com.example.back.repository.UserRepository;
import com.example.back.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    @Transactional
    public Users registerUser(AuthUserRequestDTO requestDTO){
        Users user = new Users();


        Optional.ofNullable(requestDTO.getLogin()).ifPresentOrElse(user::setLogin, () -> {throw new LoginIsMissingException("Login is required to register a user");});
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
            userRepository.save(admin);

        }
    }
}
