package com.lemomcrab.TribunaldoZodiacoBackend.dto;

import com.lemomcrab.TribunaldoZodiacoBackend.entity.User;
import lombok.Data;

import java.util.ArrayList;


@Data
public class UserSignUpDto {
    private String username;
    private String password;

    public static User toUser(UserSignUpDto dto) {
        return new User(null,dto.username, dto.password, new ArrayList<>());
    }
}
