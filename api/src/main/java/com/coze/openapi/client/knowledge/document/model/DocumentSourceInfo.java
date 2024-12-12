package com.coze.openapi.client.knowledge.document.model;
import java.util.Base64;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


@Setter(AccessLevel.PRIVATE)
@Getter
public class DocumentSourceInfo {
    /**
     * 本地文件的 Base64 编码。
     * 上传本地文件时必选
     */
    @JsonProperty("file_base64")
    private String fileBase64;

    /**
     * 本地文件的格式，即文件后缀，例如 txt。格式支持 pdf、txt、doc、docx 类型。
     * 上传的文件类型应与知识库类型匹配，例如 txt 文件只能上传到文档类型的知识库中。
     * 上传本地文件时必选
     */
    @JsonProperty("file_type")
    private String fileType;

    /**
     * 网页的 URL 地址。
     * 上传网页时必选
     */
    @JsonProperty("web_url")
    private String webUrl;

    /**
     * 文件的上传方式。支持设置为 1，表示上传在线网页。
     * 上传在线网页时必选
     */
    @JsonProperty("document_source")
    private Integer documentSource;

    public static DocumentSourceInfo buildLocalFile(String content, String fileType) {
        String encodedContent = Base64.getEncoder().encodeToString(content.getBytes());
        DocumentSourceInfo info = new DocumentSourceInfo();
        info.setFileBase64(encodedContent);
        info.setFileType(fileType);
        return info;
    }

    public static DocumentSourceInfo buildWebPage(String url) {
        DocumentSourceInfo info = new DocumentSourceInfo();
        info.setWebUrl(url);
        info.setDocumentSource(1);
        return info;
    }
} 