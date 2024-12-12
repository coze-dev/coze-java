package com.coze.openapi.client.audio.rooms;

import com.coze.openapi.client.audio.rooms.model.RoomConfig;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomReq {
    @NonNull
    @JsonProperty("bot_id")
    private String botID;
    @JsonProperty("conversation_id")
    private String conversationID;
    @JsonProperty("voice_id")
    private String voiceID;

    @JsonProperty("config")
    private RoomConfig config;

}
