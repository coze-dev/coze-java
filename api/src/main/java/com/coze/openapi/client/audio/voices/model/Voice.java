package com.coze.openapi.client.audio.voices.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Voice {
    /**
     * The id of voice.
     */
    @JsonProperty("voice_id")
    private String voiceId;

    /**
     * The name of voice.
     */
    @JsonProperty("name")
    private String name;

    /**
     * If is system voice.
     */
    @JsonProperty("is_system_voice")
    private boolean isSystemVoice;

    /**
     * Language code.
     */
    @JsonProperty("language_code")
    private String languageCode;

    /**
     * Language name.
     */
    @JsonProperty("language_name")
    private String languageName;

    /**
     * Preview text for the voice.
     */
    @JsonProperty("preview_text")
    private String previewText;

    /**
     * Preview audio URL for the voice.
     */
    @JsonProperty("preview_audio")
    private String previewAudio;

    /**
     * Number of remaining training times available for current voice.
     */
    @JsonProperty("available_training_times")
    private int availableTrainingTimes;

    /**
     * Voice creation timestamp.
     */
    @JsonProperty("create_time")
    private int createTime;

    /**
     * Voice last update timestamp.
     */
    @JsonProperty("update_time")
    private int updateTime;
} 