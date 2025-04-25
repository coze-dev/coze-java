package com.coze.openapi.service.auth;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.coze.openapi.client.auth.*;
import com.coze.openapi.client.auth.scope.Scope;
import com.coze.openapi.service.utils.Utils;

import lombok.Getter;

public class JWTOAuthClient extends OAuthClient {
  @Getter private final Integer ttl;
  private final PrivateKey privateKey;
  private final String publicKey;
  private final JWTBuilder jwtBuilder;

  protected JWTOAuthClient(JWTOAuthBuilder builder) throws Exception {
    super(builder);

    this.privateKey = parsePrivateKey(builder.privateKey);
    this.publicKey = builder.publicKey;
    if (builder.jwtBuilder != null) {
      this.jwtBuilder = builder.jwtBuilder;
    } else {
      this.jwtBuilder = new DefaultJWTBuilder();
    }
    this.ttl = builder.ttl;
  }

  public static JWTOAuthClient loadFromConfig(LoadAuthConfig loadConfig) throws Exception {
    OAuthConfig config = OAuthConfig.load(loadConfig);
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
    return doGetAccessToken(GetJWTAccessTokenReq.builder().ttl(this.ttl).build());
  }

  public OAuthToken getAccessToken(Integer ttl) {
    return doGetAccessToken(GetJWTAccessTokenReq.builder().ttl(ttl).build());
  }

  public OAuthToken getAccessToken(Scope scope) {
    return doGetAccessToken(GetJWTAccessTokenReq.builder().scope(scope).ttl(ttl).build());
  }

  public OAuthToken getAccessToken(Integer ttl, Scope scope) {
    return doGetAccessToken(GetJWTAccessTokenReq.builder().scope(scope).ttl(ttl).build());
  }

  public OAuthToken getAccessToken(String sessionName) {
    return doGetAccessToken(
        GetJWTAccessTokenReq.builder().sessionName(sessionName).ttl(ttl).build());
  }

  public OAuthToken getAccessToken(Integer ttl, String sessionName) {
    return doGetAccessToken(
        GetJWTAccessTokenReq.builder().sessionName(sessionName).ttl(ttl).build());
  }

  public OAuthToken getAccessToken(Scope scope, String sessionName) {
    return doGetAccessToken(
        GetJWTAccessTokenReq.builder().sessionName(sessionName).scope(scope).ttl(ttl).build());
  }

  public OAuthToken getAccessToken(Integer ttl, Scope scope, String sessionName) {
    return doGetAccessToken(
        GetJWTAccessTokenReq.builder().sessionName(sessionName).scope(scope).ttl(ttl).build());
  }

  public OAuthToken getAccessToken(GetJWTAccessTokenReq req) {
    return doGetAccessToken(req);
  }

  private OAuthToken doGetAccessToken(GetJWTAccessTokenReq req) {
    GetAccessTokenReq.GetAccessTokenReqBuilder builder = GetAccessTokenReq.builder();
    Integer ttl = this.ttl;
    if (req.getTtl() != null) {
      ttl = req.getTtl();
    }
    builder.grantType(GrantType.JWT_CODE.getValue()).durationSeconds(ttl).scope(req.getScope());
    Map<String, Object> header = new HashMap<>();
    header.put("alg", "RS256");
    header.put("typ", "JWT");
    header.put("kid", this.publicKey);
    long now = System.currentTimeMillis() / 1000;

    JWTPayload payload =
        JWTPayload.builder()
            .iss(this.clientID)
            .aud(this.hostName)
            .exp(new Date((now + ttl) * 1000))
            .iat(new Date(now * 1000))
            .sessionName(req.getSessionName())
            .jti(Utils.genRandomSign(16))
            .sessionContext(req.getSessionContext())
            .build();
    String jwtToken = this.jwtBuilder.generateJWT(privateKey, header, payload);
    if (req.getAccountID() != null) {
      return getAccountAccessToken(jwtToken, String.valueOf(req.getAccountID()), builder.build());
    }
    if (req.getEnterpriseID() != null) {
      return getEnterpriseAccessToken(jwtToken, req.getEnterpriseID(), builder.build());
    }
    return getAccessToken(jwtToken, builder.build());
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
    private JWTBuilder jwtBuilder;

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

    // If you are using jjwt version 0.12.x or above, you need to handle JWT parsing by yourself
    public JWTOAuthBuilder jwtBuilder(JWTBuilder jwtBuilder) {
      this.jwtBuilder = jwtBuilder;
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
