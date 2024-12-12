package com.coze.openapi.client.connversations;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateConversationResp {
    @JsonProperty("id")
    private String id;  

    @JsonProperty("created_at")
    private Integer createdAt;

    @JsonProperty("meta_data")
    private Map<String, String> metaData;
}
