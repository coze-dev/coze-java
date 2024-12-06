package com.coze.openapi.client.common;

import lombok.ToString;
import okhttp3.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FileResponse extends BaseResp {
    private final ResponseBody response;

    public FileResponse(ResponseBody response) {
        this.response = response;
    }


    public void writeToFile(String filePath) throws IOException {
        File file = new File(filePath);
        try (InputStream inputStream = response.byteStream();
            FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        }
    }
}
