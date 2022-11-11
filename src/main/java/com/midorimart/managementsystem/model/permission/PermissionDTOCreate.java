package com.midorimart.managementsystem.model.permission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDTOCreate {
    private String actionName;
    private String actionCode;
    private String path;
}
