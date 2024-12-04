package example.workspace;

import java.util.Iterator;

import com.coze.openapi.client.common.pagination.PageResult;
import com.coze.openapi.client.workspace.Workspace;
import com.coze.openapi.client.workspace.ListWorkspaceReq;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

public class ListWorkspaceExample {

    public static void main(String[] args) {
        // Get an access_token through personal access token or oauth.
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);

        // Init the Coze client through the access_token.
        CozeAPI coze = new CozeAPI.Builder()
                .baseURL(System.getenv("COZE_API_BASE_URL"))
                .auth(authCli)
                .readTimeout(10000)
                .build();

        // you can use iterator to automatically retrieve next page
        PageResult<Workspace> workspaces = coze.workspaces().list(new ListWorkspaceReq());
        Iterator<Workspace> iter = workspaces.getIterator();
        iter.forEachRemaining(System.out::println);

        // you can manually retrieve next page
        int pageNum = 1;
        ListWorkspaceReq req = new ListWorkspaceReq();
        while (workspaces.getHasMore()){
            pageNum++;
            req.setPageNum(pageNum);
            workspaces = coze.workspaces().list(req);
            for (Workspace item : workspaces.getItems()) {
                System.out.println(item);
            }
        }
    }
} 