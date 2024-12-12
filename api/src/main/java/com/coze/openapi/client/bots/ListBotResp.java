/* (C)2024 */
package com.coze.openapi.client.bots;

import java.util.List;

import com.coze.openapi.client.bots.model.SimpleBot;
import com.coze.openapi.client.common.BaseResp;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class ListBotResp extends BaseResp {
  @JsonProperty("space_bots")
  private List<SimpleBot> bots;

  @JsonProperty("total")
  private Integer total;
}
