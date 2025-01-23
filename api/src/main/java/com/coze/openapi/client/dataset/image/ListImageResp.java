package com.coze.openapi.client.dataset.image;

import java.util.List;

import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.dataset.image.model.Image;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListImageResp extends BaseResp {
  @JsonProperty("photo_infos")
  private List<Image> imageInfos;

  @JsonProperty("total_count")
  private Integer totalCount;
}
