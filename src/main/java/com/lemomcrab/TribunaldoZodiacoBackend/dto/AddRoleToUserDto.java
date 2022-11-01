package com.lemomcrab.TribunaldoZodiacoBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddRoleToUserDto {
    private String email;
    private String roleName;
}
