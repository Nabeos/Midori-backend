package com.midorimart.managementsystem.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.model.users.UserDTOCreate;
import com.midorimart.managementsystem.model.users.UserDTOLoginRequest;
import com.midorimart.managementsystem.model.users.UserDTOResponse;
import com.midorimart.managementsystem.service.UserService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usermanagement")
@CrossOrigin
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public Map<String, UserDTOResponse> login(@RequestBody Map<String, UserDTOLoginRequest> userLoginRequestMap) throws CustomBadRequestException{
        return userService.authenticate(userLoginRequestMap);
    }
    @PostMapping("/addNewUser")
    public Map<String, UserDTOResponse> addNewUser(@RequestBody Map<String, UserDTOCreate> userDTOCreateMap){
        return userService.addNewUser(userDTOCreateMap);
    }
}
