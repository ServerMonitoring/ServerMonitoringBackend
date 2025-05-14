package com.example.back.dto.response;

import com.example.back.model.Users;
import com.example.back.model.enums.PreferredLanguage;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserResponseDTO {
    private String name;
    private String surname;
    private String patronymic;
    private String department;
    private String position;
    private String login;
    private String preferredLanguage;
    private String addInfo;
    private String jwt;

    public static UserResponseDTO toDTO(Users user) {
        return UserResponseDTO.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .patronymic(user.getPatronymic())
                .department(user.getDepartment())
                .position(user.getPosition())
                .login(user.getLogin())
                .preferredLanguage(
                        user.getPreferredLanguage() != null ? user.getPreferredLanguage().name() : null
                )
                .addInfo(user.getAddInfo())
                .build();
    }
}
