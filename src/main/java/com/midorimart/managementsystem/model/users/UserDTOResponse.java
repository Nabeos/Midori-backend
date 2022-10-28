package com.midorimart.managementsystem.model.users;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTOResponse {
    private int id;
    private String email;
    private String fullname;
    private String phonenumber;
    private List<String> address;
    private String token;
}
