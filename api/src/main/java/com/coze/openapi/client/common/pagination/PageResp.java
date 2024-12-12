/* (C)2024 */
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
  private String lastID;
  private String logID;
}
