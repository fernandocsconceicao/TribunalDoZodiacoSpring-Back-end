package com.lemomcrab.TribunaldoZodiacoBackend.service;

import com.lemomcrab.TribunaldoZodiacoBackend.dto.UserSignUpDto;
import com.lemomcrab.TribunaldoZodiacoBackend.entity.User;
import com.lemomcrab.TribunaldoZodiacoBackend.exception.StandardException;
import com.lemomcrab.TribunaldoZodiacoBackend.repository.UserRepository;
import com.lemomcrab.TribunaldoZodiacoBackend.util.BadWordsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Boolean registerUser(UserSignUpDto dto) throws StandardException {
        if (!validateUserDto(dto))
            return false;
        User user = UserSignUpDto.toUser(dto);
        try{
            userRepository.save(user);
        }catch(Exception e){
            throw new StandardException("Falha ao registrar-se. Por favor, reveja os dados inseridos");
        }

        return true;
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


}
