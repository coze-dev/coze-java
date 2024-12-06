package com.coze.openapi.client.bots;

import com.coze.openapi.client.bots.model.Bot;
import com.coze.openapi.client.common.BaseResp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RetrieveBotResp extends BaseResp{
    private Bot bot;

    
}
