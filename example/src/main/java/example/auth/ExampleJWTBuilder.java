package example.auth;

import java.security.PrivateKey;
import java.util.Date;
import java.util.Map;

import com.coze.openapi.service.auth.JWTBuilder;
import com.coze.openapi.service.utils.Utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lombok.NoArgsConstructor;

/**
 * This is an example of a JWT builder implementation. If you are using jjwt version 0.12.x or
 * above, please refer to this example to implement your own JWT parser.
 */
@NoArgsConstructor
public class ExampleJWTBuilder implements JWTBuilder {
  private String clientID;
  private String hostName;

  public ExampleJWTBuilder(String clientID, String baseURL) throws Exception {
    this.clientID = clientID;
    try {
      java.net.URL url = new java.net.URL(baseURL);
      this.hostName = url.getHost();
    } catch (Exception e) {
      throw new RuntimeException("Invalid base URL: " + baseURL, e);
    }
  }

  @Override
  public String generateJWT(
      Map<String, Object> header, PrivateKey privateKey, int ttl, String sessionName) {
    try {
      long now = System.currentTimeMillis() / 1000;

      JwtBuilder jwtBuilder =
          Jwts.builder()
              .header()
              .add(header)
              .and()
              .issuer(this.clientID)
              .audience()
              .add(this.hostName)
              .and()
              .issuedAt(new Date(now * 1000))
              .expiration(new Date((now + ttl) * 1000))
              .id(Utils.genRandomSign(16))
              .signWith(privateKey, Jwts.SIG.RS256);
      if (sessionName != null) {
        jwtBuilder.claim("session_name", sessionName);
      }
      return jwtBuilder.compact();

    } catch (Exception e) {
      throw new RuntimeException("Failed to generate JWT", e);
    }
  }
}
