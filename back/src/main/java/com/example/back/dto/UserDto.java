package com.example.back.dto;

import com.example.back.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDto{
    private Long id;
    @Size(max = 50,message = "\"email\" field should be no more than 50 characters long")
    @NotBlank(message = "Email cannot be null or empty")
    @Email(message = "Email should be valid")
    private String login;
    private String password;
    private Role role;
    private Boolean isActive = true;
}