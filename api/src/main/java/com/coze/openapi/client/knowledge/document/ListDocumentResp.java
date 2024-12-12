package com.coze.openapi.client.knowledge.document;

import java.util.List;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.knowledge.document.model.Document;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListDocumentResp extends BaseResponse<List<Document>>{
    @JsonProperty("total")
    private Long total;
    @JsonProperty("document_infos")
    private List<Document> documentInfos;
    
}
