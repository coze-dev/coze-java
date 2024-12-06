package com.coze.openapi.client.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPKCEAuthURLResp {
    private String codeVerifier;
    private String authorizationURL;
}
