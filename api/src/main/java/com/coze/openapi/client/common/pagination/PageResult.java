package com.coze.openapi.client.common.pagination;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private Integer total;
    private List<T> items;
    private PageIterator<T> iterator;
    private Boolean hasMore;
    private String nextCursor;
}
