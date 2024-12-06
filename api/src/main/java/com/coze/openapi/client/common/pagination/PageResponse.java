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
    private boolean hasMore;
    
    private Integer pageNum;
    
    private Integer pageSize;
    
    private Integer total;
    
    private List<T> data;

    private String lastID;

    private String nextID;

    private String logID;
}
