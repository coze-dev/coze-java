package com.coze.openapi.client.dataset;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeleteDatasetReq extends BaseReq {
  @NonNull @JsonIgnore private String datasetID;
}
