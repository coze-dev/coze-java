package com.coze.openapi.service.service.knowledge;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.coze.openapi.api.DocumentAPI;
import com.coze.openapi.client.common.pagination.PageIterator;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.common.pagination.PageResult;
import com.coze.openapi.client.knowledge.document.CreateDocumentReq;
import com.coze.openapi.client.knowledge.document.DeleteDocumentReq;
import com.coze.openapi.client.knowledge.document.ListDocumentReq;
import com.coze.openapi.client.knowledge.document.ListDocumentResp;
import com.coze.openapi.client.knowledge.document.ModifyDocumentReq;
import com.coze.openapi.client.knowledge.document.model.Document;
import com.coze.openapi.service.utils.Utils;

public class DocumentService {
    private final DocumentAPI api;

    public DocumentService(DocumentAPI api) {
        this.api = api;
    }

    public List<Document> create(CreateDocumentReq req) {
        return Utils.execute(api.CreateDocument(req)).getDocumentInfos();
    }

    public void modify(ModifyDocumentReq req) {
        Utils.execute(api.UpdateDocument(req));
    }

    public void delete(DeleteDocumentReq req) {
        Utils.execute(api.DeleteDocument(req));
    }

    public PageResult<Document> list(@NotNull ListDocumentReq req) {
        if (req.getSize() == null) {
            req.setSize(10);
        }
        if (req.getPage() == null) {
            req.setPage(1);
        }
        // 获取当前页数据
        ListDocumentResp resp = Utils.execute(api.ListDocument(req));
        // 生成分页器
        DocumentPage pagination = new DocumentPage(api, req.getDatasetID(), req.getSize());
        // 构建当前页数据
        Boolean hasMore = resp.getDocumentInfos().size() == req.getSize();

        PageResponse<Document> pageResponse = PageResponse.<Document>builder()
            .hasMore(hasMore)
            .nextCursor(String.valueOf(req.getPage() + 1))
            .data(resp.getDocumentInfos())
            .build();

        return PageResult.<Document>builder()
            .total(resp.getTotal().intValue())
            .items(resp.getDocumentInfos())
            .iterator(new PageIterator<>(pagination, pageResponse))
            .hasMore(hasMore)
            .build();
    }

}
