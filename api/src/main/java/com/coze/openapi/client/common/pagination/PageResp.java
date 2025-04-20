package com.coze.openapi.client.common.pagination;

import java.util.Iterator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResp<T> {
  private Integer total;
  private List<T> items;
  private Iterator<T> iterator;
  private Boolean hasMore;
  private String lastID; //  当前页最后一条数据的 id
  private String firstID; // 当前页第一条数据的 id
  private String logID;
}
