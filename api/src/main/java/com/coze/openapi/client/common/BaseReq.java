package com.coze.openapi.client.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class BaseReq {
  @JsonIgnore private Integer connectTimeout;
  @JsonIgnore private Integer readTimeout;
  @JsonIgnore private Integer writeTimeout;
  @JsonIgnore private String customerToken;
}
