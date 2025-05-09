package com.example.back.dto.request;

import com.example.back.model.enums.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AuthUserRequestDTO {

    private String name;
    private String surname;
    private String patronymic;
    private String department;
    private String position;
    private String login;
    private String password;
    private Role role;
    private String addInfo;
}
