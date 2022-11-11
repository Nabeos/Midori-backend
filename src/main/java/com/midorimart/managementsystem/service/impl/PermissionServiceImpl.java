package com.midorimart.managementsystem.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.midorimart.managementsystem.model.permission.PermissionDTOCreate;
import com.midorimart.managementsystem.model.permission.PermissionDTOResponse;
import com.midorimart.managementsystem.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService{

    @Override
    public Map<String, PermissionDTOResponse> addNewPermission(Map<String, PermissionDTOCreate> permissionDTOMap) {
        // TODO Auto-generated method stub
        return null;
    }

}
