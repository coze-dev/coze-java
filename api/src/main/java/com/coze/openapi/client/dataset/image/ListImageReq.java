package com.coze.openapi.client.dataset.image;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class ListImageReq extends BaseReq {
  @JsonIgnore private String datasetID;

  @JsonIgnore private String keyword;

  @JsonIgnore private Boolean hasCaption;

  @JsonIgnore private Integer pageNum;

  @JsonIgnore private Integer pageSize;
}
