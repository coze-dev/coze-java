package com.coze.openapi.client.audio.speech;

import com.coze.openapi.client.common.FileResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import okhttp3.ResponseBody;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CreateSpeechResp extends FileResponse{
    public CreateSpeechResp(ResponseBody response) {
        super(response);
    }
}
