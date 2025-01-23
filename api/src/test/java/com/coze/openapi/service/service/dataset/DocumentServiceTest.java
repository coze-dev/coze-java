package com.coze.openapi.service.service.dataset;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.api.DatasetDocumentAPI;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.client.dataset.document.*;
import com.coze.openapi.client.dataset.document.model.Document;
import com.coze.openapi.client.dataset.document.model.DocumentBase;
import com.coze.openapi.utils.Utils;

import retrofit2.Call;
import retrofit2.Response;

class DocumentServiceTest {

  @Mock private DatasetDocumentAPI documentAPI;

  private DocumentService documentService;

  private Long dataSetID = 12345L;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    documentService = new DocumentService(documentAPI);
  }

  @Test
  void testCreate() throws Exception {
    // 准备测试数据
    CreateDocumentReq req =
        CreateDocumentReq.builder()
            .datasetID(dataSetID)
            .documentBases(
                Collections.singletonList(
                    DocumentBase.buildWebPage("web document", "www.coze.com")))
            .build();

    CreateDocumentResp createResp =
        CreateDocumentResp.builder()
            .documentInfos(Arrays.asList(Document.builder().documentID("123456").build()))
            .logID(Utils.TEST_LOG_ID)
            .build();

    // 创建 mock Call 对象
    Call<CreateDocumentResp> call = mock(Call.class);
    when(documentAPI.create(any(CreateDocumentReq.class), any(CreateDocumentReq.class)))
        .thenReturn(call);
    when(call.execute()).thenReturn(Response.success(createResp, Utils.getCommonHeader()));

    // 执行测试
    CreateDocumentResp result = documentService.create(req);

    // 验证结果
    assertNotNull(result);
    assertEquals("123456", result.getDocumentInfos().get(0).getDocumentID());
    assertEquals(Utils.TEST_LOG_ID, result.getLogID());
  }

  @Test
  void testUpdate() throws Exception {
    // 准备测试数据
    UpdateDocumentReq req =
        UpdateDocumentReq.builder().documentID(12345L).documentName("updated_name").build();

    BaseResponse<Void> baseResponse =
        BaseResponse.<Void>builder().code(0).msg("success").logID(Utils.TEST_LOG_ID).build();

    // 创建 mock Call 对象
    Call<BaseResponse<Void>> call = mock(Call.class);
    when(documentAPI.update(any(UpdateDocumentReq.class), any(UpdateDocumentReq.class)))
        .thenReturn(call);
    when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    UpdateDocumentResp result = documentService.update(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(Utils.TEST_LOG_ID, result.getLogID());
  }

  @Test
  void testDelete() throws Exception {
    // 准备测试数据
    DeleteDocumentReq req =
        DeleteDocumentReq.builder().documentIDs(Arrays.asList(12345L, 123456L)).build();

    BaseResponse<Void> baseResponse =
        BaseResponse.<Void>builder().code(0).msg("success").logID(Utils.TEST_LOG_ID).build();

    // 创建 mock Call 对象
    Call<BaseResponse<Void>> call = mock(Call.class);
    when(documentAPI.delete(any(DeleteDocumentReq.class), any(DeleteDocumentReq.class)))
        .thenReturn(call);
    when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    DeleteDocumentResp result = documentService.delete(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(Utils.TEST_LOG_ID, result.getLogID());
  }

  @Test
  void testList() throws Exception {
    // 准备测试数据
    ListDocumentReq req = ListDocumentReq.builder().datasetID(dataSetID).page(1).size(10).build();

    List<Document> documents =
        Arrays.asList(
            Document.builder().documentID("doc1").name("Document 1").build(),
            Document.builder().documentID("doc2").name("Document 2").build());

    ListDocumentResp listResp =
        ListDocumentResp.builder()
            .documentInfos(documents)
            .total(2L)
            .logID(Utils.TEST_LOG_ID)
            .build();

    // 创建 mock Call 对象
    Call<ListDocumentResp> call = mock(Call.class);
    when(documentAPI.list(any(ListDocumentReq.class), any(ListDocumentReq.class))).thenReturn(call);
    when(call.execute()).thenReturn(Response.success(listResp, Utils.getCommonHeader()));

    // 执行测试
    PageResp<Document> result = documentService.list(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(2, result.getItems().size());
    assertEquals("doc1", result.getItems().get(0).getDocumentID());
    assertEquals(Utils.TEST_LOG_ID, result.getLogID());
    assertEquals(2, result.getTotal());
  }
}
