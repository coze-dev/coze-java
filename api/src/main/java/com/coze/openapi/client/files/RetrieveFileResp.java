package com.coze.openapi.client.files;

import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.files.model.FileInfo;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RetrieveFileResp extends BaseResp {
  private FileInfo fileInfo;
}
