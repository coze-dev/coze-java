package com.coze.openapi.client.dataset.image;

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
public class UpdateImageReq extends BaseReq {
  @JsonIgnore private String datasetID;

  @JsonIgnore private String documentID;

  /*
   * The description of the image.
   */
  @JsonProperty("caption")
  private String caption;
}
