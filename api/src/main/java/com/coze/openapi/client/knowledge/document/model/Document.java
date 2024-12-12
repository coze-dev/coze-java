package com.coze.openapi.client.knowledge.document.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @JsonProperty("document_id")
    private String documentId;

    @JsonProperty("char_count")
    private int charCount;

    @JsonProperty("chunk_strategy")
    private DocumentChunkStrategy chunkStrategy;

    @JsonProperty("create_time")
    private int createTime;

    @JsonProperty("update_time")
    private int updateTime;

    @JsonProperty("format_type")
    private DocumentFormatType formatType;

    @JsonProperty("hit_count")
    private int hitCount;

    @JsonProperty("name")
    private String name;

    @JsonProperty("size")
    private int size;

    @JsonProperty("slice_count")
    private int sliceCount;

    @JsonProperty("source_type")
    private DocumentSourceType sourceType;

    @JsonProperty("status")
    private DocumentStatus status;

    @JsonProperty("type")
    private String type;

    @JsonProperty("update_interval")
    private int updateInterval;

    @JsonProperty("update_type")
    private DocumentUpdateType updateType;
} 