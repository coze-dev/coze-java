package example.workspace;

import com.coze.openapi.client.common.pagination.PageResult;
import com.coze.openapi.client.workspace.Workspace;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import java.util.Iterator;

public class ListExample {

    public static void main(String[] args) {
        String token = System.getenv("TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);

        try {
            Integer i = 1;
            PageResult<Workspace> resp = coze.workspaces().listWorkspaces(i, 2);
            while (resp.getHasMore()) {
                for (Workspace workspace : resp.getItems()) {
                    System.out.println(workspace);
                }
                i++;
                resp = coze.workspaces().listWorkspaces(i, 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 