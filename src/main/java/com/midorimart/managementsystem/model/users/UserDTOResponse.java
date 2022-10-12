package com.midorimart.managementsystem.model.users;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTOResponse {
    private int id;
    private String email;
    private String fullname;
    private String phonenumber;
    private String address;
    private String token;

    
}
