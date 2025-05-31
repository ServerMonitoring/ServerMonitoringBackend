package com.example.back.dto.response;

import com.example.back.model.Users;
import com.example.back.model.enums.Role;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForAdminResponseDTO {
    private Long userId;
    private String name;
    private String surname;
    private String patronymic;
    private String department;
    private String position;
    private String login;
    private String preferredLanguage;
    private String addInfo;
    private Role role;

    public static UserForAdminResponseDTO toDTO(Users user) {
        return UserForAdminResponseDTO.builder()
                .userId(user.getUserId())
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
                .role(user.getRole())
                .build();
    }
}
