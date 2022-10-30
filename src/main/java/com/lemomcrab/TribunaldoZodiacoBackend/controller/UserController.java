package com.lemomcrab.TribunaldoZodiacoBackend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lemomcrab.TribunaldoZodiacoBackend.dto.UserSignUpDto;
import com.lemomcrab.TribunaldoZodiacoBackend.entity.User;
import com.lemomcrab.TribunaldoZodiacoBackend.exception.StandardException;
import com.lemomcrab.TribunaldoZodiacoBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("signup")
    public ResponseEntity register(@RequestBody UserSignUpDto dto) throws StandardException {
        if (userService.registerUser(dto)) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON).build();
        } else return ResponseEntity.badRequest().build();
    }
    @PostMapping("signin")
    public String signIn(HttpServletRequest request, HttpServletResponse response) throws StandardException {
        String username = request.getParameter("username");
        String password =  request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password);
        authenticationManager.authenticate(authenticationToken);
        User user = userService.getUser(username);
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String token = JWT.create()
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()))
                .sign(algorithm);
        return token;
    }

}