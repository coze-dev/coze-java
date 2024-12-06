package com.coze.openapi.client.audio.speech;

import com.coze.openapi.client.common.FileResponse;

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import okhttp3.ResponseBody;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CreateSpeechResp extends FileResponse{
    public CreateSpeechResp(ResponseBody response) {
        super(response);
    }
}
