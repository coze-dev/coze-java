package com.coze.openapi.client.connversations.message.model;

import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageObjectString {

  /** The content type of the multimodal message. 多模态消息内容类型 */
  @JsonProperty("type")
  private MessageObjectStringType type;

  /** Text content. Required when type is text. 文本内容。 */
  @JsonProperty("text")
  private String text;

  /** The ID of the file or image content. 在 type 为 file 或 image 时，file_id 和 file_url 应至少指定一个。 */
  @JsonProperty("file_id")
  private String fileID;

  /**
   * The online address of the file or image content. Must be a valid address that is publicly
   * accessible. file_id or file_url must be specified when type is file or image.
   * 文件或图片内容的在线地址。必须是可公共访问的有效地址。 在 type 为 file 或 image 时，file_id 和 file_url 应至少指定一个。
   */
  @JsonProperty("file_url")
  private String fileURL;

  public static MessageObjectString buildText(String text) {
    return MessageObjectString.builder().type(MessageObjectStringType.TEXT).text(text).build();
  }

  public static MessageObjectString buildImageByID(String fileID) {
    return buildImage(fileID, null);
  }

  public static MessageObjectString buildImageByURL(String fileURL) {
    return buildImage(null, fileURL);
  }

  private static MessageObjectString buildImage(String fileID, String fileURL) {
    if (fileID == null && fileURL == null) {
      throw new IllegalArgumentException("file_id or file_url must be specified");
    }
    return MessageObjectString.builder()
        .type(MessageObjectStringType.IMAGE)
        .fileID(fileID)
        .fileURL(fileURL)
        .build();
  }

  public static MessageObjectString buildFileByID(String fileID) {
    return buildFile(fileID, null);
  }

  public static MessageObjectString buildFileByURL(String fileURL) {
    return buildFile(null, fileURL);
  }

  private static MessageObjectString buildFile(String fileID, String fileURL) {
    if (fileID == null && fileURL == null) {
      throw new IllegalArgumentException("file_id or file_url must be specified");
    }
    return MessageObjectString.builder()
        .type(MessageObjectStringType.FILE)
        .fileID(fileID)
        .fileURL(fileURL)
        .build();
  }

  public static MessageObjectString buildAudio(String fileID, String fileURL) {
    if (fileID == null && fileURL == null) {
      throw new IllegalArgumentException("file_id or file_url must be specified");
    }
    return MessageObjectString.builder()
        .type(MessageObjectStringType.AUDIO)
        .fileID(fileID)
        .fileURL(fileURL)
        .build();
  }

  public String toJson() {
    return Utils.toJson(this);
  }
}
