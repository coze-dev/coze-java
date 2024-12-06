package com.coze.openapi.client.files;

import com.coze.openapi.client.common.BaseReq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RetrieveFileReq extends BaseReq {
    private String fileID;

    public static RetrieveFileReq of(String fileID) {
        return RetrieveFileReq.builder().fileID(fileID).build();
    }
}
