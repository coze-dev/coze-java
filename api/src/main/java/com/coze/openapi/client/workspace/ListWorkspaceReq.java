package com.coze.openapi.client.workspace;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListWorkspaceReq {
    @NonNull
    @Builder.Default
    private Integer pageNum = 1;
    @NonNull
    @Builder.Default
    private Integer pageSize = 20;

    public static ListWorkspaceReq of(Integer pageNum, Integer pageSize) {
        return ListWorkspaceReq.builder().pageNum(pageNum).pageSize(pageSize).build();
    }
}
