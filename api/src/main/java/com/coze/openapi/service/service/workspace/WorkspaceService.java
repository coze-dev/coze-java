package com.coze.openapi.service.service.workspace;

import com.coze.openapi.api.WorkspaceAPI;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.common.pagination.PageFetcher;
import com.coze.openapi.client.common.pagination.PageNumBasedPaginator;
import com.coze.openapi.client.common.pagination.PageRequest;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.client.workspace.ListWorkspaceReq;
import com.coze.openapi.client.workspace.ListWorkspaceResp;
import com.coze.openapi.client.workspace.model.Workspace;
import com.coze.openapi.service.utils.Utils;

public class WorkspaceService {
    private final WorkspaceAPI workspaceAPI;

    public WorkspaceService(WorkspaceAPI workspaceAPI) {
        this.workspaceAPI = workspaceAPI;
    }

    public PageResp<Workspace> list(ListWorkspaceReq req) {
        if (req == null) {
            throw new IllegalArgumentException("req is required");
        }

        Integer pageNum = req.getPageNum();
        Integer pageSize = req.getPageSize();

        // create paginator
        PageFetcher<Workspace> pageFetcher = request -> {
            BaseResponse<ListWorkspaceResp> resp = Utils.execute(
                workspaceAPI.list(request.getPageNum(), request.getPageSize(), req)
            );
            
            return PageResponse.<Workspace>builder()
                .hasMore(resp.getData().getWorkspaces().size() == request.getPageSize())
                .data(resp.getData().getWorkspaces())
                .pageNum(request.getPageNum())
                .pageSize(request.getPageSize())
                .total(resp.getData().getTotalCount())
                .logID(resp.getLogID())
                .build();
        };

        // create paginator
        PageNumBasedPaginator<Workspace> paginator = new PageNumBasedPaginator<>(pageFetcher, pageSize);

        // get data from current page
        PageRequest initialRequest = PageRequest.builder()
            .pageNum(pageNum)
            .pageSize(pageSize)
            .build();
        
        PageResponse<Workspace> firstPage = pageFetcher.fetch(initialRequest);

        return PageResp.<Workspace>builder()
            .total(firstPage.getTotal())
            .items(firstPage.getData())
            .iterator(paginator)
            .hasMore(firstPage.isHasMore())
            .logID(firstPage.getLogID())
            .build();
    }
}
