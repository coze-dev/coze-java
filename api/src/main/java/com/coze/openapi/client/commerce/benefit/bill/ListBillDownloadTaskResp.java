package com.coze.openapi.client.commerce.benefit.bill;

import java.util.List;

import com.coze.openapi.client.commerce.benefit.bill.model.BillTaskInfo;
import com.coze.openapi.client.common.BaseResp;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class ListBillDownloadTaskResp extends BaseResp {
  @JsonProperty("task_infos")
  private List<BillTaskInfo> taskInfos;

  @JsonProperty("total")
  private Integer total;
}
