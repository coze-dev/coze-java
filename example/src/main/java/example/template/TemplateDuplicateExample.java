package example.template;

import com.coze.openapi.client.template.DuplicateTemplateReq;
import com.coze.openapi.client.template.DuplicateTemplateResp;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

public class TemplateDuplicateExample {
  public static void main(String[] args) {
    // Get an access_token through personal access token or oauth.
    String token = System.getenv("COZE_API_TOKEN");
    TokenAuth authCli = new TokenAuth(token);

    // Init the Coze client through the access_token.
    CozeAPI coze =
        new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(authCli)
            .readTimeout(10000)
            .build();

    String templateID = System.getenv("COZE_TEMPLATE_ID");
    String workspaceID = System.getenv("WORKSPACE_ID");
    /*
     * duplicate template
     */
    DuplicateTemplateReq req =
        DuplicateTemplateReq.builder().workspaceID(workspaceID).templateID(templateID).build();
    DuplicateTemplateResp resp = coze.templates().duplicate(req);
    System.out.println("Duplicated template ID: " + resp);
  }
}
