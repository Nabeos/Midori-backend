package com.midorimart.managementsystem.model.users;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTOLoginRequest {
    private String email;
    private String password;
}
