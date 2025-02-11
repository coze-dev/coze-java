package example.auth;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
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
  private String publicKey;
  private String clientID;
  private String hostName;
  private PrivateKey privateKey;

  public ExampleJWTBuilder(String publicKey, String clientID, String baseURL, String privateKeyStr)
      throws Exception {
    this.publicKey = publicKey;
    this.clientID = clientID;
    try {
      java.net.URL url = new java.net.URL(baseURL);
      this.hostName = url.getHost();
    } catch (Exception e) {
      throw new RuntimeException("Invalid base URL: " + baseURL, e);
    }
    this.privateKey = parsePrivateKey(privateKeyStr);
  }

  @Override
  public String generateJWT(int ttl, String sessionName) {
    try {
      long now = System.currentTimeMillis() / 1000;

      // 构建 JWT header
      Map<String, Object> header = new HashMap<>();
      header.put("alg", "RS256");
      header.put("typ", "JWT");
      header.put("kid", this.publicKey);

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
}
