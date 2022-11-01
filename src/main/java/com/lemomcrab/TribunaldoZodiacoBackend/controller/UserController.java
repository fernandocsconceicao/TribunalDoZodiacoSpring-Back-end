package com.lemomcrab.TribunaldoZodiacoBackend.controller;

import com.lemomcrab.TribunaldoZodiacoBackend.dto.AddRoleToUserDto;
import com.lemomcrab.TribunaldoZodiacoBackend.dto.UserSignInDto;
import com.lemomcrab.TribunaldoZodiacoBackend.dto.UserSignUpDto;
import com.lemomcrab.TribunaldoZodiacoBackend.entity.Role;
import com.lemomcrab.TribunaldoZodiacoBackend.entity.User;
import com.lemomcrab.TribunaldoZodiacoBackend.exception.StandardException;
import com.lemomcrab.TribunaldoZodiacoBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity register(@RequestBody UserSignUpDto dto) throws StandardException {
        if (userService.registerUser(dto)) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON).build();
        } else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/signin")
    public boolean signIn(@RequestBody UserSignInDto dto) throws StandardException {
        return userService.login(dto);
    }


    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return ResponseEntity.ok().body(userService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<User> addToUser(@RequestBody AddRoleToUserDto dto) throws StandardException {
        userService.addRoleToUser(dto.getEmail(), dto.getRoleName());
        return ResponseEntity.ok().build();
    }

}