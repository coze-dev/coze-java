package com.coze.openapi.client.bots.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleBot {
    @JsonProperty("bot_id")
    private String botID;

    @JsonProperty("bot_name")
    private String botName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("icon_url")
    private String iconUrl;

    @JsonProperty("publish_time")
    private String publishTime;
} 