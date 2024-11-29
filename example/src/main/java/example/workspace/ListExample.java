package example.workspace;

import java.util.Iterator;

import com.coze.openapi.client.common.pagination.PageResult;
import com.coze.openapi.client.workspace.Workspace;
import com.coze.openapi.client.workspace.ListWorkspaceReq;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

public class ListExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);

        try {
            PageResult<Workspace> resp = coze.workspaces().list(ListWorkspaceReq.of(1, 2));
            Iterator<Workspace> workspaces = resp.getIterator();
            while (workspaces.hasNext()) {
                Workspace workspace = workspaces.next();
                System.out.println(workspace);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 