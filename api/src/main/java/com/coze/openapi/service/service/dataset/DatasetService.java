/* (C)2024 */
package com.coze.openapi.service.service.dataset;

import com.coze.openapi.api.DocumentAPI;

public class DatasetService {
  private final DocumentService documentAPI;

  public DatasetService(DocumentAPI api) {
    this.documentAPI = new DocumentService(api);
  }

  public DocumentService documents() {
    return this.documentAPI;
  }
}
