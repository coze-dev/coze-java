package com.coze.openapi.client.bots;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishBotReq {
    
    @JsonProperty("bot_id") 
    private String botID;
    @JsonProperty("connector_ids")
    private List<String> connectorIDs;
}
