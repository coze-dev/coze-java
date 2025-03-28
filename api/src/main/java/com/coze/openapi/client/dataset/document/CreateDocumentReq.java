package com.coze.openapi.client.dataset.document;

import java.util.List;

import com.coze.openapi.client.common.BaseReq;
import com.coze.openapi.client.dataset.document.model.DocumentBase;
import com.coze.openapi.client.dataset.document.model.DocumentChunkStrategy;
import com.coze.openapi.client.dataset.document.model.DocumentFormatType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class CreateDocumentReq extends BaseReq {
  /** The ID of the knowledge base. */
  @NonNull
  @JsonProperty("dataset_id")
  private Long datasetID;

  /**
   * The metadata information of the files awaiting upload. The array has a maximum length of 10,
   * meaning up to 10 files can be uploaded at a time. For detailed instructions, refer to the
   * DocumentBase object.
   */
  @NonNull
  @JsonProperty("document_bases")
  private List<DocumentBase> documentBases;

  /**
   * Chunk strategy. These rules must be set only when uploading a file to new knowledge for the
   * first time. For subsequent file uploads to this knowledge, it is not necessary to pass these
   * rules; the default is to continue using the initial settings, and modifications are not
   * supported. For detailed instructions, refer to the ChunkStrategy object.
   */
  @JsonProperty("chunk_strategy")
  private DocumentChunkStrategy chunkStrategy;

  @JsonProperty("format_type")
  private DocumentFormatType formatType;
}
