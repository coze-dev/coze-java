package com.coze.openapi.client.dataset;

import java.util.List;

import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.dataset.model.Dataset;
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
public class ListDatasetResp extends BaseResp {
  @JsonProperty("total_count")
  private Integer totalCount;

  @JsonProperty("dataset_list")
  private List<Dataset> datasetList;
}
