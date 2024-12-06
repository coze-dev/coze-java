package com.coze.openapi.client.dataset.document.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.AccessLevel;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class DocumentUpdateRule {
    /**
     * 在线网页是否自动更新。取值包括：
     * 0：不自动更新
     * 1：自动更新
     */
    @JsonProperty("update_type")
    private DocumentUpdateType updateType;

    /**
     * 在线网页自动更新的频率。单位为小时，最小值为 24。
     */
    @JsonProperty("update_interval")
    private int updateInterval;

    /**
     * 创建不自动更新的 DocumentUpdateRule 实例。
     * @return DocumentUpdateRule 实例
     */
    public static DocumentUpdateRule buildNoAutoUpdate() {
        DocumentUpdateRule rule = new DocumentUpdateRule();
        rule.setUpdateType(DocumentUpdateType.NO_AUTO_UPDATE);
        rule.setUpdateInterval(24);
        return rule;
    }

    /**
     * 创建自动更新的 DocumentUpdateRule 实例。
     * @param interval 更新频率
     * @return DocumentUpdateRule 实例
     */
    public static DocumentUpdateRule buildAutoUpdate(int interval) {
        DocumentUpdateRule rule = new DocumentUpdateRule();
        rule.setUpdateType(DocumentUpdateType.AUTO_UPDATE);
        rule.setUpdateInterval(interval);
        return rule;
    }
}