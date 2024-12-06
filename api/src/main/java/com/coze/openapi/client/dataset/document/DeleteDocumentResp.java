package com.coze.openapi.client.dataset.document;

import com.coze.openapi.client.common.BaseResp;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeleteDocumentResp extends BaseResp{
}
