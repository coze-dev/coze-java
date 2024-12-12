/* (C)2024 */
package example.workspace;

import java.util.Iterator;

import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.client.workspace.ListWorkspaceReq;
import com.coze.openapi.client.workspace.model.Workspace;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

public class WorkspaceListExample {

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

    // you can use iterator to automatically retrieve next page
    PageResp<Workspace> workspaces = coze.workspaces().list(new ListWorkspaceReq());
    Iterator<Workspace> iter = workspaces.getIterator();
    iter.forEachRemaining(System.out::println);

    // the page result will return followed information
    System.out.println("total: " + workspaces.getTotal());
    System.out.println("has_more: " + workspaces.getHasMore());
    System.out.println("logID: " + workspaces.getLogID());
    for (Workspace item : workspaces.getItems()) {
      System.out.println(item);
    }
  }
}
