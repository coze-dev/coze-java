package com.coze.openapi.service.auth;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.coze.openapi.client.auth.GetAccessTokenReq;
import com.coze.openapi.client.auth.GrantType;
import com.coze.openapi.client.auth.OAuthConfig;
import com.coze.openapi.client.auth.OAuthToken;
import com.coze.openapi.client.auth.scope.Scope;
import com.coze.openapi.service.utils.Utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;

public class JWTOAuthClient extends OAuthClient {
  @Getter private final Integer ttl;
  private final PrivateKey privateKey;
  private final String publicKey;

  protected JWTOAuthClient(JWTOAuthBuilder builder) throws Exception {
    super(builder);
    this.privateKey = parsePrivateKey(builder.privateKey);
    this.publicKey = builder.publicKey;
    this.ttl = builder.ttl;
  }

  public static JWTOAuthClient loadFromFile(String configFilePath) throws Exception {
    OAuthConfig config = OAuthConfig.load(configFilePath);
    return new JWTOAuthClient.JWTOAuthBuilder()
        .privateKey(config.getPrivateKey())
        .publicKey(config.getPublicKeyId())
        .clientID(config.getClientId())
        .baseURL(config.getCozeApiBase())
        .build();
  }

  @Override
  public OAuthToken refreshToken(String refreshToken) {
    return null;
  }

  public OAuthToken getAccessToken() {
    return doGetAccessToken(this.ttl, null, null);
  }

  public OAuthToken getAccessToken(Integer ttl) {
    return doGetAccessToken(ttl, null, null);
  }

  public OAuthToken getAccessToken(Scope scope) {
    return doGetAccessToken(this.ttl, scope, null);
  }

  public OAuthToken getAccessToken(Integer ttl, Scope scope) {
    return doGetAccessToken(ttl, scope, null);
  }

  public OAuthToken getAccessToken(String sessionName) {
    return doGetAccessToken(this.ttl, null, sessionName);
  }

  public OAuthToken getAccessToken(Integer ttl, String sessionName) {
    return doGetAccessToken(ttl, null, sessionName);
  }

  public OAuthToken getAccessToken(Scope scope, String sessionName) {
    return doGetAccessToken(this.ttl, scope, sessionName);
  }

  public OAuthToken getAccessToken(Integer ttl, Scope scope, String sessionName) {
    return doGetAccessToken(ttl, scope, sessionName);
  }

  private OAuthToken doGetAccessToken(Integer ttl, Scope scope, String sessionName) {
    GetAccessTokenReq.GetAccessTokenReqBuilder builder = GetAccessTokenReq.builder();
    builder.grantType(GrantType.JWT_CODE.getValue()).durationSeconds(ttl).scope(scope);

    return getAccessToken(this.generateJWT(ttl, sessionName), builder.build());
  }

  private String generateJWT(int ttl, String sessionName) {
    try {
      long now = System.currentTimeMillis() / 1000;

      // 构建 JWT header
      Map<String, Object> header = new HashMap<>();
      header.put("alg", "RS256");
      header.put("typ", "JWT");
      header.put("kid", this.publicKey);

      JwtBuilder jwtBuilder =
          Jwts.builder()
              .setHeader(header)
              .setIssuer(this.clientID)
              .setAudience(this.hostName)
              .setIssuedAt(new Date(now * 1000))
              .setExpiration(new Date((now + ttl) * 1000))
              .setId(Utils.genRandomSign(16))
              .signWith(privateKey, SignatureAlgorithm.RS256);
      if (sessionName != null) {
        jwtBuilder.claim("session_name", sessionName);
      }
      return jwtBuilder.compact();

    } catch (Exception e) {
      throw new RuntimeException("Failed to generate JWT", e);
    }
  }

  private PrivateKey parsePrivateKey(String privateKeyPEM) throws Exception {
    String privateKeyContent =
        privateKeyPEM
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replaceAll("\\s", "");

    byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyContent);
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    return keyFactory.generatePrivate(keySpec);
  }

  public static class JWTOAuthBuilder extends OAuthBuilder<JWTOAuthBuilder> {
    private Integer ttl;
    private String publicKey;
    private String privateKey;

    public JWTOAuthBuilder publicKey(String publicKey) {
      this.publicKey = publicKey;
      return this;
    }

    public JWTOAuthBuilder ttl(Integer ttl) {
      this.ttl = ttl;
      return this;
    }

    public JWTOAuthBuilder privateKey(String privateKey) {
      this.privateKey = privateKey;
      return this;
    }

    @Override
    protected JWTOAuthBuilder self() {
      return this;
    }

    @Override
    public JWTOAuthClient build() throws Exception {
      if (this.ttl == null || this.ttl.equals(0)) {
        this.ttl = 900;
      }
      return new JWTOAuthClient(this);
    }
  }
}
