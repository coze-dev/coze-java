package com.coze.openapi.client.bots;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.NoArgsConstructor;

import com.coze.openapi.client.common.BaseResp;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PublishBotResp extends BaseResp{
    @JsonProperty("bot_id") 
    private String botID;
    @JsonProperty("version")
    private String botVersion;
}
