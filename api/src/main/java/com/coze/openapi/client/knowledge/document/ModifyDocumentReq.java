package com.coze.openapi.client.knowledge.document;

import com.coze.openapi.client.knowledge.document.model.DocumentUpdateRule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModifyDocumentReq {
    @NonNull
    @JsonProperty("document_id")
    private Long documentID;
    @JsonProperty("document_name")
    private String documentName;
    @JsonProperty("update_rule")
    private DocumentUpdateRule updateRule;
}
