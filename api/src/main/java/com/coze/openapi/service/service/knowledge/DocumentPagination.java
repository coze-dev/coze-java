package com.coze.openapi.service.service.knowledge;

import com.coze.openapi.api.DocumentAPI;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.common.pagination.PaginationBase;
import com.coze.openapi.client.knowledge.document.ListDocumentReq;
import com.coze.openapi.client.knowledge.document.ListDocumentResp;
import com.coze.openapi.client.knowledge.document.model.Document;
import com.coze.openapi.service.utils.Utils;

public class DocumentPagination extends PaginationBase<Document>{
    private final DocumentAPI api;
    private final Long dataSetID;
    private final Integer pageSize;

    public DocumentPagination(DocumentAPI api, Long dataSetID, Integer pageSize) {
        this.api = api;
        this.dataSetID = dataSetID;
        if (pageSize != null) {
            this.pageSize = pageSize;
        } else {
            this.pageSize = 10;
        }
    }


    @Override
    protected PageResponse<Document> fetchNextPage(String cursor) throws Exception {
        Integer pageCursor = parsePageCursor(cursor);

        ListDocumentReq req = ListDocumentReq.builder().datasetID(this.dataSetID).size(this.pageSize).page(pageCursor).build();
        ListDocumentResp resp = Utils.execute(api.ListDocument(req));


        PageResponse<Document> data = new PageResponse<>(
            this.pageSize.equals(resp.getDocumentInfos().size()),
            String.valueOf(pageCursor + 1),
            resp.getDocumentInfos());
        return data;
    }
    
}
