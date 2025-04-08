package com.coze.openapi.client.commerce.benefit.bill;

import com.coze.openapi.client.common.BaseReq;
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
public class CreateBillDownloadTaskReq extends BaseReq {
  @JsonProperty("started_at")
  private long startedAt;

  @JsonProperty("ended_at")
  private long endedAt;
}
