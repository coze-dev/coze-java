package com.coze.openapi.client.dataset;

import java.util.List;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProcessDatasetReq extends BaseReq {
  @NonNull @JsonIgnore private String datasetID;

  /*
   * The IDs of the documents
   */
  @JsonProperty("document_ids")
  private List<String> documentIDs;
}
