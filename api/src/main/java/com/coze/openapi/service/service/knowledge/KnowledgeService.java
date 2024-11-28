package com.coze.openapi.service.service.knowledge;

import com.coze.openapi.api.DocumentAPI;

public class KnowledgeService {
    private final DocumentService documentAPI;

    public KnowledgeService(DocumentAPI api) {
        this.documentAPI = new DocumentService(api);
    }

    public DocumentService documents() {
        return this.documentAPI;
    }

}
