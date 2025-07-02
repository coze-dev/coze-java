package com.coze.openapi.client.commerce.benefit.bill;

import java.util.List;

import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListBillDownloadTaskReq extends BaseReq {
  @JsonProperty("task_ids")
  private List<String> taskIds;

  @JsonProperty("page_num")
  private Integer pageNum;

  @JsonProperty("page_size")
  private Integer pageSize;
}
