package com.coze.openapi.service.service.dataset;

import com.coze.openapi.api.DatasetImageAPI;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.common.pagination.PageFetcher;
import com.coze.openapi.client.common.pagination.PageNumBasedPaginator;
import com.coze.openapi.client.common.pagination.PageRequest;
import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.dataset.image.ListImageReq;
import com.coze.openapi.client.dataset.image.ListImageResp;
import com.coze.openapi.client.dataset.image.UpdateImageReq;
import com.coze.openapi.client.dataset.image.UpdateImageResp;
import com.coze.openapi.client.dataset.image.model.Photo;
import com.coze.openapi.service.utils.Utils;

public class ImageService {
  private final DatasetImageAPI api;

  public ImageService(DatasetImageAPI api) {
    this.api = api;
  }

  /*
   * Call this API to update the description of images in the knowledge base.
   *
   * docs en: https://www.coze.com/docs/developer_guides/update_image_caption
   * docs zh: https://www.coze.cn/docs/developer_guides/update_image_caption
   */
  public UpdateImageResp update(UpdateImageReq req) {
    BaseResponse<UpdateImageResp> result =
        Utils.execute(api.update(req.getDatasetID(), req.getDocumentID(), req, req));
    return UpdateImageResp.builder().logID(result.getLogID()).build();
  }

  /*
   * Call this API to view detailed information of images in the image knowledge base.
   * When viewing images, it is supported to filter through image annotations.
   *
   * docs en: https://www.coze.com/docs/developer_guides/get_images
   * docs zh: https://www.coze.cn/docs/developer_guides/get_images
   */
  public PageResp<Photo> list(ListImageReq req) {
    if (req.getDatasetID() == null) {
      throw new IllegalArgumentException("datasetID is required");
    }

    // 创建分页获取器
    PageFetcher<Photo> pageFetcher =
        request -> {
          ListImageResp resp =
              Utils.execute(
                      api.list(
                          req.getDatasetID(),
                          req.getKeyword(),
                          req.getHasCaption(),
                          request.getPageNum(),
                          request.getPageSize(),
                          req))
                  .getData();
          return PageResponse.<Photo>builder()
              .hasMore(resp.getPhotoInfos().size() == request.getPageSize())
              .data(resp.getPhotoInfos())
              .pageNum(request.getPageNum())
              .logID(resp.getLogID())
              .pageSize(request.getPageSize())
              .total(resp.getTotalCount())
              .build();
        };

    // 创建分页器
    PageNumBasedPaginator<Photo> paginator =
        new PageNumBasedPaginator<>(pageFetcher, req.getPageSize());

    // 获取当前页数据
    PageRequest initialRequest =
        PageRequest.builder().pageNum(req.getPageNum()).pageSize(req.getPageSize()).build();

    PageResponse<Photo> currentPage = pageFetcher.fetch(initialRequest);

    return PageResp.<Photo>builder()
        .total(currentPage.getTotal())
        .items(currentPage.getData())
        .iterator(paginator)
        .hasMore(currentPage.isHasMore())
        .logID(currentPage.getLogID())
        .build();
  }
}
