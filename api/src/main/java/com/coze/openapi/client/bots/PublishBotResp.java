package com.coze.openapi.client.bots;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishBotResp {
    @JsonProperty("bot_id") 
    private String botID;
    @JsonProperty("version")
    private String botVersion;
}
