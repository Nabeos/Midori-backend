package com.midorimart.managementsystem.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.midorimart.managementsystem.entity.User;
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

public interface UserService {
    public Map<String, UserDTOResponse> authenticate(Map<String, UserDTOLoginRequest> userLoginRequestMap)
            throws CustomBadRequestException;

    public Map<String, UserDTOResponse> register(Map<String, UserDTOCreate> userDTOCreateMap) throws CustomBadRequestException;

    public Map<String, UserDTOResponse> getCurrentUser() throws CustomNotFoundException;

    public User getUserLogin();

    public void sendEmail(User user)throws MessagingException, UnsupportedEncodingException;

    public Map<String, UserDTOResponse> updateUser(int id, Map<String, UserDTOUpdate> userUpdateMap);

    public Map<String, UserDTOResponse> forgotPassword(Map<String, UserDTOForgotPassword> userDTOForgotPassword) throws UnsupportedEncodingException, MessagingException;

    public Map<String, UserDTOResponse> verifyForgotPassword(Map<String, UserDTORetypePassword> userDTORetypeMap,
            String verificationCode) throws CustomBadRequestException;
    public Map<String, List<ImageDTOResponse>> uploadImage(MultipartFile[] files) throws IllegalStateException, IOException;

    public Map<String, UserDTOResponse> addNewUser(Map<String, UserDTOCreate> userDTOCreateMap);

    public Map<String, RoleDTOResponse> addNewRole(Map<String, RoleDTOCreate> roleDTOCreateMap);
}
