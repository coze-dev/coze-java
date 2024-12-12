package com.coze.openapi.client.common.pagination;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    @JsonProperty("has_more")
    private boolean hasMore;
    
    @JsonProperty("next_cursor")
    private String nextCursor;
    
    @JsonProperty("data")
    private List<T> data;
} 