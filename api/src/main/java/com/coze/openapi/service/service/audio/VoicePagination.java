package com.coze.openapi.service.service.audio;

import com.coze.openapi.api.AudioVoiceAPI;
import com.coze.openapi.client.audio.voices.ListVoiceResp;
import com.coze.openapi.client.audio.voices.model.Voice;
import com.coze.openapi.client.common.pagination.PageResponse;
import com.coze.openapi.client.common.pagination.PaginationBase;
import com.coze.openapi.service.utils.Utils;

public class VoicePagination extends PaginationBase<Voice>{
    private final AudioVoiceAPI api;
    private final int pageSize;
    private final Boolean filterSystemVoice;

    public VoicePagination(AudioVoiceAPI api, Integer pageSize, Boolean filterSystemVoice) {
        this.api = api;
        if (pageSize != null) {
            this.pageSize = pageSize;
        } else {
            this.pageSize = 100;
        }
        this.filterSystemVoice = filterSystemVoice;
    }


    @Override
    protected PageResponse<Voice> fetchNextPage(String cursor) throws Exception {
        Integer pageCursor = parsePageCursor(cursor);

        ListVoiceResp resp = Utils.execute(api.listVoice(this.filterSystemVoice, pageCursor, this.pageSize)).getData();

        PageResponse<Voice> data = new PageResponse<>(
            this.pageSize == resp.getVoiceList().size(),
            String.valueOf(pageCursor + 1),
            resp.getVoiceList()
        );
        return data;    
    }
}
