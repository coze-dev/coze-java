package com.coze.openapi.client.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CozeError {

    @JsonProperty("error_message")
    String errorMessage;

    @JsonProperty("error_code")
    String errorCode;

    String error;

    @JsonCreator
    public CozeError(@JsonProperty("error_message") String errorMessage,
                            @JsonProperty("error_code") String errorCode,
                            @JsonProperty("error") String error) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.error = error;
    }
}
