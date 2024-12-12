package com.coze.openapi.client.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 非流式接口的默认返回值
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T>{
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
