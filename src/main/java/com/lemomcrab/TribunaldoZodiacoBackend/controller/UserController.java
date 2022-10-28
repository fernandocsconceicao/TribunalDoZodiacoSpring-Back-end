package com.lemomcrab.TribunaldoZodiacoBackend.controller;

import com.lemomcrab.TribunaldoZodiacoBackend.dto.UserSignUpDto;
import com.lemomcrab.TribunaldoZodiacoBackend.exception.StandardException;
import com.lemomcrab.TribunaldoZodiacoBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("signup")
    public ResponseEntity register(@RequestBody UserSignUpDto dto) throws StandardException {
        if (userService.registerUser(dto)) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON).build();
        } else return ResponseEntity.badRequest().build();
    }
}