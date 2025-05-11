package com.example.back.model;

import com.example.back.model.enums.PreferredLanguage;
import com.example.back.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter

public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;
    private String surname;
    private String patronymic;
    private String department;
    private String position;
    private String login;
    private String password;
    private Boolean isActive;
    private PreferredLanguage preferredLanguage;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String addInfo;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Server> servers;
}
