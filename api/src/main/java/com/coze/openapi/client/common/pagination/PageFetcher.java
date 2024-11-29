package com.coze.openapi.client.common.pagination;


@FunctionalInterface
public interface PageFetcher<T> {
    PageResponse<T> fetch(PageRequest request);
} 