package com.coze.openapi.client.dataset.model;

import java.util.List;

import com.coze.openapi.client.dataset.document.model.DocumentChunkStrategy;
import com.coze.openapi.client.dataset.document.model.DocumentFormatType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dataset {
  /*
   * The ID of the dataset
   */
  @JsonProperty("dataset_id")
  private String datasetID;

  /*
   * The name of the dataset
   */
  @JsonProperty("name")
  private String name;

  /*
   * The description of the dataset
   */
  @JsonProperty("description")
  private String description;

  /*
   * The ID of the space that the dataset belongs to
   */
  @JsonProperty("space_id")
  private String spaceID;

  /*
   * The status of the dataset, 1: enable, 3: disable
   */
  @JsonProperty("status")
  private DatasetStatus status;

  /*
   * The format type of the dataset, 0: text, 1: table, 2: image
   */
  @JsonProperty("format_type")
  private DocumentFormatType formatType;

  /*
   * Whether the dataset can be edited
   */
  @JsonProperty("can_edit")
  private Boolean canEdit;

  /*
   * The icon URL of the dataset
   */
  @JsonProperty("icon_url")
  private String iconURL;

  /*
   * The count of the documents in the dataset
   */
  @JsonProperty("doc_count")
  private Integer docCount;

  /*
   * The list of files in the dataset
   */
  @JsonProperty("file_list")
  private List<String> fileList;

  /*
   * The count of the hits in the dataset
   */
  @JsonProperty("hit_count")
  private Integer hitCount;

  /*
   * The count of the bots used in the dataset
   */
  @JsonProperty("bot_used_count")
  private Integer botUsedCount;

  /*
   * The count of the segments in the dataset
   */
  @JsonProperty("slice_count")
  private Integer sliceCount;

  /*
   * The total size of all files in the dataset
   */
  @JsonProperty("all_file_size")
  private String allFileSize;

  /*
   * The chunk strategy of the dataset
   */
  @JsonProperty("chunk_strategy")
  private DocumentChunkStrategy chunkStrategy;

  /*
   * The list of files that failed to be uploaded in the dataset
   */
  @JsonProperty("failed_file_list")
  private List<String> failedFileList;

  /*
   * The list of files that are being processed in the dataset
   */
  @JsonProperty("processing_file_list")
  private List<String> processingFileList;

  /*
   * The list of file IDs that are being processed in the dataset
   */
  @JsonProperty("processing_file_id_list")
  private List<String> processingFileIDList;

  /*
   * The avatar URL of the dataset creator
   */
  @JsonProperty("avatar_url")
  private String avatarURL;

  /*
   * The ID of the dataset creator
   */
  @JsonProperty("creator_id")
  private String creatorID;

  /*
   * The name of the dataset creator
   */
  @JsonProperty("creator_name")
  private String creatorName;

  /*
   * The creation second-level timestamp of the dataset
   */
  @JsonProperty("create_time")
  private Long createTime;

  /*
   * The update second-level timestamp of the dataset
   */
  @JsonProperty("update_time")
  private Long updateTime;
}
