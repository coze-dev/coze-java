package com.coze.openapi.client.audio.rooms;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomResp {
    /**
     * 房间 id
     */
    @JsonProperty("room_id")
    private String roomId;

    /**
     * app_id
     */
    @JsonProperty("app_id")
    private String appId;

    /**
     * token
     */
    @JsonProperty("token")
    private String token;

    /**
     * uid
     */
    @JsonProperty("uid")
    private String uid;
}