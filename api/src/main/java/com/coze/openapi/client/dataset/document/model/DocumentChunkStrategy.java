package com.coze.openapi.client.dataset.document.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Getter
@Builder(builderClassName = "DocumentChunkStrategyBuilder")
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class DocumentChunkStrategy {
  /**
   * 分段设置。取值包括： 0：自动分段与清洗。采用扣子预置规则进行数据分段与处理。 1：自定义。此时需要通过 separator、max_tokens、remove_extra_spaces 和
   * remove_urls_emails 分段规则细节。
   */
  @JsonProperty("chunk_type")
  private Integer chunkType;

  /** 最大分段长度，取值范围为 100~2000。 在 chunk_type=1 时必选。 */
  @JsonProperty("max_tokens")
  private Integer maxTokens;

  /** 是否自动过滤连续的空格、换行符和制表符。取值包括： true：自动过滤 false：（默认）不自动过滤 在 chunk_type=1 时生效。 */
  @JsonProperty("remove_extra_spaces")
  private Boolean removeExtraSpaces;

  /** 是否自动过滤所有 URL 和电子邮箱地址。取值包括： true：自动过滤 false：（默认）不自动过滤 在 chunk_type=1 时生效。 */
  @JsonProperty("remove_urls_emails")
  private Boolean removeUrlsEmails;

  /** 分段标识符。 在 chunk_type=1 时必选。 */
  @JsonProperty("separator")
  private String separator;

  public static class DocumentChunkStrategyBuilder {
    public DocumentChunkStrategy build() {
      if (this.chunkType == null) {
        throw new IllegalStateException("chunkType is required");
      }
      if (this.chunkType == 1) {
        if (maxTokens == null
            || separator == null
            || removeExtraSpaces == null
            || removeUrlsEmails == null) {
          throw new IllegalStateException("All fields must be non-null when chunk_type is 1");
        }
      }
      DocumentChunkStrategy strategy = new DocumentChunkStrategy();
      strategy.setChunkType(chunkType);
      strategy.setMaxTokens(maxTokens);
      strategy.setRemoveExtraSpaces(removeExtraSpaces);
      strategy.setRemoveUrlsEmails(removeUrlsEmails);
      strategy.setSeparator(separator);
      return strategy;
    }
  }

  /**
   * 创建自动分段的 DocumentChunkStrategy 实例。
   *
   * @return DocumentChunkStrategy 实例
   */
  public static DocumentChunkStrategy buildAuto() {
    return DocumentChunkStrategy.builder().chunkType(0).build();
  }

  /**
   * 创建自定义分段的 DocumentChunkStrategy 实例。
   *
   * @param maxTokens 最大分段长度
   * @param separator 分段标识符
   * @param removeExtraSpaces 是否自动过滤连续空格
   * @param removeUrlsEmails 是否自动过滤 URL 和电子邮箱
   * @return DocumentChunkStrategy 实例
   */
  public static DocumentChunkStrategy buildCustom(
      int maxTokens, String separator, boolean removeExtraSpaces, boolean removeUrlsEmails) {
    return DocumentChunkStrategy.builder()
        .chunkType(1)
        .maxTokens(maxTokens)
        .separator(separator)
        .removeExtraSpaces(removeExtraSpaces)
        .removeUrlsEmails(removeUrlsEmails)
        .build();
  }
}
