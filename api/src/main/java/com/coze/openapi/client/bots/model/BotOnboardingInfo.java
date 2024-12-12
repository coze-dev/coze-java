package com.coze.openapi.client.bots.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data   
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BotOnboardingInfo {
    @JsonProperty("prologue")
    private String prologue;

    @JsonProperty("suggested_questions")
    private List<String> suggestedQuestions;
} 