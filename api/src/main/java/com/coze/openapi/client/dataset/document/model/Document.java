/* (C)2024 */
package com.coze.openapi.client.dataset.document.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {
  /** The ID of the file. 文件的 ID。 */
  @JsonProperty("document_id")
  private String documentID;

  /** The total character count of the file content. 文件内容的总字符数量。 */
  @JsonProperty("char_count")
  private int charCount;

  /**
   * The chunking rules. For detailed instructions, refer to the ChunkStrategy object. 分段规则。详细说明可参考
   * chunk_strategy object。
   */
  @JsonProperty("chunk_strategy")
  private DocumentChunkStrategy chunkStrategy;

  /**
   * The upload time of the file, in the format of a 10-digit Unix timestamp. 文件的上传时间，格式为 10 位的
   * Unixtime 时间戳。
   */
  @JsonProperty("create_time")
  private int createTime;

  /**
   * The last modified time of the file, in the format of a 10-digit Unix timestamp. 文件的最近一次修改时间，格式为
   * 10 位的 Unixtime 时间戳。
   */
  @JsonProperty("update_time")
  private int updateTime;

  /**
   * The type of file format. Values include: 0: Document type, such as txt, pdf, online web pages,
   * etc. 1: Spreadsheet type, such as xls spreadsheets, etc. 2: Photo type, such as png images,
   * etc. 文件的格式类型。取值包括： 0：文档类型，例如 txt 、pdf 、在线网页等格式均属于文档类型。 1：表格类型，例如 xls 表格等格式属于表格类型。 2：照片类型，例如 png
   * 图片等格式属于照片类型。
   */
  @JsonProperty("format_type")
  private DocumentFormatType formatType;

  /** The number of times the file has been hit in conversations. 被对话命中的次数。 */
  @JsonProperty("hit_count")
  private int hitCount;

  /** The name of the file. 文件的名称。 */
  @JsonProperty("name")
  private String name;

  /** The size of the file in bytes. 文件的大小，单位为字节。 */
  @JsonProperty("size")
  private int size;

  /** The number of slices the file has been divided into. 文件的分段数量。 */
  @JsonProperty("slice_count")
  private int sliceCount;

  /**
   * The method of uploading the file. Values include: 0: Upload local files. 1: Upload online web
   * pages. 文件的上传方式。取值包括： 0：上传本地文件。 1：上传在线网页。
   */
  @JsonProperty("source_type")
  private DocumentSourceType sourceType;

  /**
   * The processing status of the file. Values include: 0: Processing 1: Completed 9: Processing
   * failed, it is recommended to re-upload 文件的处理状态。取值包括： 0：处理中 1：处理完毕 9：处理失败，建议重新上传
   */
  @JsonProperty("status")
  private DocumentStatus status;

  /**
   * The format of the local file, i.e., the file extension, such as "txt". Supported formats
   * include PDF, TXT, DOC, DOCX. 本地文件格式，即文件后缀，例如 txt。格式支持 pdf、txt、doc、docx 类型。
   */
  @JsonProperty("type")
  private String type;

  /** The frequency of automatic updates for online web pages, in hours. 在线网页自动更新的频率。单位为小时。 */
  @JsonProperty("update_interval")
  private int updateInterval;

  /**
   * Whether the online web page is automatically updated. Values include: 0: Do not automatically
   * update 1: Automatically update 在线网页是否自动更新。取值包括： 0：不自动更新 1：自动更新
   */
  @JsonProperty("update_type")
  private DocumentUpdateType updateType;
}
