package com.coze.openapi.client.dataset.model;

import com.coze.openapi.client.dataset.document.model.DocumentStatus;
import com.coze.openapi.client.dataset.document.model.DocumentUpdateType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentProgress {
  /*
   * File ID.
   */
  @JsonProperty("document_id")
  private String documentID;

  /*
   * File address.
   */
  @JsonProperty("url")
  private String url;

  /*
   * The size of the file, in bytes.
   */
  @JsonProperty("size")
  private Integer size;

  /*
   * Local file format, i.e., the file extension, such as txt. Format supports pdf, txt, doc, docx types.
   */
  @JsonProperty("type")
  private String type;

  /*
   * File processing status. Values include: 0: In processing, 1: Processing completed, 9: Processing failed, recommend re-uploading
   */
  @JsonProperty("status")
  private DocumentStatus status;

  /*
   * File upload progress. Unit is percentage.
   */
  @JsonProperty("progress")
  private Integer progress;

  /*
   * Will the webpage automatically update online.
   */
  @JsonProperty("update_type")
  private DocumentUpdateType updateType;

  /*
   * File name.
   */
  @JsonProperty("document_name")
  private String documentName;

  /*
   * Estimated remaining time, in seconds.
   */
  @JsonProperty("remaining_time")
  private Integer remainingTime;

  /*
   * Detailed description of the failure status, for example, the failure information returned when slicing fails.
   * This parameter is returned only when document processing fails.
   */
  @JsonProperty("status_descript")
  private String statusDescript;

  /*
   * Frequency of automatic updates for online web pages. Unit is hours.
   */
  @JsonProperty("update_interval")
  private Integer updateInterval;
}
