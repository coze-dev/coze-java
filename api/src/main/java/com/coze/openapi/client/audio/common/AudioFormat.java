package com.coze.openapi.client.audio.common;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Audio format types.
 */
public enum AudioFormat {
    WAV("wav"),
    PCM("pcm"),
    OGG_OPUS("ogg_opus"),
    M4A("m4a"),
    AAC("aac"),
    MP3("mp3");

    private final String value;

    AudioFormat(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static AudioFormat fromString(String value) {
        for (AudioFormat format : AudioFormat.values()) {
            if (format.value.equals(value)) {
                return format;
            }
        }
        throw new IllegalArgumentException("Unknown AudioFormat: " + value);
    }
} 