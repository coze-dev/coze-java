package com.coze.openapi.client.dataset;

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
public class UpdateDatasetReq extends BaseReq {
  @JsonIgnore private String datasetID;

  /*
   * The name of the dataset
   */
  @JsonProperty("name")
  private String name;

  /*
   * The description of the dataset
   */
  @JsonProperty("description")
  private String description;

  /*
   * The ID of the icon file, uploaded by `coze.files.upload`
   */
  @JsonProperty("file_id")
  private String fileID;
}
