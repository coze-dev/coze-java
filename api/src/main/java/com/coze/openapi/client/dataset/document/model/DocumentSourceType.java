package com.coze.openapi.client.dataset.document.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class DocumentSourceType {
  /** File upload 文件上传 */
  public static final DocumentSourceType LOCAL_FILE = new DocumentSourceType(0);
  /** Web page 网页 */
  public static final DocumentSourceType ONLINE_WEB = new DocumentSourceType(1);
  /** API 接口 */
  public static final DocumentSourceType CUSTOM = new DocumentSourceType(2);
  /** Third party 第三方 */
  public static final DocumentSourceType THIRD_PARTY = new DocumentSourceType(3);
  /** Front-end crawling 前端爬虫 */
  public static final DocumentSourceType FRONT_CRAWL = new DocumentSourceType(4);
  /** OpenAPI Upload file_id OpenAPI上传文件ID */
  public static final DocumentSourceType UPLOAD_FILE_ID = new DocumentSourceType(5);
  /** Notion */
  public static final DocumentSourceType NOTION = new DocumentSourceType(101);
  /** Google Drive */
  public static final DocumentSourceType GOOGLE_DRIVE = new DocumentSourceType(102);
  /** Feishu Web 飞书网页 */
  public static final DocumentSourceType FEISHU_WEB = new DocumentSourceType(103);
  /** Lark Web Lark网页 */
  public static final DocumentSourceType LARK_WEB = new DocumentSourceType(104);

  @JsonValue private final Integer value;

  private DocumentSourceType(Integer value) {
    this.value = value;
  }

  @JsonCreator
  public static DocumentSourceType fromValue(Integer value) {
    DocumentSourceType[] types = {
      LOCAL_FILE,
      ONLINE_WEB,
      CUSTOM,
      THIRD_PARTY,
      FRONT_CRAWL,
      UPLOAD_FILE_ID,
      NOTION,
      GOOGLE_DRIVE,
      FEISHU_WEB,
      LARK_WEB
    };
    for (DocumentSourceType type : types) {
      if (type.value.equals(value)) {
        return type;
      }
    }
    return new DocumentSourceType(value);
  }
}
