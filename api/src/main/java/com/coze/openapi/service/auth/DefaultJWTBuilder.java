package com.coze.openapi.service.auth;

import java.security.PrivateKey;
import java.util.Date;
import java.util.Map;

import com.coze.openapi.service.utils.Utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DefaultJWTBuilder implements JWTBuilder {
  private String clientID;
  private String hostName;

  @Override
  public String generateJWT(
      Map<String, Object> header, PrivateKey privateKey, int ttl, String sessionName) {
    try {
      long now = System.currentTimeMillis() / 1000;
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
}
