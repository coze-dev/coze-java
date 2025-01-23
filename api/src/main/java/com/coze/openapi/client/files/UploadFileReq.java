package com.coze.openapi.client.files;

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
public class UploadFileReq extends BaseReq {
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

  public static UploadFileReq of(String fileName, byte[] fileBytes) {
    return UploadFileReq.builder().fileName(fileName).fileBytes(fileBytes).build();
  }

  public static UploadFileReq of(File file) {
    return UploadFileReq.builder().file(file).build();
  }

  public static UploadFileReq of(String filePath) {
    return UploadFileReq.builder().filePath(filePath).build();
  }
}
