package com.midorimart.managementsystem.service;

import java.util.Map;

import com.midorimart.managementsystem.model.permission.PermissionDTOCreate;
import com.midorimart.managementsystem.model.permission.PermissionDTOResponse;

public interface PermissionService {

    Map<String, PermissionDTOResponse> addNewPermission(Map<String, PermissionDTOCreate> permissionDTOMap);

}
