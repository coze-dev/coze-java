package com.coze.openapi.client.dataset;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProcessDatasetReq extends BaseReq {
  @NotNull @JsonIgnore private String datasetID;

  /*
   * The IDs of the documents
   */
  @JsonProperty("document_ids")
  private List<String> documentIDs;
}
