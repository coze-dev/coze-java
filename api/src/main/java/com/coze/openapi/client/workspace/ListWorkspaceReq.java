/* (C)2024 */
package com.coze.openapi.client.workspace;

import com.coze.openapi.client.common.BaseReq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ListWorkspaceReq extends BaseReq {
  @NonNull @Builder.Default private Integer pageNum = 1;
  @NonNull @Builder.Default private Integer pageSize = 20;

  public static ListWorkspaceReq of(Integer pageNum, Integer pageSize) {
    return ListWorkspaceReq.builder().pageNum(pageNum).pageSize(pageSize).build();
  }
}
