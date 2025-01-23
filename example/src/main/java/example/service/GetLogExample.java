package example.service;

import com.coze.openapi.client.bots.RetrieveBotReq;
import com.coze.openapi.client.bots.RetrieveBotResp;
import com.coze.openapi.client.exception.CozeApiException;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

/*
 * For all SDK methods that request the OpenAPI, the request logID is encapsulated
 * and returned to developers. Developers can directly call the getLogID method to obtain it.
 */
public class GetLogExample {
  public static void main(String[] args) {

    String token = System.getenv("COZE_API_TOKEN");
    TokenAuth authCli = new TokenAuth(token);

    // Init the Coze client through the access_token.
    CozeAPI coze =
        new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(authCli)
            .readTimeout(10000)
            .build();

    try {
      RetrieveBotResp resp = coze.bots().retrieve(RetrieveBotReq.of("botID"));
      /*
       * For all response objects, you can get the request logID by calling the getLogID method.
       */
      System.out.println(resp.getLogID());
    } catch (CozeApiException e) {
      System.out.println(e.getLogID());
    }
  }
}
