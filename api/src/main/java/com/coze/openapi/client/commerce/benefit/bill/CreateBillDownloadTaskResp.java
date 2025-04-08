package com.coze.openapi.client.commerce.benefit.bill;

import com.coze.openapi.client.commerce.benefit.bill.model.BillTaskInfo;
import com.coze.openapi.client.common.BaseResp;
import com.fasterxml.jackson.annotation.JsonInclude;

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
public class CreateBillDownloadTaskResp extends BaseResp {
  private BillTaskInfo billTaskInfo;
}
