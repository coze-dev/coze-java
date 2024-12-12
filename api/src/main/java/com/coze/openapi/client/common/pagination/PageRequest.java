/* (C)2024 */
package com.coze.openapi.client.common.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {

  @JsonProperty("page_token")
  private String pageToken;

  @JsonProperty("page_num")
  private Integer pageNum;

  @JsonProperty("page_size")
  private Integer pageSize;
}
