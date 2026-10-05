package com.example.back.dto.request;

import com.example.back.model.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleUpdateRequestDTO {
    private Role role;
}
