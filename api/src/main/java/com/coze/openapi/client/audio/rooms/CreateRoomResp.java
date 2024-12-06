package com.coze.openapi.client.audio.rooms;


import com.coze.openapi.client.common.BaseResp;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateRoomResp extends BaseResp{
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