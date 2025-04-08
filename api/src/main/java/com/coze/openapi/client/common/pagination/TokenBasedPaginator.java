package com.coze.openapi.client.common.pagination;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.slf4j.Logger;

import com.coze.openapi.service.service.common.CozeLoggerFactory;

public class TokenBasedPaginator<T> implements Iterator<T> {
  private final PageFetcher<T> pageFetcher;
  private static final Logger logger = CozeLoggerFactory.getLogger();
  private final int pageSize;
  private Iterator<T> currentIterator;
  private PageResponse<T> currentPage;
  private String pageToken;

  public TokenBasedPaginator(PageFetcher<T> pageFetcher, int pageSize) {
    this.pageFetcher = pageFetcher;
    this.pageSize = pageSize;
  }

  private void fetchNextPage() {
    try {
      PageRequest request = PageRequest.builder().pageToken(pageToken).pageSize(pageSize).build();
      currentPage = pageFetcher.fetch(request);
      logger.info(
          "Fetched page: " + pageToken + " success, got" + currentPage.getData().size() + " items");
      currentIterator = currentPage.getData().iterator();
      pageToken = currentPage.getPageToken();
    } catch (Exception e) {
      throw e;
    }
  }

  public void setCurrentPage(PageResponse<T> currentPage) {
    this.currentPage = currentPage;
    this.pageToken = currentPage.getPageToken();
    this.currentIterator = currentPage.getData().iterator();
  }

  @Override
  public boolean hasNext() {
    if (currentIterator == null) {
      fetchNextPage();
      return currentIterator.hasNext();
    }
    if (currentIterator.hasNext()) {
      return true;
    }
    if (currentPage.isHasMore() && pageToken != null) {
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
