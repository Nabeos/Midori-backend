package com.midorimart.managementsystem.model.mapper;

import com.midorimart.managementsystem.entity.Role;
import com.midorimart.managementsystem.model.role.RoleDTOResponse;

public class RoleMapper {
    public static RoleDTOResponse toRoleDTOResponse(Role role){
        return RoleDTOResponse.builder().id(role.getId()).name(role.getName()).build();
    }
}
