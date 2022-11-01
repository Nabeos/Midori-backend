package com.midorimart.managementsystem.model.users;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTOUpdate {
    private String email;
    private String fullname;
    private String phoneNumber;
    private String thumbnail;
    private List<String> address;
}
