package com.coze.openapi.client.workspace;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Workspace {
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("icon_url")
    private String iconUrl;  
    
    @JsonProperty("role_type")
    private WorkspaceRoleType roleType;
    
    @JsonProperty("workspace_type")
    private WorkspaceType workspaceType;
} 