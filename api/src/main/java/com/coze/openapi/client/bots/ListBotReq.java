package com.coze.openapi.client.bots;

import com.coze.openapi.client.common.BaseReq;

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
public class ListBotReq extends BaseReq {
  private String spaceID;
  private Integer pageNum;
  private Integer pageSize;
}
