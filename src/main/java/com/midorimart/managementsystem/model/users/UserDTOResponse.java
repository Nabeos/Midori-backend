package com.midorimart.managementsystem.model.users;

import com.midorimart.managementsystem.model.address.dto.AddressDTOResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOResponse {
    private int id;
    private int roleId;
    private String email;
    private String fullname;
    private String thumbnail;
    private String phonenumber;
    private AddressDTOResponse address;
    private String token;
    private String verifyStatus;
}
