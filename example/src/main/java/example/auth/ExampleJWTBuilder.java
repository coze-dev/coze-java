package example.auth;

import java.security.PrivateKey;
import java.util.Map;

import com.coze.openapi.service.auth.JWTBuilder;
import com.coze.openapi.service.auth.JWTPayload;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lombok.NoArgsConstructor;

// import com.coze.openapi.service.utils.Utils;
// import io.jsonwebtoken.JwtBuilder;
// import io.jsonwebtoken.Jwts;
// import java.util.Date;

/**
 * This is an example of a JWT builder implementation. If you are using jjwt version 0.12.x or
 * above, please refer to this example to implement your own JWT parser. When you try to run this
 * code, please uncomment the commented code and imports
 */
@NoArgsConstructor
public class ExampleJWTBuilder implements JWTBuilder {
  @Override
  public String generateJWT(PrivateKey privateKey, Map<String, Object> header, JWTPayload payload) {
    try {
//                  JwtBuilder jwtBuilder =
//                      Jwts.builder()
//                          .header()
//                          .add(header)
//                          .and()
//                          .issuer(payload.getIss())
//                          .audience()
//                          .add(payload.getAud())
//                          .and()
//                          .issuedAt(payload.getIat())
//                          .expiration(payload.getExp())
//                          .id(payload.getJti())
//                          .signWith(privateKey, Jwts.SIG.RS256);
//                  if (payload.getSessionName() != null) {
//                    jwtBuilder.claim("session_name", payload.getSessionName());
//                  }
//                  return jwtBuilder.compact();

    } catch (Exception e) {
      throw new RuntimeException("Failed to generate JWT", e);
    }
    return "";
  }
}
