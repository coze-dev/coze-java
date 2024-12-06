package com.coze.openapi.client.dataset.document;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.dataset.document.model.Document;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper=true)
public class CreateDocumentResp extends BaseResponse<List<Document>>{
    @JsonProperty("document_infos")
    private List<Document> documentInfos;

}