package com.midorimart.managementsystem.model.users;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTOFilter {
    private int offset;
    private int limit;
}
