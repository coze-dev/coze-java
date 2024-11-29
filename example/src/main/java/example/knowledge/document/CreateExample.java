package example.knowledge.document;

import java.util.Arrays;

import com.coze.openapi.client.knowledge.document.CreateDocumentReq;
import com.coze.openapi.client.knowledge.document.CreateDocumentResp;
import com.coze.openapi.client.knowledge.document.model.DocumentBase;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

public class CreateExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        Long datasetID = Long.parseLong(System.getenv("DATASET_ID"));
        CreateDocumentReq req = CreateDocumentReq.builder()
            .datasetID(datasetID)
            .documentBases(Arrays.asList(
                DocumentBase.buildWebPage("web doc example", "https://your-website.com"),
                DocumentBase.buildLocalFile("file doc example", "your file content", "txt")))
            .build();
        CreateDocumentResp resp = coze.knowledge().documents().create(req);
        System.out.println(resp);

    }
}
