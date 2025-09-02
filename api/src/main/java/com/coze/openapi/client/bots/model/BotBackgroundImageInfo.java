package com.coze.openapi.client.bots.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BotBackgroundImageInfo {
  @JsonProperty("web_background_image")
  private BackgroundImageDetail webBackgroundImage;

  @JsonProperty("mobile_background_image")
  private BackgroundImageDetail mobileBackgroundImage;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class BackgroundImageDetail {
    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("theme_color")
    private String themeColor;

    @JsonProperty("canvas_position")
    private CanvasPosition canvasPosition;

    @JsonProperty("gradient_position")
    private GradientPosition gradientPosition;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CanvasPosition {
    @JsonProperty("top")
    private Double top;
    @JsonProperty("left")
    private Double left;
    @JsonProperty("width")
    private Double width;
    @JsonProperty("height")
    private Double height;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class GradientPosition {
    @JsonProperty("left")
    private Double left;
    @JsonProperty("right")
    private Double right;
  }
}
