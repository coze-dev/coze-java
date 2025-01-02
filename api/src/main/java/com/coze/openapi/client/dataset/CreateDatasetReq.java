package com.coze.openapi.client.dataset;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.dataset.document.model.DocumentFormatType;
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
public class CreateDatasetReq extends BaseReq {
  @JsonProperty("name")
  private String name;

  @JsonProperty("space_id")
  private String spaceID;

  @JsonProperty("format_type")
  private DocumentFormatType formatType;

  @JsonProperty("description")
  private String description;

  @JsonProperty("file_id")
  private String fileID;
}
