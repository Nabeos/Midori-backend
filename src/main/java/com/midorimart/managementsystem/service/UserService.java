package com.midorimart.managementsystem.service;

import java.util.Map;

import com.midorimart.managementsystem.exception.custom.CustomBadRequestException;
import com.midorimart.managementsystem.model.users.UserDTOCreate;
import com.midorimart.managementsystem.model.users.UserDTOLoginRequest;
import com.midorimart.managementsystem.model.users.UserDTOResponse;

public interface UserService {
    public Map<String, UserDTOResponse> authenticate(Map<String, UserDTOLoginRequest> userLoginRequestMap) throws CustomBadRequestException;

    public Map<String, UserDTOResponse> addNewUser(Map<String, UserDTOCreate> userDTOCreateMap);
}
