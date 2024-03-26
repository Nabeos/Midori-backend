package com.midorimart.managementsystem.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.exception.custom.CustomNotFoundException;
import com.midorimart.managementsystem.model.product.dto.ImageDTOResponse;
import com.midorimart.managementsystem.model.role.RoleDTOCreate;
import com.midorimart.managementsystem.model.role.RoleDTOResponse;
import com.midorimart.managementsystem.model.users.UserDTOCreate;
import com.midorimart.managementsystem.model.users.UserDTOFilter;
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
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Tag(name = "User API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get Current User")
    @GetMapping("/v1/user-management/user")
    public Map<String, UserDTOResponse> getCurrentUser() throws CustomNotFoundException {
        return userService.getCurrentUser();
    }

    @Operation(summary = "Get All users for admin")
    @GetMapping("/v1/user-management/users")
    public Map<String, Object> getUsers(
            @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", required = false, defaultValue = "20") Integer limit) {
        UserDTOFilter filter = UserDTOFilter.builder().offset(offset).limit(limit).build();
        return userService.getUsers(filter);
    }

    @Operation(summary = "Get All sellers")
    @GetMapping("/v1/user-management/users/sellers")
    public Map<String, Object> getSellers() {
        return userService.getSellers();
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

    @Operation(summary = "Activate deactive user profile")
    @PutMapping("/user-management/users/{id}/status")
    public Map<String, UserDTOResponse> updateUserStatus(@PathVariable int id) {
        return userService.updateUserStatus(id);
    }

    @Operation(summary = "Forgot password")
    @PostMapping("/v1/user-management/forgot-password")
    public Map<String, UserDTOResponse> forgotPassword(
            @RequestBody Map<String, UserDTOForgotPassword> userDTOForgotPasswordMap)
            throws UnsupportedEncodingException, MessagingException {
        return userService.forgotPassword(userDTOForgotPasswordMap);
    }

    @Operation(summary = "Verify forgot password")
    @GetMapping("/user-management/verify")
    public Map<String, UserDTOResponse> verifyForgotPassword(@RequestParam(name = "code") String verificationCode)
            throws CustomNotFoundException, UnsupportedEncodingException, MessagingException {
        return userService.verifyForgotPassword(verificationCode);
    }

    @Operation(summary = "Change password")
    @PostMapping("/v1/user/changePassword")
    public Map<String, UserDTOResponse> changePassword(@RequestBody Map<String, UserDTORetypePassword> retypeMap)
            throws CustomBadRequestException {
        return userService.changePassword(retypeMap);
    }

    @Operation(summary = "Search user")
    @GetMapping("/v1/user-management/searchUser")
    public Map<String, List<UserDTOResponse>> searchUser(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "offset", defaultValue = "0", required = true) int offset,
            @RequestParam(name = "limit", defaultValue = "20", required = true) int limit) {
        return userService.searchUser(name, offset, limit);
    }
}
