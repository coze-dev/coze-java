package com.coze.openapi.client.common.pagination_save;

import java.util.Iterator;
public abstract class PageBase<T> {
    protected abstract PageResponse<T> fetchNextPage(String cursor) throws Exception;

    public Iterator<T> iterator(String cursor) throws Exception {
        PageResponse<T> firstPage = fetchNextPage(cursor);
        return new PageIterator<>(this, firstPage);
    }

    protected static Integer parsePageCursor(String cursor) {
        Integer pageCursor = 1;
        if (cursor != null) {
            try{
                pageCursor = Integer.parseInt(cursor);
            } catch (NumberFormatException e) {
                pageCursor = 1;
            }
        }
        return pageCursor;
    }
} 