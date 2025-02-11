package com.coze.openapi.service.auth;

import java.security.PrivateKey;
import java.util.Date;
import java.util.Map;

import com.coze.openapi.service.utils.Utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DefaultJWTBuilder implements JWTBuilder {

  @Override
  public String generateJWT(Map<String, Object> header, PrivateKey privateKey, JWTPayload payload) {
    try {
      long now = System.currentTimeMillis() / 1000;
      JwtBuilder jwtBuilder =
          Jwts.builder()
              .setHeader(header)
              .setIssuer(payload.getClientID())
              .setAudience(payload.getHostName())
              .setIssuedAt(new Date(now * 1000))
              .setExpiration(new Date((now + payload.getTtl()) * 1000))
              .setId(Utils.genRandomSign(16))
              .signWith(privateKey, SignatureAlgorithm.RS256);
      if (payload.getSessionName() != null) {
        jwtBuilder.claim("session_name", payload.getSessionName());
      }
      return jwtBuilder.compact();

    } catch (Exception e) {
      throw new RuntimeException("Failed to generate JWT", e);
    }
  }
}
