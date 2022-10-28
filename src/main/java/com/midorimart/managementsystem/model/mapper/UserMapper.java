package com.midorimart.managementsystem.model.mapper;

import java.util.Date;

import com.midorimart.managementsystem.entity.User;
import com.midorimart.managementsystem.model.users.UserDTOCreate;
import com.midorimart.managementsystem.model.users.UserDTOResponse;

public class UserMapper {
    public static UserDTOResponse toUserDTOResponse(User user){
        return UserDTOResponse.builder()
        .id(user.getId())
        .email(user.getEmail())
        .fullname(user.getFullname())
        .address(user.getAddress()!=null?user.getAddress():null)
        .phonenumber(user.getPhonenumber())
        .build();
    }

    public static User toUser(UserDTOCreate userDTOCreate) {
        Date now = new Date();
        return User.builder()
        .email(userDTOCreate.getEmail())
        .password(userDTOCreate.getPassword())
        .phonenumber(userDTOCreate.getPhonenumber())
        .fullname(userDTOCreate.getFullname())
        .createdAt(now)
        .updatedAt(now)
        .deleted(0)
        .build();
    }
}
