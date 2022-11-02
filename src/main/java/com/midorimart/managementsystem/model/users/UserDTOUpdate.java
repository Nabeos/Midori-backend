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
public class UserDTOUpdate {
    private String email;
    private String fullname;
    private String phoneNumber;
    private String thumbnail;
    private List<String> address;
}
