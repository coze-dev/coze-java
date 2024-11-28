package example.knowledge.document;

import com.coze.openapi.client.knowledge.document.DeleteDocumentReq;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import java.util.Arrays;

public class DeleteExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        Long did = Long.parseLong(System.getenv("DOCUMENT_ID"));

        try {
            coze.knowledge().documents().delete(DeleteDocumentReq.builder().documentIDs(Arrays.asList(did)).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 