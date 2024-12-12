package com.coze.openapi.service.service.workspace;

import com.coze.openapi.api.WorkspaceAPI;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.common.pagination.PaginationBase;
import com.coze.openapi.client.workspace.ListWorkspaceResp;
import com.coze.openapi.client.workspace.Workspace;
import com.coze.openapi.service.utils.Utils;

public class WorkspacePagination extends PaginationBase<Workspace>{
    private final WorkspaceAPI api;
    private final int pageSize ;

    public WorkspacePagination(WorkspaceAPI api) {
        this.api = api;
        this.pageSize = 20;
    }

    public WorkspacePagination(WorkspaceAPI api, int pageSize) {
        this.api = api;
        this.pageSize = pageSize;
    }

    @Override
    protected PageResponse<Workspace> fetchNextPage(String cursor) throws Exception {
        Integer pageCursor = parsePageCursor(cursor);
        BaseResponse<ListWorkspaceResp> resp = Utils.execute(api.ListWorkspaces(pageCursor, pageSize));
        PageResponse<Workspace> data = new PageResponse<>(
            resp.getData().getWorkspaces().size() == pageSize,
            String.valueOf(pageCursor + 1),
            resp.getData().getWorkspaces()
        );
        return data;
    }
}
