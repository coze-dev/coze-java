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
public class UpdateDocumentReq {
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
