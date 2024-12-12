/* (C)2024 */
package com.coze.openapi.service.service.audio;

import java.io.File;

import org.jetbrains.annotations.NotNull;

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
    RequestBody voiceName = RequestBody.create(req.getVoiceName(), MediaType.parse("text/plain"));
    RequestBody audioFormat =
        RequestBody.create(req.getAudioFormat().getValue(), MediaType.parse("text/plain"));

    RequestBody language = null;
    if (req.getLanguage() != null) {
      language = RequestBody.create(req.getLanguage().getValue(), MediaType.parse("text/plain"));
    }
    RequestBody voiceID = null;
    if (req.getVoiceID() != null) {
      voiceID = RequestBody.create(req.getVoiceID(), MediaType.parse("text/plain"));
    }
    RequestBody previewText = null;
    if (req.getPreviewText() != null) {
      previewText = RequestBody.create(req.getPreviewText(), MediaType.parse("text/plain"));
    }
    RequestBody text = null;
    if (req.getText() != null) {
      text = RequestBody.create(req.getText(), MediaType.parse("text/plain"));
    }
    File file = new File(req.getFilePath());
    RequestBody fileBody = RequestBody.create(file, MediaType.parse("multipart/form-data"));
    MultipartBody.Part filePart =
        MultipartBody.Part.createFormData("file", file.getName(), fileBody);

    return Utils.execute(
            api.clone(filePart, voiceName, audioFormat, language, voiceID, previewText, text, req))
        .getData();
  }

  public PageResp<Voice> list(@NotNull ListVoiceReq req) {
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
