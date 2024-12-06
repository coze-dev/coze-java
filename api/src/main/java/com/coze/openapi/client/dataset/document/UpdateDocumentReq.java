package com.coze.openapi.client.dataset.document;

import com.coze.openapi.client.dataset.document.model.DocumentUpdateRule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import com.coze.openapi.client.common.BaseReq;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class UpdateDocumentReq extends BaseReq {
    /**
     * The ID of the knowledge base file.
     */
    @NonNull
    @JsonProperty("document_id")
    private Long documentID;
    /**
     * The new name of the knowledge base file.
     */
    @JsonProperty("document_name")
    private String documentName;

    /**
     * The update strategy for online web pages. Defaults to no automatic updates.
     * For detailed information, refer to the UpdateRule object.
     */
    @JsonProperty("update_rule")
    private DocumentUpdateRule updateRule;
}
