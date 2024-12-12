package com.coze.openapi.service.service.knowledge;

import com.coze.openapi.api.DocumentAPI;

public class KnowledgeService {
    private final DocumentService document;

    public KnowledgeService(DocumentAPI api) {
        this.document = new DocumentService(api);
    }

    public DocumentService document() {
        return this.document;
    }

}
