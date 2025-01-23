package com.coze.openapi.service.service.template;

import com.coze.openapi.api.TemplateAPI;
import com.coze.openapi.client.template.DuplicateTemplateReq;
import com.coze.openapi.client.template.DuplicateTemplateResp;
import com.coze.openapi.service.utils.Utils;

public class TemplateService {

  private final TemplateAPI templateAPI;

  public TemplateService(TemplateAPI templateAPI) {
    this.templateAPI = templateAPI;
  }

  public DuplicateTemplateResp duplicate(DuplicateTemplateReq req) {
    return Utils.execute(templateAPI.duplicate(req.getTemplateID(), req, req)).getData();
  }
}
