/* (C)2024 */
package com.coze.openapi.client.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class BaseResp {
  private String logID;
}
