package com.coze.openapi.service.service.knowledge;

import org.jetbrains.annotations.NotNull;

import com.coze.openapi.api.DocumentAPI;
import com.coze.openapi.client.common.pagination.PageFetcher;
import com.coze.openapi.client.common.pagination.PageNumBasedPaginator;
import com.coze.openapi.client.common.pagination.PageRequest;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.common.pagination.PageResult;
import com.coze.openapi.client.knowledge.document.CreateDocumentReq;
import com.coze.openapi.client.knowledge.document.CreateDocumentResp;
import com.coze.openapi.client.knowledge.document.DeleteDocumentReq;
import com.coze.openapi.client.knowledge.document.ListDocumentReq;
import com.coze.openapi.client.knowledge.document.ListDocumentResp;
import com.coze.openapi.client.knowledge.document.UpdateDocumentReq;
import com.coze.openapi.client.knowledge.document.model.Document;
import com.coze.openapi.service.utils.Utils;

public class DocumentService {
    private final DocumentAPI api;

    public DocumentService(DocumentAPI api) {
        this.api = api;
    }

    public CreateDocumentResp create(CreateDocumentReq req) {
        return Utils.execute(api.CreateDocument(req));
    }

    /*
    *   Modify the knowledge base file name and update strategy.

        docs en: https://www.coze.com/docs/developer_guides/modify_knowledge_files
        docs zh: https://www.coze.cn/docs/developer_guides/modify_knowledge_files
    * */
    public void update(UpdateDocumentReq req) {
        Utils.execute(api.UpdateDocument(req));
    }

    /*
    *  Delete text, images, sheets, and other files in the knowledge base, supporting batch deletion.

        docs en: https://www.coze.com/docs/developer_guides/delete_knowledge_files
        docs zh: https://www.coze.cn/docs/developer_guides/delete_knowledge_files
    * */
    public void delete(DeleteDocumentReq req) {
        Utils.execute(api.DeleteDocument(req));
    }

    /*
    *  View the file list of a specified knowledge base, which includes lists of documents, spreadsheets, or images.

        docs en: https://www.coze.com/docs/developer_guides/list_knowledge_files
        docs zh: https://www.coze.cn/docs/developer_guides/list_knowledge_files
    * */
    public PageResult<Document> list(@NotNull ListDocumentReq req) {
        if (req == null || req.getDatasetID() == null) {
            throw new IllegalArgumentException("req is required");
        }

        Integer pageNum = req.getPage();
        Integer pageSize = req.getSize();

        // 创建分页获取器
        PageFetcher<Document> pageFetcher = request -> {
            ListDocumentResp resp = Utils.execute(api.ListDocument(ListDocumentReq.of(req.getDatasetID(), request.getPageNum(), request.getPageSize())));
            return PageResponse.<Document>builder()
                .hasMore(resp.getDocumentInfos().size() == request.getPageSize())
                .data(resp.getDocumentInfos())
                .pageNum(request.getPageNum())
                .pageSize(request.getPageSize())
                .total(resp.getTotal().intValue())
                .build();
        };

        // 创建分页器
        PageNumBasedPaginator<Document> paginator = new PageNumBasedPaginator<>(pageFetcher, pageSize);

        // 获取当前页数据
        PageRequest initialRequest = PageRequest.builder()
            .pageNum(pageNum)
            .pageSize(pageSize)
            .build();
        
        PageResponse<Document> currentPage = pageFetcher.fetch(initialRequest);

        return PageResult.<Document>builder()
            .total(currentPage.getTotal())
            .items(currentPage.getData())
            .iterator(paginator)
            .hasMore(currentPage.isHasMore())
            .build();
    }

}
