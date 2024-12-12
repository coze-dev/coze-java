package com.coze.openapi.client.workspace;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListWorkspaceResp {
    @JsonProperty("total_count")
    private Integer totalCount;

    @JsonProperty("workspaces")
    private List<Workspace> workspaces;

}