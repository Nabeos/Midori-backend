package com.midorimart.managementsystem.model.permission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDTOResponse {
    private int id;
    private String actionName;
    private String actionCode;
    private String path;
    private String method;
    private int checkAction;
}
