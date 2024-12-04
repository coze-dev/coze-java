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

    /**
     * Upload files to Coze platform.
     * 
     * Local files cannot be used directly in messages. Before creating messages or conversations,
     * you need to call this interface to upload local files to the platform first.
     * After uploading the file, you can use it directly in multimodal content in messages
     * by specifying the file_id.
     * 
     * docs en: https://www.coze.com/docs/developer_guides/upload_files
     * docs zh: https://www.coze.cn/docs/developer_guides/upload_files

     * @param filePath local file path
     */
    public FileInfo upload(String filePath) {
        File file = new File(filePath);
        return uploadFile(file, file.getName());
    }

    /**
     * Upload files by byte array.
     * When you need to upload files retrieved from other servers to Coze, 
     * instead of downloading them locally first, you can call this method to 
     * directly upload the byte array to Coze, eliminating the need for local storage.
     * 
     * docs en: https://www.coze.com/docs/developer_guides/upload_files
     * docs zh: https://www.coze.cn/docs/developer_guides/upload_files
     * 
     * @param bytes file byte array
     * @param filename file name
     */
    public FileInfo upload(byte[] bytes, String filename) {
        return uploadFile(bytes, filename);
    }

    /**
     * Upload files by File object.
     * 
     * docs en: https://www.coze.com/docs/developer_guides/upload_files
     * docs zh: https://www.coze.cn/docs/developer_guides/upload_files
     * 
     * @param file File object
     */
    public FileInfo upload(File file) {
        return uploadFile(file, file.getName());
    }

    /**
     * Internal unified upload processing method
     */
    private FileInfo uploadFile(Object fileSource, String filename) {
        RequestBody requestFile;
        if (fileSource instanceof File) {
            requestFile = RequestBody.create((File) fileSource, MediaType.parse("multipart/form-data"));
        } else {
            requestFile = RequestBody.create((byte[]) fileSource, MediaType.parse("multipart/form-data"));
        }
        
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", filename, requestFile);
        return Utils.execute(api.upload(body)).getData();
    }

    public FileInfo retrieve(String fileID) {
        return Utils.execute(api.retrieve(fileID)).getData();
    }   

}
