package com.coze.openapi.client.dataset;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.dataset.document.model.DocumentFormatType;
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
public class ListDatasetReq extends BaseReq {
  @JsonIgnore private String spaceID;

  @JsonIgnore private String name;

  @JsonIgnore private DocumentFormatType formatType;

  @JsonIgnore private Integer pageNum;

  @JsonIgnore private Integer pageSize;
}
