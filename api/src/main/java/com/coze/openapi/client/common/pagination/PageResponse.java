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
    
    @JsonProperty("page_num")
    private Integer pageNum;
    
    @JsonProperty("page_size")
    private Integer pageSize;
    
    @JsonProperty("total")
    private Integer total;
    
    @JsonProperty("data")
    private List<T> data;

    @JsonProperty("last_token")
    private String lastToken;

    @JsonProperty("next_token")
    private String nextToken;
}
