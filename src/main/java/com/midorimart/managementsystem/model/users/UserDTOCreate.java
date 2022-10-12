package com.midorimart.managementsystem.model.users;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTOCreate {
    private String fullname;
    private String email;
    private String phonenumber;
    private String password;
    
}
