package com.coze.openapi.client.bots.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BotModelInfo {
    @JsonProperty("model_id")
    private String modelID;

    @JsonProperty("model_name")
    private String modelName;
} 