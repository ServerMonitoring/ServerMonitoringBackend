package com.example.back.dto.search;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchCriteria extends BaseSearchCriteria {
    private String name;
    private String surname;
    private String patronymic;
    private String department;
    private String position;
    private String login;
}
