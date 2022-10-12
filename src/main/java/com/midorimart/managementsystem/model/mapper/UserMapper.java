package com.midorimart.managementsystem.model.mapper;

import com.midorimart.managementsystem.entity.User;
import com.midorimart.managementsystem.model.users.UserDTOCreate;
import com.midorimart.managementsystem.model.users.UserDTOResponse;

public class UserMapper {
    public static UserDTOResponse toUserDTOResponse(User user){
        return UserDTOResponse.builder()
        .email(user.getEmail())
        .fullname(user.getFullname())
        .address(user.getAddress())
        .phonenumber(user.getPhonenumber())
        .build();
    }

    public static User toUser(UserDTOCreate userDTOCreate) {
        return User.builder()
        .email(userDTOCreate.getEmail())
        .password(userDTOCreate.getPassword())
        .phonenumber(userDTOCreate.getPhonenumber())
        .fullname(userDTOCreate.getFullname())
        .build();
    }
}
