package com.coze.openapi.client.bots;

import com.coze.openapi.client.bots.model.SimpleBot;
import com.coze.openapi.client.common.BaseResp;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class ListBotResp extends BaseResp{
    @JsonProperty("space_bots")
    private List<SimpleBot> bots;

    @JsonProperty("total")
    private Integer total;
}
