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
  public String generateJWT(PrivateKey privateKey, Map<String, Object> header, JWTPayload payload) {
    try {
      JwtBuilder jwtBuilder =
          Jwts.builder()
              .setHeader(header)
              .setIssuer(payload.getIss())
              .setAudience(payload.getAud())
              .setIssuedAt(payload.getIat())
              .setExpiration(payload.getExp())
              .setId(payload.getJti())
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
