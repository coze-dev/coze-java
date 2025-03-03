package com.coze.openapi.service.service.audio;

import java.io.File;

import com.coze.openapi.api.AudioTranscriptionAPI;
import com.coze.openapi.client.audio.transcriptions.CreateTranscriptionsReq;
import com.coze.openapi.client.audio.transcriptions.CreateTranscriptionsResp;
import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.service.utils.Utils;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class TranscriptionService {
  private final AudioTranscriptionAPI roomApi;

  public TranscriptionService(AudioTranscriptionAPI roomApi) {
    this.roomApi = roomApi;
  }

  public CreateTranscriptionsResp create(CreateTranscriptionsReq req) {
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
  private CreateTranscriptionsResp uploadFile(Object fileSource, String filename, BaseReq req) {
    RequestBody requestFile;
    if (fileSource instanceof File) {
      MediaType mediaType = MediaType.parse("multipart/form-data");
      requestFile = RequestBody.create(mediaType, (File) fileSource);
    } else {
      MediaType mediaType = MediaType.parse("multipart/form-data");
      requestFile = RequestBody.create(mediaType, (byte[]) fileSource);
    }

    MultipartBody.Part body = MultipartBody.Part.createFormData("file", filename, requestFile);

    BaseResponse<CreateTranscriptionsResp> resp = Utils.execute(roomApi.create(body, req));
    CreateTranscriptionsResp data = resp.getData();
    data.setLogID(resp.getLogID());
    return data;
  }
}
