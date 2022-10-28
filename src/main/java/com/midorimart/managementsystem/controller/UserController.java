package com.midorimart.managementsystem.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.exception.custom.CustomNotFoundException;
import com.midorimart.managementsystem.model.users.UserDTOCreate;
import com.midorimart.managementsystem.model.users.UserDTOLoginRequest;
import com.midorimart.managementsystem.model.users.UserDTOResponse;
import com.midorimart.managementsystem.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user-management")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "User API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get Current User")
    @GetMapping("/user")
    public Map<String, UserDTOResponse> getCurrentUser() throws CustomNotFoundException {
        return userService.getCurrentUser();
    }

    @Operation(summary = "login")
    @PostMapping("/login")
    public Map<String, UserDTOResponse> login(@RequestBody Map<String, UserDTOLoginRequest> userLoginRequestMap)
            throws CustomBadRequestException {
        return userService.authenticate(userLoginRequestMap);
    }

    @Operation(summary = "Add new user")
    @PostMapping("/users")
    public Map<String, UserDTOResponse> addNewUser(@RequestBody Map<String, UserDTOCreate> userDTOCreateMap) {
        return userService.addNewUser(userDTOCreateMap);
    }

}
