package com.coze.openapi.service.service.dataset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.api.DatasetImageAPI;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.client.dataset.image.ListImageReq;
import com.coze.openapi.client.dataset.image.ListImageResp;
import com.coze.openapi.client.dataset.image.UpdateImageReq;
import com.coze.openapi.client.dataset.image.UpdateImageResp;
import com.coze.openapi.client.dataset.image.model.Image;
import com.coze.openapi.utils.Utils;

import retrofit2.Call;
import retrofit2.Response;

class ImageServiceTest {
  @Mock private DatasetImageAPI imageAPI;

  private ImageService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    service = new ImageService(imageAPI);
  }

  @Test
  void testUpdate() throws Exception {
    // 准备测试数据
    UpdateImageReq req =
        UpdateImageReq.builder()
            .datasetID("dataset_1")
            .documentID("doc_1")
            .caption("new caption")
            .build();

    BaseResponse<UpdateImageResp> baseResponse =
        BaseResponse.<UpdateImageResp>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .build();

    // 创建 mock Call 对象
    Call<BaseResponse<UpdateImageResp>> call = mock(Call.class);
    when(imageAPI.update(any(), any(), any(), any())).thenReturn(call);
    when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    UpdateImageResp result = service.update(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(Utils.TEST_LOG_ID, result.getLogID());
  }

  @Test
  void testList() throws Exception {
    // 准备测试数据
    ListImageReq req =
        ListImageReq.builder().datasetID("dataset_1").pageNum(1).pageSize(10).build();

    List<Image> images =
        Arrays.asList(
            Image.builder().documentID("1").caption("image1").build(),
            Image.builder().documentID("2").caption("image2").build());

    ListImageResp respData =
        ListImageResp.builder()
            .imageInfos(images)
            .totalCount(5) // 总共5条数据
            .logID(Utils.TEST_LOG_ID)
            .build();

    BaseResponse<ListImageResp> baseResponse =
        BaseResponse.<ListImageResp>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .data(respData)
            .build();

    // 创建 mock Call 对象
    Call<BaseResponse<ListImageResp>> call = mock(Call.class);
    when(imageAPI.list(any(), any(), any(), any(), any(), any())).thenReturn(call);
    when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    PageResp<Image> result = service.list(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(5, result.getTotal());
    assertEquals(2, result.getItems().size());
    assertEquals(Utils.TEST_LOG_ID, result.getLogID());
    assertEquals("1", result.getItems().get(0).getDocumentID());
    assertEquals("image1", result.getItems().get(0).getCaption());
  }

  @Test
  void testListWithInvalidRequest() {
    // 准备测试数据
    ListImageReq req = ListImageReq.builder().build();

    // 执行测试并验证异常
    assertThrows(IllegalArgumentException.class, () -> service.list(req));
  }
}
