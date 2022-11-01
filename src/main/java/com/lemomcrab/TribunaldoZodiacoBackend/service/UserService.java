package com.lemomcrab.TribunaldoZodiacoBackend.service;

import com.lemomcrab.TribunaldoZodiacoBackend.dto.UserSignInDto;
import com.lemomcrab.TribunaldoZodiacoBackend.dto.UserSignUpDto;
import com.lemomcrab.TribunaldoZodiacoBackend.entity.Role;
import com.lemomcrab.TribunaldoZodiacoBackend.entity.User;
import com.lemomcrab.TribunaldoZodiacoBackend.exception.StandardException;
import com.lemomcrab.TribunaldoZodiacoBackend.repository.RoleRepository;
import com.lemomcrab.TribunaldoZodiacoBackend.repository.UserRepository;
import com.lemomcrab.TribunaldoZodiacoBackend.util.BadWordsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Boolean registerUser(UserSignUpDto dto) throws StandardException {
        if (!validateUserDto(dto))
            return false;
        User user = UserSignUpDto.toUser(dto);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new StandardException("Falha ao registrar-se. Por favor, reveja os dados inseridos");
        }
        return true;
    }

    public User saveUser(User user) throws StandardException {
        try {
            log.info("Saving user - username s%".format(user.getUsername()));
            user.setPassword(user.getPassword());
//            user.setPassword(encoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (NullPointerException e) {
            throw new StandardException("Dados de usuario inválidos, cheque a documentação");
        }
    }


    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public void addRoleToUser(String username, String roleName) throws StandardException {
        User user = userRepository.findByUsername(username);

        Role role = roleRepository.findByRolename(roleName);
        if (role == null) {
            throw new StandardException("Rolename inválida");
        }
        user.getRoles().add(role);
        userRepository.save(user);
    }

    private Boolean validateUserDto(UserSignUpDto dto) {
        String username = dto.getUsername().toLowerCase();
        ArrayList<String> badWords = BadWordsUtil.getBadWords();

        for (String badWord : badWords) {
            if (username.equals(badWord.toLowerCase())) {
                return false;
            }
        }
        return true;
    }


    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean login(UserSignInDto dto) throws StandardException {
        User user = getUser(dto.getUsername());
        if(user != null){
            String encodedDtoPassword = dto.getPassword();
            String encodedUserPassword = user.getPassword();
            return encodedDtoPassword.equals(encodedUserPassword);
        }
        else{
             throw new StandardException("Usuario não encontrado");
        }
//        String encodedDtoPassword = encoder.encode(dto.getPassword());
//        String encodedUserPassword = encoder.encode(user.getPassword());

    }
}
