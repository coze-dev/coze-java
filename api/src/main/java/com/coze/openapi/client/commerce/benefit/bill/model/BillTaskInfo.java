package com.coze.openapi.client.commerce.benefit.bill.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillTaskInfo {
  @JsonProperty("task_id")
  private String taskID;

  @JsonProperty("file_urls")
  private List<String> fileURLs;
}
