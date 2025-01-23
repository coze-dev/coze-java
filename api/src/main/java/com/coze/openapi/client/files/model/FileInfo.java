package com.coze.openapi.client.files.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {
  // The ID of the uploaded file.
  @JsonProperty("id")
  private String id;

  public String getID() {
    return id;
  }

  // The total byte size of the file.
  @JsonProperty("bytes")
  private Integer bytes;

  // The upload time of the file, in the format of a 10-digit Unix timestamp in seconds (s).
  @JsonProperty("created_at")
  private Integer createdAt;

  // The name of the file.
  @JsonProperty("file_name")
  private String fileName;
}
