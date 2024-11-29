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
    @Builder.Default
    private Integer page = 1;
    @JsonProperty("size")
    @Builder.Default
    private Integer size = 10;

    public static ListDocumentReq of(Long datasetID, Integer page, Integer size) {
        return ListDocumentReq.builder().datasetID(datasetID).page(page).size(size).build();
    }
}
