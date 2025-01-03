package com.coze.openapi.client.dataset.document.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public class DocumentFormatType {
  /** Document type, such as txt, pdf, online web pages, etc. 文档类型，例如 txt 、pdf 、在线网页等格式均属于文档类型。 */
  public static final DocumentFormatType DOCUMENT = new DocumentFormatType(0);
  /** Spreadsheet type, such as xls spreadsheets, etc. 表格类型，例如 xls 表格等格式属于表格类型。 */
  public static final DocumentFormatType SPREADSHEET = new DocumentFormatType(1);
  /** Image type, such as png images, etc. 照片类型，例如 png 图片等格式属于照片类型。 */
  public static final DocumentFormatType IMAGE = new DocumentFormatType(2);

  @JsonValue private final Integer value;

  private DocumentFormatType(Integer value) {
    this.value = value;
  }

  @JsonCreator
  public static DocumentFormatType fromValue(Integer value) {
    DocumentFormatType[] types = {DOCUMENT, SPREADSHEET, IMAGE};
    for (DocumentFormatType type : types) {
      if (type.value.equals(value)) {
        return type;
      }
    }
    return new DocumentFormatType(value);
  }
}
