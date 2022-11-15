package com.midorimart.managementsystem.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.exception.custom.CustomNotFoundException;
import com.midorimart.managementsystem.model.product.dto.ImageDTOResponse;
import com.midorimart.managementsystem.model.role.RoleDTOCreate;
import com.midorimart.managementsystem.model.role.RoleDTOResponse;
import com.midorimart.managementsystem.model.users.UserDTOCreate;
import com.midorimart.managementsystem.model.users.UserDTOForgotPassword;
import com.midorimart.managementsystem.model.users.UserDTOLoginRequest;
import com.midorimart.managementsystem.model.users.UserDTOResponse;
import com.midorimart.managementsystem.model.users.UserDTORetypePassword;
import com.midorimart.managementsystem.model.users.UserDTOUpdate;
import com.midorimart.managementsystem.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5050")
@RequiredArgsConstructor
@Tag(name = "User API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get Current User")
    @GetMapping("/v1/user-management/user")
    public Map<String, UserDTOResponse> getCurrentUser() throws CustomNotFoundException {
        return userService.getCurrentUser();
    }

    @Operation(summary = "login")
    @PostMapping("/user-management/login")
    public Map<String, UserDTOResponse> login(@RequestBody Map<String, UserDTOLoginRequest> userLoginRequestMap)
            throws CustomBadRequestException {
        return userService.authenticate(userLoginRequestMap);
    }

    @Operation(summary = "Add new user")
    @PostMapping("/user-management/register")
    public Map<String, UserDTOResponse> register(@RequestBody Map<String, UserDTOCreate> userDTOCreateMap)
            throws CustomBadRequestException {
        return userService.register(userDTOCreateMap);
    }

    @Operation(summary = "Add new User for Admin")
    @PostMapping("/v1/user-management/users")
    public Map<String, UserDTOResponse> addNewUser(@RequestBody Map<String, UserDTOCreate> userDTOCreateMap) {
        return userService.addNewUser(userDTOCreateMap);

    }

    @Operation(summary = "Add new Role for Admin")
    @PostMapping("/v1/user-management/roles")
    public Map<String, RoleDTOResponse> addNewRole(@RequestBody Map<String, RoleDTOCreate> roleDTOCreateMap) {
        return userService.addNewRole(roleDTOCreateMap);
    }

    @Operation(summary = "Upload Avatar")
    @PostMapping("/v1/user-management/users/image/upload")
    public Map<String, List<ImageDTOResponse>> uploadImage(MultipartFile[] files)
            throws IllegalStateException, IOException {
        return userService.uploadImage(files);
    }

    @Operation(summary = "Update user profile")
    @PutMapping("/v1/user-management/users/{id}")
    public Map<String, UserDTOResponse> updateUser(@PathVariable int id,
            @RequestBody Map<String, UserDTOUpdate> userUpdateMap) {
        return userService.updateUser(id, userUpdateMap);
    }

    @Operation(summary = "Forgot password")
    @PostMapping("/v1/user-management/forgot-password")
    public Map<String, UserDTOResponse> forgotPassword(@RequestBody Map<String, UserDTOForgotPassword> userDTOForgotPasswordMap) throws UnsupportedEncodingException, MessagingException {
        return userService.forgotPassword(userDTOForgotPasswordMap);
    }

    @Operation(summary = "Verify forgot password")
    @GetMapping("/v1/user-management/verify?code={verificationCode}")
    public Map<String, UserDTOResponse> verifyForgotPassword(@RequestBody Map<String, UserDTORetypePassword> userDTORetypeMap,@PathVariable String verificationCode) throws CustomBadRequestException{
        return userService.verifyForgotPassword(userDTORetypeMap, verificationCode);
    }
}
