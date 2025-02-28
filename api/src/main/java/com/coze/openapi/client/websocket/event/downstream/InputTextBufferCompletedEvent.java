package com.coze.openapi.client.websocket.event.downstream;

import com.coze.openapi.client.websocket.common.BaseEvent;
import com.coze.openapi.client.websocket.event.EventType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
// event_type: input_text_buffer.completed
public class InputTextBufferCompletedEvent extends BaseEvent {
    @JsonProperty("event_type")
    @Builder.Default
    private final String eventType = EventType.INPUT_TEXT_BUFFER_COMPLETED;


} 