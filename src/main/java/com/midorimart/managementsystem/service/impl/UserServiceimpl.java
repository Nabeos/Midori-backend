package com.midorimart.managementsystem.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.midorimart.managementsystem.entity.User;
import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.model.CustomError;
import com.midorimart.managementsystem.model.mapper.UserMapper;
import com.midorimart.managementsystem.model.users.UserDTOCreate;
import com.midorimart.managementsystem.model.users.UserDTOLoginRequest;
import com.midorimart.managementsystem.model.users.UserDTOResponse;
import com.midorimart.managementsystem.repository.UserRepository;
import com.midorimart.managementsystem.service.UserService;
import com.midorimart.managementsystem.util.JwtTokenUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceimpl implements UserService {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Map<String, UserDTOResponse> authenticate(Map<String, UserDTOLoginRequest> userLoginRequestMap) throws CustomBadRequestException{
        UserDTOLoginRequest userDTOLoginRequest = userLoginRequestMap.get("user");

        Optional<User> userOptional=userRepository.findByEmail(userDTOLoginRequest.getEmail());

        boolean isAuthen=false;

        if (userOptional.isPresent()){
            User user=userOptional.get();
            if(passwordEncoder.matches(userDTOLoginRequest.getPassword(), user.getPassword())){
                isAuthen=true;
            }
        }
        if (!isAuthen){
            throw new CustomBadRequestException(
                CustomError.builder().code("400").message("User name and password incorrect").build());
        }
        return buildDTOResponse(userOptional.get());

    }

    @Override
    public Map<String, UserDTOResponse> addNewUser(Map<String, UserDTOCreate> userDTOCreateMap) {
        UserDTOCreate userDTOCreate=userDTOCreateMap.get("user");
        User user=UserMapper.toUser(userDTOCreate);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user=userRepository.save(user);
        return buildDTOResponse(user);

    }

    private Map<String, UserDTOResponse> buildDTOResponse(User user){
        Map<String, UserDTOResponse> wrapper=new HashMap<>();
        UserDTOResponse userDTOResponse=UserMapper.toUserDTOResponse(user);
        userDTOResponse.setToken(jwtTokenUtil.generateToken(user, 24*60*60));
        wrapper.put("user", userDTOResponse);
        return wrapper;
    }
}
