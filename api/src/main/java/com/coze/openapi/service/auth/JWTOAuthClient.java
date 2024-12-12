package com.coze.openapi.service.auth;

import com.coze.openapi.client.auth.GetAccessTokenReq;
import com.coze.openapi.client.auth.GetAccessTokenResp;
import com.coze.openapi.client.auth.GrantType;
import com.coze.openapi.client.auth.scope.Scope;

import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.security.spec.PKCS8EncodedKeySpec;

public class JWTOAuthClient extends OAuthClient{
    private final Integer ttl;
    private final PrivateKey privateKey;
    private final String publicKey;

    public JWTOAuthClient(String clientID, String privateKey, String publicKey)  throws Exception{
        super(null, clientID);
        this.privateKey = parsePrivateKey(privateKey);
        this.publicKey = publicKey;
        this.ttl = 3600;
    }

    public JWTOAuthClient(String clientID, String privateKey, String publicKey, Integer ttl)  throws Exception{
        super(null, clientID);
        this.privateKey = parsePrivateKey(privateKey);
        this.publicKey = publicKey;
        this.ttl = ttl;
    }

    public JWTOAuthClient(String clientID, String privateKey, String publicKey, String baseURL)  throws Exception{
        super(null, clientID, baseURL);
        this.privateKey = parsePrivateKey(privateKey);
        this.publicKey = publicKey;
        this.ttl = 3600;
    }

    public JWTOAuthClient(String clientID, String privateKey, String publicKey, Integer ttl, String baseURL) throws Exception {
        super(null, clientID, baseURL);
        this.privateKey = parsePrivateKey(privateKey);
        this.publicKey = publicKey;
        this.ttl = ttl;
    }


    public GetAccessTokenResp getAccessToken() {
        return doGetAccessToken(this.ttl, null, null);
    }

    public GetAccessTokenResp getAccessToken(Integer ttl) {
        return doGetAccessToken(ttl, null, null);
    }

    public GetAccessTokenResp getAccessToken(Scope scope) {
        return doGetAccessToken(this.ttl, scope, null);
    }

    public GetAccessTokenResp getAccessToken(Integer ttl, Scope scope) {
        return doGetAccessToken(ttl, scope, null);
    }

    public GetAccessTokenResp getAccessToken(String sessionName) {
        return doGetAccessToken(this.ttl, null, sessionName);
    }

    public GetAccessTokenResp getAccessToken(Integer ttl, String sessionName) {
        return doGetAccessToken(ttl, null, sessionName);
    }

    public GetAccessTokenResp getAccessToken(Scope scope, String sessionName) {
        return doGetAccessToken(this.ttl, scope, sessionName);
    }

    public GetAccessTokenResp getAccessToken(Integer ttl, Scope scope, String sessionName) {
        return doGetAccessToken(ttl, scope, sessionName);
    }

    private GetAccessTokenResp doGetAccessToken(Integer ttl, Scope scope, String sessionName) {
        GetAccessTokenReq.GetAccessTokenReqBuilder builder = GetAccessTokenReq.builder();
        builder.grantType(GrantType.JWTCode.getValue()).durationSeconds(ttl).scope(scope);

        return getAccessToken(this.generateJWT(ttl, sessionName), builder.build());
    }

    @Override
    public GetAccessTokenResp refreshToken(String refreshToken, String redirectURI) {
        return null;
    }

    private String generateJWT(int ttl, String sessionName) {
        try {
            long now = System.currentTimeMillis() / 1000;
            
            // 构建 JWT header
            Map<String, Object> header = new HashMap<>();
            header.put("alg", "RS256");
            header.put("typ", "JWT");
            header.put("kid", this.publicKey);

            
            JwtBuilder jwtBuilder = Jwts.builder()
                .setHeader(header)
                .setIssuer(this.clientID)
                .setAudience("api.coze.cn")
                .setIssuedAt(new Date(now * 1000))
                .setExpiration(new Date((now + ttl) * 1000))
                .setId(generateRandomHex(16))
                .signWith(privateKey, SignatureAlgorithm.RS256);
            if (sessionName != null) {
                jwtBuilder.claim("sesstion_name", sessionName);
            }
            return jwtBuilder.compact();
                    
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate JWT", e);
        }
    }

    private PrivateKey parsePrivateKey(String privateKeyPEM) throws Exception {
        String privateKeyContent = privateKeyPEM
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyContent);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    private String generateRandomHex(int length) {
        byte[] bytes = new byte[length / 2];
        new SecureRandom().nextBytes(bytes);
        return bytesToHex(bytes);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

}
