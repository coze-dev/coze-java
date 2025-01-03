package com.coze.openapi.client.dataset.image;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateImageReq extends BaseReq {
  @JsonIgnore @NonNull private String datasetID;

  @JsonIgnore @NonNull private String documentID;

  /*
   * The description of the image.
   */
  @JsonProperty("caption")
  private String caption;
}
