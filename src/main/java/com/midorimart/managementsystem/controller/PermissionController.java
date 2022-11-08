package com.midorimart.managementsystem.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midorimart.managementsystem.model.permission.PermissionDTOCreate;
import com.midorimart.managementsystem.model.permission.PermissionDTOResponse;
import com.midorimart.managementsystem.service.PermissionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Tag(name = "Permission API")
public class PermissionController {
    private final PermissionService permissionService;
    @Operation(summary = "Add new Permission")
    @PostMapping("/v1/permission-management/permissions")
    public Map<String, PermissionDTOResponse> addNewPermission(@RequestBody Map<String, PermissionDTOCreate> permissionDTOMap){
        return permissionService.addNewPermission(permissionDTOMap);
    }
}
