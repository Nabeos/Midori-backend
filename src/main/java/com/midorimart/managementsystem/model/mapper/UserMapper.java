package com.midorimart.managementsystem.model.mapper;

import java.util.Date;

import com.midorimart.managementsystem.entity.Role;
import com.midorimart.managementsystem.entity.User;
import com.midorimart.managementsystem.model.users.UserDTOCreate;
import com.midorimart.managementsystem.model.users.UserDTOResponse;
import com.midorimart.managementsystem.model.users.UserDTOUpdate;

public class UserMapper {
    //convert from User to UserDTO
    public static UserDTOResponse toUserDTOResponse(User user) {
        return UserDTOResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullname(user.getFullname())
                .thumbnail(user.getThumbnail()!=null?user.getThumbnail().replace("\\", "/"):null)
                .address(user.getAddress() != null ? user.getAddress() : null)
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
                .role(Role.builder().id(2).build())
                .deleted(0)
                .build();
    }

    public static User toUserDTO(UserDTOUpdate userUpdateDTO) {
        Date now = new Date();
        return User.builder().fullname(userUpdateDTO.getFullname()).phonenumber(userUpdateDTO.getPhoneNumber())
                .thumbnail(userUpdateDTO.getThumbnail()).updatedAt(now).build();
    }
}
