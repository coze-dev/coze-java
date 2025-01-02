package com.coze.openapi.service.service.dataset;

import com.coze.openapi.api.DatasetAPI;
import com.coze.openapi.api.DatasetDocumentAPI;
import com.coze.openapi.api.DatasetImageAPI;
import com.coze.openapi.client.common.pagination.PageFetcher;
import com.coze.openapi.client.common.pagination.PageNumBasedPaginator;
import com.coze.openapi.client.common.pagination.PageRequest;
import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.dataset.CreateDatasetReq;
import com.coze.openapi.client.dataset.CreateDatasetResp;
import com.coze.openapi.client.dataset.DeleteDatasetReq;
import com.coze.openapi.client.dataset.DeleteDatasetResp;
import com.coze.openapi.client.dataset.ListDatasetReq;
import com.coze.openapi.client.dataset.ListDatasetResp;
import com.coze.openapi.client.dataset.ProcessDatasetReq;
import com.coze.openapi.client.dataset.ProcessDatasetResp;
import com.coze.openapi.client.dataset.UpdateDatasetReq;
import com.coze.openapi.client.dataset.UpdateDatasetResp;
import com.coze.openapi.client.dataset.model.Dataset;
import com.coze.openapi.service.utils.Utils;

public class DatasetService {
  private final DatasetAPI api;
  private final DocumentService documentService;
  private final ImageService imageService;

  public DatasetService(DatasetAPI api, DatasetDocumentAPI documentApi, DatasetImageAPI imageApi) {
    this.api = api;
    this.documentService = new DocumentService(documentApi);
    this.imageService = new ImageService(imageApi);
  }

  public DocumentService documents() {
    return this.documentService;
  }

  public ImageService images() {
    return this.imageService;
  }

  /*
   * Create Dataset
   *
   * docs en: https://www.coze.com/docs/developer_guides/create_dataset
   * docs zh: https://www.coze.cn/docs/developer_guides/create_dataset
   */
  public CreateDatasetResp create(CreateDatasetReq req) {
    return Utils.execute(api.create(req, req)).getData();
  }

  /*
   * List Datasets
   *
   * docs en: https://www.coze.com/docs/developer_guides/list_dataset
   * docs zh: https://www.coze.cn/docs/developer_guides/list_dataset
   */
  public PageResp<Dataset> list(ListDatasetReq req) {
    if (req.getSpaceID() == null) {
      throw new IllegalArgumentException("spaceID is required");
    }

    // 创建分页获取器
    PageFetcher<Dataset> pageFetcher =
        request -> {
          ListDatasetResp resp =
              Utils.execute(
                      api.list(
                          req.getSpaceID(),
                          req.getName(),
                          req.getFormatType() != null ? req.getFormatType().getValue() : null,
                          request.getPageSize(),
                          request.getPageNum(),
                          req))
                  .getData();
          return PageResponse.<Dataset>builder()
              .hasMore(resp.getDatasetList().size() == request.getPageSize())
              .data(resp.getDatasetList())
              .pageNum(request.getPageNum())
              .logID(resp.getLogID())
              .pageSize(request.getPageSize())
              .total(resp.getTotalCount())
              .build();
        };

    // 创建分页器
    PageNumBasedPaginator<Dataset> paginator =
        new PageNumBasedPaginator<>(pageFetcher, req.getPageSize());

    // 获取当前页数据
    PageRequest initialRequest =
        PageRequest.builder().pageNum(req.getPageNum()).pageSize(req.getPageSize()).build();

    PageResponse<Dataset> currentPage = pageFetcher.fetch(initialRequest);

    return PageResp.<Dataset>builder()
        .total(currentPage.getTotal())
        .items(currentPage.getData())
        .iterator(paginator)
        .hasMore(currentPage.isHasMore())
        .logID(currentPage.getLogID())
        .build();
  }

  /*
   * Update Dataset
   *
   * docs en: https://www.coze.com/docs/developer_guides/update_dataset
   * docs zh: https://www.coze.cn/docs/developer_guides/update_dataset
   */
  public UpdateDatasetResp update(UpdateDatasetReq req) {
    if (req.getDatasetID() == null) {
      throw new IllegalArgumentException("datasetID is required");
    }
    return Utils.execute(api.update(req.getDatasetID(), req, req)).getData();
  }

  /*
   * Delete Dataset
   *
   * docs en: https://www.coze.com/docs/developer_guides/delete_dataset
   * docs zh: https://www.coze.cn/docs/developer_guides/delete_dataset
   */
  public DeleteDatasetResp delete(DeleteDatasetReq req) {
    if (req.getDatasetID() == null) {
      throw new IllegalArgumentException("datasetID is required");
    }
    return Utils.execute(api.delete(req.getDatasetID(), req)).getData();
  }

  /*
   * Check the upload progress
   *
   * docs en: https://www.coze.com/docs/developer_guides/get_dataset_progress
   * docs zh: https://www.coze.cn/docs/developer_guides/get_dataset_progress
   */
  public ProcessDatasetResp process(ProcessDatasetReq req) {
    if (req.getDatasetID() == null) {
      throw new IllegalArgumentException("datasetID is required");
    }
    return Utils.execute(api.process(req.getDatasetID(), req, req)).getData();
  }
}
