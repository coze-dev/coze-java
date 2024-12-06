package com.coze.openapi.client.dataset.document;

import com.coze.openapi.client.common.BaseResp;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.Data;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeleteDocumentResp extends BaseResp{
}
