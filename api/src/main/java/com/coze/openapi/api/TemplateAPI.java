package com.coze.openapi.api;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.template.DuplicateTemplateReq;
import com.coze.openapi.client.template.DuplicateTemplateResp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Tag;

public interface TemplateAPI {
  @POST("/v1/templates/{template_id}/duplicate")
  Call<BaseResponse<DuplicateTemplateResp>> duplicate(
      @Path("template_id") String templateID, @Body DuplicateTemplateReq req, @Tag BaseReq baseReq);
}
