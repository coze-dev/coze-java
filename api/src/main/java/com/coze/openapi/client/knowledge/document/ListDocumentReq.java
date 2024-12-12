package com.coze.openapi.client.knowledge.document;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListDocumentReq {
    @NotNull
    @JsonProperty("dataset_id")
    private Long datasetID;
    @JsonProperty("page")
    private Integer page;
    @JsonProperty("size")
    private Integer size;
}
