/* (C)2024 */
package com.coze.openapi.client.dataset.document;

import java.util.List;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class DeleteDocumentReq extends BaseReq {
  @NonNull
  @JsonProperty("document_ids")
  private List<Long> documentIDs;
}
