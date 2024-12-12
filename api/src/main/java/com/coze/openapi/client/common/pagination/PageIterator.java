package com.coze.openapi.client.common.pagination;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.coze.openapi.client.exception.CozeApiExcetion;

public class PageIterator<T> implements Iterator<T> {
    private final PaginationBase<T> paginationBase;
    private Iterator<T> currentIterator;
    private PageResponse<T> currentPage;
    
    public PageIterator(PaginationBase<T> paginationBase, PageResponse<T> firstPage) {
        this.paginationBase = paginationBase;
        this.currentPage = firstPage;
        this.currentIterator = firstPage.getData().iterator();
    }
    
    @Override
    public boolean hasNext() {
        if (currentIterator.hasNext()) {
            return true;
        }
        if (currentPage.isHasMore()) {
            try {
                currentPage = paginationBase.fetchNextPage(currentPage.getNextCursor());
                currentIterator = currentPage.getData().iterator();
                return currentIterator.hasNext();
            } catch (CozeApiExcetion e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("Failed to fetch next page", e);
            }
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