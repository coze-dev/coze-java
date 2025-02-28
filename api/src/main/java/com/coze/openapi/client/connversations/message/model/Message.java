package com.coze.openapi.client.connversations.message.model;

import java.util.Base64;
import java.util.List;
import java.util.Map;

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
public class Message {
  /** The entity that sent this message. */
  @JsonProperty("role")
  private MessageRole role;

  /** The type of message. */
  @JsonProperty("type")
  private MessageType type;

  /**
   * The content of the message. It supports various types of content, including plain text,
   * multimodal (a mix of text, images, and files), message cards, and more.
   * 消息的内容，支持纯文本、多模态（文本、图片、文件混合输入）、卡片等多种类型的内容。
   */
  @JsonProperty("content")
  private String content;

  /** The type of message content. 消息内容的类型 */
  @JsonProperty("content_type")
  private MessageContentType contentType;

  /**
   * Additional information when creating a message, and this additional information will also be
   * returned when retrieving messages. Custom key-value pairs should be specified in Map object
   * format, with a length of 16 key-value pairs. The length of the key should be between 1 and 64
   * characters, and the length of the value should be between 1 and 512 characters.
   * 创建消息时的附加消息，获取消息时也会返回此附加消息。 自定义键值对，应指定为 Map 对象格式。长度为 16 对键值对，其中键（key）的长度范围为 1～64
   * 个字符，值（value）的长度范围为 1～512 个字符。
   */
  @JsonProperty("meta_data")
  private Map<String, String> metaData;

  @JsonProperty("id")
  private String id;

  @JsonProperty("conversation_id")
  private String conversationId;

  /**
   * section_id is used to distinguish the context sections of the session history. The same section
   * is one context.
   */
  @JsonProperty("section_id")
  private String sectionId;

  @JsonProperty("bot_id")
  private String botId;

  @JsonProperty("chat_id")
  private String chatId;

  @JsonProperty("created_at")
  private Long createdAt;

  @JsonProperty("updated_at")
  private Long updatedAt;

  @JsonProperty("reasoning_content")
  private String reasoningContent;

  public byte[] getAudio() {
    if (this.contentType == MessageContentType.AUDIO) {
      return Base64.getDecoder().decode(this.content);
    }
    return new byte[0];
  }

  public static Message buildUserQuestionText(String content) {
    return buildUserQuestionText(content, null);
  }

  public static Message buildUserQuestionText(String content, Map<String, String> metaData) {
    return Message.builder()
        .role(MessageRole.USER)
        .type(MessageType.QUESTION)
        .content(content)
        .contentType(MessageContentType.TEXT)
        .metaData(metaData)
        .build();
  }

  public static Message buildUserQuestionObjects(List<MessageObjectString> objects) {
    return buildUserQuestionObjects(objects, null);
  }

  public static Message buildUserQuestionObjects(
      List<MessageObjectString> objects, Map<String, String> metaData) {
    return Message.builder()
        .role(MessageRole.USER)
        .type(MessageType.QUESTION)
        .content(Utils.toJson(objects))
        .contentType(MessageContentType.OBJECT_STRING)
        .metaData(metaData)
        .build();
  }

  public static Message buildAssistantAnswer(String content) {
    return buildAssistantAnswer(content, null);
  }

  public static Message buildAssistantAnswer(String content, Map<String, String> metaData) {
    return Message.builder()
        .role(MessageRole.ASSISTANT)
        .type(MessageType.ANSWER)
        .content(content)
        .contentType(MessageContentType.TEXT)
        .metaData(metaData)
        .build();
  }

  public static Message fromJson(String json) {
    return Utils.fromJson(json, Message.class);
  }
}
