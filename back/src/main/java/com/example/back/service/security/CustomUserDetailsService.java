package com.example.back.service.security;

import com.example.back.config.security.components.CustomUserDetails;
import com.example.back.dto.UserDto;
import com.example.back.exception.UserNotFoundException;
import com.example.back.model.Users;
import com.example.back.repository.UserRepository;
import com.example.back.service.UserService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Users user = userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException("User with login: "+login+" not found"));
        return getUser(Optional.ofNullable(user)).map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(login + " " +
                "not found."));
    }

    private Optional<UserDto> getUser(Optional<Users> user) {
        if(user.isEmpty()) {
            return Optional.empty();
        }

        Users usr = user.get();
        UserDto userDto = new UserDto();


        userDto.setId(usr.getUserId());
        userDto.setLogin(usr.getLogin());
        userDto.setPassword(usr.getPassword());
        userDto.setRole(usr.getRole());

        return Optional.of(userDto);
    }
}
