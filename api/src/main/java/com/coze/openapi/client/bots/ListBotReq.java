package com.coze.openapi.client.bots;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import com.coze.openapi.client.common.BaseReq;

import lombok.AllArgsConstructor;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ListBotReq extends BaseReq {
    private String spaceID;
    private Integer pageNum;
    private Integer pageSize;
}
