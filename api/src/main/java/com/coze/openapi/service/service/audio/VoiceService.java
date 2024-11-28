package com.coze.openapi.service.service.audio;

import java.io.File;

import org.jetbrains.annotations.NotNull;

import com.coze.openapi.api.AudioVoiceAPI;
import com.coze.openapi.client.audio.voices.CloneVoiceReq;
import com.coze.openapi.client.audio.voices.CloneVoiceResp;
import com.coze.openapi.client.audio.voices.ListVoiceReq;
import com.coze.openapi.client.audio.voices.ListVoiceResp;
import com.coze.openapi.client.audio.voices.model.Voice;
import com.coze.openapi.client.common.pagination.PageIterator;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.common.pagination.PageResult;
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
        RequestBody audioFormat = RequestBody.create(req.getAudioFormat().getValue(), MediaType.parse("text/plain"));

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
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), fileBody);

        return Utils.execute(api.cloneVoice(filePart, voiceName, audioFormat, language, voiceID, previewText, text)).getData();
    }

     public PageResult<Voice> list(@NotNull ListVoiceReq req) {
        if (req == null) {
            throw new IllegalArgumentException("req is required");
        }
        ListVoiceResp resp = Utils.execute(api.listVoice(req.getFilterSystemVoice(), req.getPageNum(), req.getPageSize())).getData();
        // 生成分页器
        VoicePage pagination = new VoicePage(api,req.getPageSize(), req.getFilterSystemVoice());

        Boolean hasMore = resp.getVoiceList().size() == req.getPageSize();
        // 构建当前页数据
        PageResponse<Voice> pageResponse = PageResponse.<Voice>builder()
            .hasMore(hasMore)
            .nextCursor(String.valueOf(req.getPageNum() + 1))
            .data(resp.getVoiceList())
            .build();

        return PageResult.<Voice>builder()
            .items(resp.getVoiceList())
            .iterator(new PageIterator<>(pagination, pageResponse))
            .hasMore(hasMore)
            .nextCursor(String.valueOf(req.getPageNum() + 1))
            .build();
    }
}