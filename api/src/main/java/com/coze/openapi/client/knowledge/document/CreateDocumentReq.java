package com.coze.openapi.client.knowledge.document;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.coze.openapi.client.knowledge.document.model.DocumentBase;
import com.coze.openapi.client.knowledge.document.model.DocumentChunkStrategy;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateDocumentReq {
    @NotNull
    @JsonProperty("dataset_id")
    private Long datasetID;
    @NotNull
    @JsonProperty("document_bases")
    private List<DocumentBase> documentBases;
    @JsonProperty("chunk_strategy")
    private DocumentChunkStrategy chunkStrategy;
}
