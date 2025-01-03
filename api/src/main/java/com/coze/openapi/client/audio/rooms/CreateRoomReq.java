package com.coze.openapi.client.audio.rooms;

import com.coze.openapi.client.audio.rooms.model.RoomConfig;
import com.coze.openapi.client.common.BaseReq;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateRoomReq extends BaseReq {
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
