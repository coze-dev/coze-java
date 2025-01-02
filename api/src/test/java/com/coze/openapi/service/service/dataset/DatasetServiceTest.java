package com.coze.openapi.service.service.dataset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.api.DatasetAPI;
import com.coze.openapi.api.DatasetDocumentAPI;
import com.coze.openapi.api.DatasetImageAPI;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.common.pagination.PageResp;
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
import com.coze.openapi.client.dataset.document.model.DocumentFormatType;
import com.coze.openapi.client.dataset.model.Dataset;
import com.coze.openapi.utils.Utils;

import retrofit2.Call;
import retrofit2.Response;

class DatasetServiceTest {
  @Mock private DatasetAPI datasetAPI;
  @Mock private DatasetDocumentAPI documentAPI;
  @Mock private DatasetImageAPI imageAPI;

  private DatasetService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    service = new DatasetService(datasetAPI, documentAPI, imageAPI);
  }

  @Test
  void testCreate() throws Exception {
    // 准备测试数据
    CreateDatasetReq req =
        CreateDatasetReq.builder()
            .name("test")
            .spaceID("space_1")
            .formatType(DocumentFormatType.TEXT)
            .build();

    CreateDatasetResp respData =
        CreateDatasetResp.builder().datasetID("dataset_1").logID(Utils.TEST_LOG_ID).build();

    BaseResponse<CreateDatasetResp> baseResponse =
        BaseResponse.<CreateDatasetResp>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .data(respData)
            .build();

    // 创建 mock Call 对象
    Call<BaseResponse<CreateDatasetResp>> call = mock(Call.class);
    when(datasetAPI.create(any(), any())).thenReturn(call);
    when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    CreateDatasetResp result = service.create(req);

    // 验证结果
    assertNotNull(result);
    assertEquals("dataset_1", result.getDatasetID());
    assertEquals(Utils.TEST_LOG_ID, result.getLogID());
  }

  @Test
  void testList() throws Exception {
    // 准备测试数据
    ListDatasetReq req =
        ListDatasetReq.builder().spaceID("space_1").pageNum(1).pageSize(10).build();

    List<Dataset> datasets =
        Arrays.asList(
            Dataset.builder().datasetID("1").name("dataset1").build(),
            Dataset.builder().datasetID("2").name("dataset2").build());

    ListDatasetResp respData =
        ListDatasetResp.builder()
            .datasetList(datasets)
            .totalCount(5) // 总共5条数据
            .logID(Utils.TEST_LOG_ID)
            .build();

    BaseResponse<ListDatasetResp> baseResponse =
        BaseResponse.<ListDatasetResp>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .data(respData)
            .build();

    // 创建 mock Call 对象
    Call<BaseResponse<ListDatasetResp>> call = mock(Call.class);
    when(datasetAPI.list(any(), any(), any(), any(), any(), any())).thenReturn(call);
    when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    PageResp<Dataset> result = service.list(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(5, result.getTotal());
    assertEquals(2, result.getItems().size());
    assertEquals(Utils.TEST_LOG_ID, result.getLogID());
    assertEquals("1", result.getItems().get(0).getDatasetID());
    assertEquals("dataset1", result.getItems().get(0).getName());
  }

  @Test
  void testUpdate() throws Exception {
    // 准备测试数据
    UpdateDatasetReq req =
        UpdateDatasetReq.builder().datasetID("dataset_1").name("new name").build();

    BaseResponse<UpdateDatasetResp> baseResponse =
        BaseResponse.<UpdateDatasetResp>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .build();

    // 创建 mock Call 对象
    Call<BaseResponse<UpdateDatasetResp>> call = mock(Call.class);
    when(datasetAPI.update(any(), any(), any())).thenReturn(call);
    when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    UpdateDatasetResp result = service.update(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(Utils.TEST_LOG_ID, result.getLogID());
  }

  @Test
  void testDelete() throws Exception {
    // 准备测试数据
    DeleteDatasetReq req = DeleteDatasetReq.builder().datasetID("dataset_1").build();

    BaseResponse<DeleteDatasetResp> baseResponse =
        BaseResponse.<DeleteDatasetResp>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .build();

    // 创建 mock Call 对象
    Call<BaseResponse<DeleteDatasetResp>> call = mock(Call.class);
    when(datasetAPI.delete(any(), any())).thenReturn(call);
    when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    DeleteDatasetResp result = service.delete(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(Utils.TEST_LOG_ID, result.getLogID());
  }

  @Test
  void testProcess() throws Exception {
    // 准备测试数据
    ProcessDatasetReq req =
        ProcessDatasetReq.builder()
            .datasetID("dataset_1")
            .documentIDs(Arrays.asList("doc1", "doc2"))
            .build();

    ProcessDatasetResp respData = ProcessDatasetResp.builder().logID(Utils.TEST_LOG_ID).build();

    BaseResponse<ProcessDatasetResp> baseResponse =
        BaseResponse.<ProcessDatasetResp>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .data(respData)
            .build();

    // 创建 mock Call 对象
    Call<BaseResponse<ProcessDatasetResp>> call = mock(Call.class);
    when(datasetAPI.process(any(), any(), any())).thenReturn(call);
    when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    ProcessDatasetResp result = service.process(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(Utils.TEST_LOG_ID, result.getLogID());
  }
}
