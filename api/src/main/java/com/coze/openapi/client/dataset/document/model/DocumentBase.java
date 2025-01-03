package com.coze.openapi.client.dataset.document.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentBase {
  /** 文件名称。 */
  @NonNull
  @JsonProperty("name")
  private String name;

  /** 文件的元数据信息。详细信息可参考 DocumentSourceInfo object。 */
  @NonNull
  @JsonProperty("source_info")
  private DocumentSourceInfo sourceInfo;

  /** 在线网页的更新策略。默认不自动更新。 */
  @JsonProperty("update_rule")
  private DocumentUpdateRule updateRule;

  public static DocumentBase buildWebPage(String name, String url) {
    return DocumentBase.builder()
        .name(name)
        .sourceInfo(DocumentSourceInfo.buildWebPage(url))
        .updateRule(DocumentUpdateRule.buildNoAutoUpdate())
        .build();
  }

  public static DocumentBase buildWebPage(String name, String url, Integer interval) {
    return DocumentBase.builder()
        .name(name)
        .sourceInfo(DocumentSourceInfo.buildWebPage(url))
        .updateRule(DocumentUpdateRule.buildAutoUpdate(interval))
        .build();
  }

  public static DocumentBase buildLocalFile(String name, String content, String fileType) {
    return DocumentBase.builder()
        .name(name)
        .sourceInfo(DocumentSourceInfo.buildLocalFile(content, fileType))
        .build();
  }
}
