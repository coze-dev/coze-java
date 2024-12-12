/* (C)2024 */
package com.coze.openapi.service.service.file;

import java.io.File;

import com.coze.openapi.api.FileAPI;
import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.files.RetrieveFileReq;
import com.coze.openapi.client.files.RetrieveFileResp;
import com.coze.openapi.client.files.UploadFileReq;
import com.coze.openapi.client.files.UploadFileResp;
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
   * <p>Local files cannot be used directly in messages. Before creating messages or conversations,
   * you need to call this interface to upload local files to the platform first. After uploading
   * the file, you can use it directly in multimodal content in messages by specifying the file_id.
   *
   * <p>docs en: https://www.coze.com/docs/developer_guides/upload_files docs zh:
   * https://www.coze.cn/docs/developer_guides/upload_files
   */
  public UploadFileResp upload(UploadFileReq req) {
    if (req.getFilePath() != null) {
      File file = new File(req.getFilePath());
      return uploadFile(file, file.getName(), req);
    }
    if (req.getFileBytes() != null) {
      return uploadFile(req.getFileBytes(), req.getFileName(), req);
    }
    if (req.getFile() != null) {
      return uploadFile(req.getFile(), req.getFileName(), req);
    }
    throw new IllegalArgumentException("file source is required");
  }

  /** Internal unified upload processing method */
  private UploadFileResp uploadFile(Object fileSource, String filename, BaseReq req) {
    RequestBody requestFile;
    if (fileSource instanceof File) {
      requestFile = RequestBody.create((File) fileSource, MediaType.parse("multipart/form-data"));
    } else {
      requestFile = RequestBody.create((byte[]) fileSource, MediaType.parse("multipart/form-data"));
    }

    MultipartBody.Part body = MultipartBody.Part.createFormData("file", filename, requestFile);

    BaseResponse<FileInfo> resp = Utils.execute(api.upload(body, req));
    return UploadFileResp.builder().fileInfo(resp.getData()).logID(resp.getLogID()).build();
  }

  public RetrieveFileResp retrieve(RetrieveFileReq req) {
    BaseResponse<FileInfo> resp = Utils.execute(api.retrieve(req.getFileID(), req));
    return RetrieveFileResp.builder().fileInfo(resp.getData()).logID(resp.getLogID()).build();
  }
}
