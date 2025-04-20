package com.coze.openapi.service.service.audio;

import java.io.File;

import com.coze.openapi.api.AudioVoiceAPI;
import com.coze.openapi.client.audio.voices.CloneVoiceReq;
import com.coze.openapi.client.audio.voices.CloneVoiceResp;
import com.coze.openapi.client.audio.voices.ListVoiceReq;
import com.coze.openapi.client.audio.voices.ListVoiceResp;
import com.coze.openapi.client.audio.voices.model.Voice;
import com.coze.openapi.client.common.pagination.PageFetcher;
import com.coze.openapi.client.common.pagination.PageNumBasedPaginator;
import com.coze.openapi.client.common.pagination.PageRequest;
import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.service.utils.Utils;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class VoiceService {
  private final AudioVoiceAPI api;

  public VoiceService(AudioVoiceAPI api) {
    this.api = api;
  }

  public CloneVoiceResp clone(CloneVoiceReq req) {
    MediaType textPlainType = MediaType.parse("text/plain");
    RequestBody voiceName = RequestBody.create(textPlainType, req.getVoiceName());
    RequestBody audioFormat = RequestBody.create(textPlainType, req.getAudioFormat().getValue());

    RequestBody language = null;
    if (req.getLanguage() != null) {
      language = RequestBody.create(textPlainType, req.getLanguage().getValue());
    }
    RequestBody voiceID = null;
    if (req.getVoiceID() != null) {
      voiceID = RequestBody.create(textPlainType, req.getVoiceID());
    }
    RequestBody previewText = null;
    if (req.getPreviewText() != null) {
      previewText = RequestBody.create(textPlainType, req.getPreviewText());
    }
    RequestBody text = null;
    if (req.getText() != null) {
      text = RequestBody.create(textPlainType, req.getText());
    }
    File file = new File(req.getFilePath());
    MediaType formDataType = MediaType.parse("multipart/form-data");
    RequestBody fileBody = RequestBody.create(formDataType, file);
    MultipartBody.Part filePart =
        MultipartBody.Part.createFormData("file", file.getName(), fileBody);

    return Utils.execute(
            api.clone(filePart, voiceName, audioFormat, language, voiceID, previewText, text, req))
        .getData();
  }

  public PageResp<Voice> list(ListVoiceReq req) {
    if (req == null) {
      throw new IllegalArgumentException("req is required");
    }

    Integer pageNum = req.getPageNum();
    Integer pageSize = req.getPageSize();
    Boolean filterSystemVoice = req.getFilterSystemVoice();

    // 创建分页获取器
    PageFetcher<Voice> pageFetcher =
        request -> {
          ListVoiceResp resp =
              Utils.execute(
                      api.list(filterSystemVoice, request.getPageNum(), request.getPageSize(), req))
                  .getData();

          return PageResponse.<Voice>builder()
              .hasMore(resp.getVoiceList().size() == request.getPageSize())
              .data(resp.getVoiceList())
              .pageNum(request.getPageNum())
              .pageSize(request.getPageSize())
              .logID(resp.getLogID())
              .build();
        };

    // 创建分页器
    PageNumBasedPaginator<Voice> paginator = new PageNumBasedPaginator<>(pageFetcher, pageSize);

    // 获取当前页数据
    PageRequest initialRequest = PageRequest.builder().pageNum(pageNum).pageSize(pageSize).build();

    PageResponse<Voice> currentPage = pageFetcher.fetch(initialRequest);

    return PageResp.<Voice>builder()
        .items(currentPage.getData())
        .iterator(paginator)
        .hasMore(currentPage.isHasMore())
        .logID(currentPage.getLogID())
        .build();
  }
}
