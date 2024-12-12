package com.coze.openapi.service.service.workspace;

import com.coze.openapi.api.WorkspaceAPI;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.common.pagination.PageIterator;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.common.pagination.PageResult;
import com.coze.openapi.client.workspace.ListWorkspaceResp;
import com.coze.openapi.client.workspace.Workspace;
import com.coze.openapi.service.utils.Utils;

public class WorkspaceService {
    private final WorkspaceAPI workspaceAPI;

    public WorkspaceService(WorkspaceAPI workspaceAPI) {
        this.workspaceAPI = workspaceAPI;
    }

    public PageResult<Workspace> listWorkspaces(Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        // 获取当前页数据
        BaseResponse<ListWorkspaceResp> resp = Utils.execute(workspaceAPI.ListWorkspaces(pageNum, pageSize));
        // 生成分页器
        WorkspacePagination pagination = new WorkspacePagination(workspaceAPI, pageSize);
        // 构建当前页数据
        Boolean hasMore = resp.getData().getWorkspaces().size() == pageSize;

        PageResponse<Workspace> pageResponse = PageResponse.<Workspace>builder()
            .hasMore(hasMore)
            .nextCursor(String.valueOf(pageNum + 1))
            .data(resp.getData().getWorkspaces())
            .build();

        return PageResult.<Workspace>builder()
            .total(resp.getData().getTotalCount())
            .items(resp.getData().getWorkspaces())
            .iterator(new PageIterator<>(pagination, pageResponse))
            .hasMore(hasMore)
            .build();
    }
}
