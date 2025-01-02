package com.coze.openapi.client.dataset.image.model;

import com.coze.openapi.client.dataset.document.model.DocumentSourceType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
  /*
   * The ID of the image.
   */
  @JsonProperty("document_id")
  private String documentID;

  /*
   * The URL of the image.
   */
  @JsonProperty("url")
  private String url;

  /*
   * The name of the image.
   */
  @JsonProperty("name")
  private String name;

  /*
   * The size of the image, in bytes.
   */
  @JsonProperty("size")
  private Integer size;

  /*
   * File format, i.e., file extension, such as jpg, png.
   */
  @JsonProperty("type")
  private String type;

  /*
   * Status of the file. Values include: 0: In processing, 1: Completed, 9: Processing failed, it is recommended to re-upload
   */
  @JsonProperty("status")
  private PhotoStatus status;

  /*
   * Image description information.
   */
  @JsonProperty("caption")
  private String caption;

  /*
   * The ID of the creator.
   */
  @JsonProperty("creator_id")
  private String creatorID;

  /*
   * The source type of the image.
   */
  @JsonProperty("source_type")
  private DocumentSourceType sourceType;

  /*
   * The creation time of the image.
   */
  @JsonProperty("create_time")
  private Integer createTime;

  /*
   * The update time of the image.
   */
  @JsonProperty("update_time")
  private Integer updateTime;
}
