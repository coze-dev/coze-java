package com.coze.openapi.client.common.pagination;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.coze.openapi.client.exception.CozeApiExcetion;

public class PageIterator<T> implements Iterator<T> {
    private final PageBase<T> pageBase;
    private Iterator<T> currentIterator;
    private PageResponse<T> currentPage;
    
    public PageIterator(PageBase<T> pageBase, PageResponse<T> firstPage) {
        this.pageBase = pageBase;
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
                currentPage = pageBase.fetchNextPage(currentPage.getNextCursor());
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