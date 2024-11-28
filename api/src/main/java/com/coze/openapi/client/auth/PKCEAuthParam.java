package com.coze.openapi.client.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PKCEAuthParam {
    private String codeVerifier;
    private String authorizationURL;
}
