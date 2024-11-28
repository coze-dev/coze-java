package example.workspace;

import com.coze.openapi.client.common.pagination.PageResult;
import com.coze.openapi.client.workspace.Workspace;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

public class ListExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);

        try {
            Integer i = 1;
            PageResult<Workspace> resp = coze.workspaces().list(i, 2);
            while (resp.getHasMore()) {
                for (Workspace workspace : resp.getItems()) {
                    System.out.println(workspace);
                }
                i++;
                resp = coze.workspaces().list(i, 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 