package com.coze.openapi.service.service.file;

import java.io.File;

import com.coze.openapi.api.FileAPI;
import com.coze.openapi.client.files.model.FileInfo;
import com.coze.openapi.service.utils.Utils;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileService {
    private final FileAPI api;

    public FileService(FileAPI api) {
        this.api = api;
    }

    public FileInfo upload(String filePath){
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        return Utils.execute(api.uploadFile(body)).getData();
    }

    public FileInfo retrieve(String fileID) {
        return Utils.execute(api.retrieveFile(fileID)).getData();
    }   

}
