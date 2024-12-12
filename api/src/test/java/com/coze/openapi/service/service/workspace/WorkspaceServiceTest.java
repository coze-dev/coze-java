/* (C)2024 */
package com.coze.openapi.service.service.workspace;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coze.openapi.api.WorkspaceAPI;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.client.workspace.ListWorkspaceReq;
import com.coze.openapi.client.workspace.ListWorkspaceResp;
import com.coze.openapi.client.workspace.model.Workspace;
import com.coze.openapi.utils.Utils;

import retrofit2.Call;
import retrofit2.Response;

class WorkspaceServiceTest {

  @Mock private WorkspaceAPI workspaceAPI;

  private WorkspaceService workspaceService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    workspaceService = new WorkspaceService(workspaceAPI);
  }

  @Test
  void testListWorkspaces() throws Exception {
    // 准备测试数据
    int pageNum = 1;
    int pageSize = 2;
    ListWorkspaceReq req = ListWorkspaceReq.builder().pageNum(pageNum).pageSize(pageSize).build();

    // 准备模拟响应数据
    List<Workspace> workspaces =
        Arrays.asList(
            Workspace.builder().id("workspace1").name("工作空间1").build(),
            Workspace.builder().id("workspace2").name("工作空间2").build());

    ListWorkspaceResp respData =
        ListWorkspaceResp.builder()
            .workspaces(workspaces)
            .totalCount(5) // 总共5条数据
            .build();

    BaseResponse<ListWorkspaceResp> baseResponse =
        BaseResponse.<ListWorkspaceResp>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .data(respData)
            .build();

    // 创建 mock Call 对象
    Call<BaseResponse<ListWorkspaceResp>> call = mock(Call.class);
    when(workspaceAPI.list(eq(pageNum), eq(pageSize), any(ListWorkspaceReq.class)))
        .thenReturn(call);
    when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    PageResp<Workspace> result = workspaceService.list(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(5, result.getTotal());
    assertEquals(2, result.getItems().size());
    assertTrue(result.getHasMore());
    assertEquals(Utils.TEST_LOG_ID, result.getLogID());
    assertEquals("workspace1", result.getItems().get(0).getId());
    assertEquals("工作空间1", result.getItems().get(0).getName());
  }

  @Test
  void testListWorkspacesLastPage() throws Exception {
    // 准备测试数据 - 最后一页
    int pageNum = 2;
    int pageSize = 2;
    ListWorkspaceReq req = ListWorkspaceReq.builder().pageNum(pageNum).pageSize(pageSize).build();

    // 准备模拟响应数据 - 只有一条数据的最后一页
    List<Workspace> workspaces =
        Arrays.asList(Workspace.builder().id("workspace3").name("工作空间3").build());

    ListWorkspaceResp respData =
        ListWorkspaceResp.builder().workspaces(workspaces).totalCount(3).build();

    BaseResponse<ListWorkspaceResp> baseResponse =
        BaseResponse.<ListWorkspaceResp>builder()
            .code(0)
            .msg("success")
            .logID(Utils.TEST_LOG_ID)
            .data(respData)
            .build();

    // 创建 mock Call 对象
    Call<BaseResponse<ListWorkspaceResp>> call = mock(Call.class);
    when(workspaceAPI.list(any(), any(), any())).thenReturn(call);
    when(call.execute()).thenReturn(Response.success(baseResponse, Utils.getCommonHeader()));

    // 执行测试
    PageResp<Workspace> result = workspaceService.list(req);

    // 验证结果
    assertNotNull(result);
    assertEquals(3, result.getTotal());
    assertEquals(1, result.getItems().size());
    assertFalse(result.getHasMore());
  }

  @Test
  void testListWorkspacesWithNullRequest() {
    // 验证空请求异常
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          workspaceService.list(null);
        });
  }
}
