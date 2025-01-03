package com.coze.openapi.client.bots;

import com.coze.openapi.client.bots.model.Bot;
import com.coze.openapi.client.common.BaseResp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RetrieveBotResp extends BaseResp {
  private Bot bot;
}
