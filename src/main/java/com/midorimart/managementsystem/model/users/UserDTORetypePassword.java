package com.midorimart.managementsystem.model.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTORetypePassword {
    String currentPassword;
    String password;
    String repassword;
    
}
