package com.coze.openapi.client.common.pagination;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class PageNumBasedPaginator<T> implements Iterator<T> {
    private final PageFetcher<T> pageFetcher;
    private final int pageSize;
    private Iterator<T> currentIterator;
    private PageResponse<T> currentPage;
    private int currentPageNum = 1;

    public PageNumBasedPaginator(PageFetcher<T> pageFetcher, int pageSize) {
        this.pageFetcher = pageFetcher;
        this.pageSize = pageSize;
        this.fetchNextPage();
    }

    private void fetchNextPage() {
        try {
            PageRequest request = PageRequest.builder()
                    .pageNum(currentPageNum)
                    .pageSize(pageSize)
                    .build();
            currentPage = pageFetcher.fetch(request);
            currentIterator = currentPage.getData().iterator();
            currentPageNum++;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch page", e);
        }
    }

    @Override
    public boolean hasNext() {
        if (currentIterator.hasNext()) {
            return true;
        }
        if (currentPage.isHasMore()) {
            fetchNextPage();
            return currentIterator.hasNext();
        }
        return false;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return currentIterator.next();
    }
} 