package com.example.back.dto.request;

import com.example.back.model.enums.PreferredLanguage;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDTO {
    private String name;
    private String surname;
    private String patronymic;
    private String department;
    private String position;
    private String login;
    private String password;
    //задано числом нумерация enum с 0
    private PreferredLanguage preferredLanguage;
    private String addInfo;
}
