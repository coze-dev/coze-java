package com.coze.openapi.client.bots;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishBotReq {
    @NonNull
    @JsonProperty("bot_id") 
    private String botID;
    @JsonProperty("connector_ids")
    private List<String> connectorIDs;

    public static PublishBotReq of(@NotNull String botID) {
        return PublishBotReq.builder().botID(botID).build();
    }
}
