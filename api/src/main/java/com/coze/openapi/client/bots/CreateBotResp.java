package com.coze.openapi.client.bots;

import com.coze.openapi.client.common.BaseResp;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateBotResp extends BaseResp{
    @JsonProperty("bot_id")
    String botID;
}
