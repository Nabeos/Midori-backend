package com.midorimart.managementsystem.model.users;

import java.util.List;

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
    private String email;
    private String fullname;
    private String thumbnail;
    private String phonenumber;
    private List<String> address;
    private String token;
}
