package com.coze.openapi.client.audio.transcriptions;

import java.io.File;

import com.coze.openapi.client.common.BaseReq;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateTranscriptionsReq extends BaseReq {
  /** local file path */
  private String filePath;

  /*
   * file byte array
   */
  private byte[] fileBytes;

  /** file name */
  private String fileName;

  /** file object */
  private File file;

  public static CreateTranscriptionsReq of(String fileName, byte[] fileBytes) {
    return CreateTranscriptionsReq.builder().fileName(fileName).fileBytes(fileBytes).build();
  }

  public static CreateTranscriptionsReq of(File file) {
    return CreateTranscriptionsReq.builder().file(file).fileName(file.getName()).build();
  }

  public static CreateTranscriptionsReq of(String filePath) {
    return CreateTranscriptionsReq.builder().filePath(filePath).build();
  }
}
