/* (C)2024 */
package com.coze.openapi.client.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.SuperBuilder;

// 非流式接口的默认返回值
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString(callSuper = true)
public class BaseResponse<T> extends BaseResp {
  @JsonProperty("msg")
  private String msg;

  @JsonProperty("code")
  private Integer code;

  @JsonProperty("data")
  private T data;

  @JsonProperty("detail")
  private Detail detail;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Detail {
    @JsonProperty("logid")
    private String logID;
  }
}
