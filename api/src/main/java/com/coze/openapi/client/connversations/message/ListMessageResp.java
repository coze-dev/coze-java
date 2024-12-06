package com.coze.openapi.client.connversations.message;

import java.util.List;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.connversations.message.model.Message;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class ListMessageResp extends BaseResponse<List<Message>> {
    @JsonProperty("has_more")
    private boolean hasMore;
    @JsonProperty("first_id")
    private String firstID;
    @JsonProperty("last_id")
    private String lastID;
}
