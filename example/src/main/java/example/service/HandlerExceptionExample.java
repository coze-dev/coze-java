package example.service;

import com.coze.openapi.client.exception.CozeApiExcetion;
import com.coze.openapi.client.exception.CozeAuthException;
import com.coze.openapi.client.workspace.ListWorkspaceReq;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

public class HandlerExceptionExample {
    public static void main(String[] args) {

        // Using the request to get workspace list as an example

        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);

        // Init the Coze client through the access_token.
        CozeAPI coze = new CozeAPI.Builder()
                .baseURL(System.getenv("COZE_API_BASE_URL"))
                .auth(authCli)
                .readTimeout(10000)
                .build();

        try {
            coze.workspaces().list(ListWorkspaceReq.of(1, 10));
        } catch (CozeApiExcetion e) {
            /*
            * You can locate error information based on the code in the exception, see the meaning of codes at:
            * cn: https://www.coze.cn/docs/developer_guides/coze_error_codes
            * en: https://www.coze.com/docs/developer_guides/coze_error_codes
            */
            System.out.println(e.getMessage());
            /*
             * you can get request log id by getLogID() method
            */
            System.out.println(e.getLogID());
        } catch (CozeAuthException e){

        }
    }
}
