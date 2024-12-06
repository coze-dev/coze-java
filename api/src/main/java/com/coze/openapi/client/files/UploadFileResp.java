package com.coze.openapi.client.files;

import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.files.model.FileInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileResp extends BaseResp {
    private FileInfo fileInfo;
}
