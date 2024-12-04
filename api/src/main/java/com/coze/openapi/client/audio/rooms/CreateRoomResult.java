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
public class CreateRoomResult {
    /**
     * 房间 id
     */
    @JsonProperty("room_id")
    private String roomID;

    /**
     * app_id
     */
    @JsonProperty("app_id")
    private String appID;

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