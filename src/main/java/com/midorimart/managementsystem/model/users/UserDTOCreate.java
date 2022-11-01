package com.midorimart.managementsystem.model.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOCreate {
    private String fullname;
    private String email;
    private String phonenumber;
    private String password;
}
