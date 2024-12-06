package com.coze.openapi.client.dataset.document;

import java.util.List;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.dataset.document.model.Document;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString(callSuper = true)
public class ListDocumentResp extends BaseResponse<List<Document>>{
    @JsonProperty("total")
    private Long total;
    @JsonProperty("document_infos")
    private List<Document> documentInfos;
    
}
