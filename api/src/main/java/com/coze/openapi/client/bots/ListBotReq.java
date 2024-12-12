package com.coze.openapi.client.bots;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListBotReq {
    private String spaceID;
    private Integer pageNum;
    private Integer pageSize;
}
