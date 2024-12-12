package com.coze.openapi.client.knowledge.document.model;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentBase {
    @NotNull
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("source_info")
    private DocumentSourceInfo sourceInfo;

    @JsonProperty("update_rule")
    private DocumentUpdateRule updateRule;

    public static DocumentBase buildWebPage(String name, String url) {
        return DocumentBase.builder().name(name).sourceInfo(DocumentSourceInfo.buildWebPage(url)).updateRule(DocumentUpdateRule.buildNoAutoUpdate()).build();
    }

    public static DocumentBase buildWebPage(String name, String url, Integer interval) {
        return DocumentBase.builder().name(name).sourceInfo(DocumentSourceInfo.buildWebPage(url)).updateRule(DocumentUpdateRule.buildAutoUpdate(interval)).build();
    }

    public static DocumentBase buildLocalFile(String name, String content, String fileType) {
        return DocumentBase.builder().name(name).sourceInfo(DocumentSourceInfo.buildLocalFile(content, fileType)).build();
    }
} 
