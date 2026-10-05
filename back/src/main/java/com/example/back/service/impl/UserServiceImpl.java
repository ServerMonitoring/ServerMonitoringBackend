package com.example.back.service.impl;

import com.example.back.config.security.components.CustomUserDetails;
import com.example.back.dto.request.AuthUserRequestDTO;
import com.example.back.dto.request.UserUpdateRequestDTO;
import com.example.back.dto.response.UserForAdminResponseDTO;
import com.example.back.dto.response.UserResponseDTO;
import com.example.back.exception.*;
import com.example.back.model.Users;
import com.example.back.model.enums.Role;
import com.example.back.repository.UserRepository;
import com.example.back.service.UserService;
import com.example.back.service.security.CustomUserDetailsService;
import com.example.back.service.security.JwtService;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Optional.ofNullable(updateRequestDTO.getPassword())
                .filter(password -> !password.trim().isEmpty())
                .map(passwordEncoder::encode)
                .ifPresentOrElse(user::setPassword, () -> {
                    throw new PasswordIsMissingException("Password is required to register a user");
                });
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
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        ensureAdminCanBeRemoved(user);
        userRepository.delete(user);
    }

    @Override
    public void deleteUser(Long id){
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        ensureAdminCanBeRemoved(user);
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public UserForAdminResponseDTO updateUserRole(Long id, Role role) {
        if (role == null || role == Role.NODE) {
            throw new RequestArgumentException("Only USER or ADMIN role can be assigned to a user");
        }

        Users user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user.getRole() == Role.ADMIN && role != Role.ADMIN) {
            ensureAdminCanBeRemoved(user);
        }

        user.setRole(role);
        return UserForAdminResponseDTO.toDTO(userRepository.save(user));
    }

    private void ensureAdminCanBeRemoved(Users user) {
        if (user.getRole() == Role.ADMIN && userRepository.countByRole(Role.ADMIN) <= 1) {
            throw new RequestArgumentException("The last administrator cannot be demoted or deleted");
        }
    }

    @Override
    public List<UserForAdminResponseDTO> getAllUsersForAdmin(){
        List<Users> users = userRepository.findAll();
        return users.stream().map(UserForAdminResponseDTO::toDTO).toList();
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


        // Public registration must never be able to choose a privileged role.
        user.setRole(Role.USER);
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
    public void createAdmin(String login, String password) {
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            return;
        }
        if (!userRepository.existsByRole(Role.ADMIN)) {
            Users admin = new Users();
            admin.setLogin(login.trim());
            admin.setPassword(passwordEncoder.encode(password));
            admin.setRole(Role.ADMIN);
            admin.setIsActive(true);
            userRepository.save(admin);

        }
    }
}
