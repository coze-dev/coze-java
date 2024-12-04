package com.coze.openapi.client.knowledge.document.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DocumentFormatType {
    /**
     * Document type, such as txt, pdf, online web pages, etc.
     * 文档类型，例如 txt 、pdf 、在线网页等格式均属于文档类型。
     */
    DOCUMENT(0),

    /**
     * Spreadsheet type, such as xls spreadsheets, etc.
     * 表格类型，例如 xls 表格等格式属于表格类型。
     */
    SPREADSHEET(1),

    /**
     * Photo type, such as png images, etc.
     * 照片类型，例如 png 图片等格式属于照片类型。
     */
    IMAGE(2);

    private final int value;

    DocumentFormatType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static DocumentFormatType fromValue(int value) {
        for (DocumentFormatType type : DocumentFormatType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown DocumentFormatType value: " + value);
    }
} 