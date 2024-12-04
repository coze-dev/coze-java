package com.coze.openapi.client.bots;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RetrieveBotReq {
    @JsonProperty("bot_id")
    private String botID;

    public static RetrieveBotReq of(String botID) {
        return RetrieveBotReq.builder().botID(botID).build();
    }
}
