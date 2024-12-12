package com.coze.openapi.client.bots;

import com.coze.openapi.client.bots.model.SimpleBot;    
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListBotResp {
    @JsonProperty("space_bots")
    private List<SimpleBot> bots;

    @JsonProperty("total")
    private Integer total;
}
