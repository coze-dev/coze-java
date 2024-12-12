/* (C)2024 */
package com.coze.openapi.service.service.dataset;

import org.jetbrains.annotations.NotNull;

import com.coze.openapi.api.DocumentAPI;
import com.coze.openapi.client.common.pagination.PageFetcher;
import com.coze.openapi.client.common.pagination.PageNumBasedPaginator;
import com.coze.openapi.client.common.pagination.PageRequest;
import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.dataset.document.CreateDocumentReq;
import com.coze.openapi.client.dataset.document.CreateDocumentResp;
import com.coze.openapi.client.dataset.document.DeleteDocumentReq;
import com.coze.openapi.client.dataset.document.DeleteDocumentResp;
import com.coze.openapi.client.dataset.document.ListDocumentReq;
import com.coze.openapi.client.dataset.document.ListDocumentResp;
import com.coze.openapi.client.dataset.document.UpdateDocumentReq;
import com.coze.openapi.client.dataset.document.UpdateDocumentResp;
import com.coze.openapi.client.dataset.document.model.Document;
import com.coze.openapi.service.utils.Utils;

public class DocumentService {
  private final DocumentAPI api;

  public DocumentService(DocumentAPI api) {
    this.api = api;
  }

  public CreateDocumentResp create(CreateDocumentReq req) {
    return Utils.execute(api.create(req, req));
  }

  /*
  *   Modify the knowledge base file name and update strategy.

      docs en: https://www.coze.com/docs/developer_guides/modify_knowledge_files
      docs zh: https://www.coze.cn/docs/developer_guides/modify_knowledge_files
  * */
  public UpdateDocumentResp update(UpdateDocumentReq req) {
    String logID = Utils.execute(api.update(req, req)).getLogID();
    return UpdateDocumentResp.builder().logID(logID).build();
  }

  /*
  *  Delete text, images, sheets, and other files in the knowledge base, supporting batch deletion.

      docs en: https://www.coze.com/docs/developer_guides/delete_knowledge_files
      docs zh: https://www.coze.cn/docs/developer_guides/delete_knowledge_files
  * */
  public DeleteDocumentResp delete(DeleteDocumentReq req) {
    String logID = Utils.execute(api.delete(req, req)).getLogID();
    return DeleteDocumentResp.builder().logID(logID).build();
  }

  /*
  *  View the file list of a specified knowledge base, which includes lists of documents, spreadsheets, or images.

      docs en: https://www.coze.com/docs/developer_guides/list_knowledge_files
      docs zh: https://www.coze.cn/docs/developer_guides/list_knowledge_files
  * */
  public PageResp<Document> list(@NotNull ListDocumentReq req) {
    if (req == null || req.getDatasetID() == null) {
      throw new IllegalArgumentException("req is required");
    }

    Integer pageNum = req.getPage();
    Integer pageSize = req.getSize();

    // 创建分页获取器
    PageFetcher<Document> pageFetcher =
        request -> {
          ListDocumentResp resp =
              Utils.execute(
                  api.list(
                      ListDocumentReq.of(
                          req.getDatasetID(), request.getPageNum(), request.getPageSize()),
                      req));
          return PageResponse.<Document>builder()
              .hasMore(resp.getDocumentInfos().size() == request.getPageSize())
              .data(resp.getDocumentInfos())
              .pageNum(request.getPageNum())
              .logID(resp.getLogID())
              .pageSize(request.getPageSize())
              .total(resp.getTotal().intValue())
              .build();
        };

    // 创建分页器
    PageNumBasedPaginator<Document> paginator = new PageNumBasedPaginator<>(pageFetcher, pageSize);

    // 获取当前页数据
    PageRequest initialRequest = PageRequest.builder().pageNum(pageNum).pageSize(pageSize).build();

    PageResponse<Document> currentPage = pageFetcher.fetch(initialRequest);

    return PageResp.<Document>builder()
        .total(currentPage.getTotal())
        .items(currentPage.getData())
        .iterator(paginator)
        .hasMore(currentPage.isHasMore())
        .logID(currentPage.getLogID())
        .build();
  }
}
