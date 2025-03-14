/**
 * 知识库信息类。
 */
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
public class BotKnowledgeInfo {
    /**
     * 知识ID。
     *
     * <p>示例：738694398580390*****
     */
    @JsonProperty("id")
    private String id;

    /**
     * 知识名称。
     *
     * <p>示例：text
     */
    @JsonProperty("name")
    private String name;
}