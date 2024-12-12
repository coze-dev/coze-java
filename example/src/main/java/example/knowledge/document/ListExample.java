package example.knowledge.document;

import com.coze.openapi.client.knowledge.document.ListDocumentReq;
import com.coze.openapi.client.knowledge.document.model.Document;
import com.coze.openapi.client.common.pagination.PageResult;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import java.util.Iterator;

public class ListExample {

    public static void main(String[] args) {
        String token = System.getenv("TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        Long kid = Long.parseLong(System.getenv("DATASET_ID"));

        ListDocumentReq req = ListDocumentReq.builder().datasetID(kid).build();

        Integer count = 0;
        try {
            PageResult<Document> resp = coze.knowledge().document().list(req);
            Iterator<Document> iterator = resp.getIterator();
            while (iterator.hasNext()) {
                count++;
                Document document = iterator.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 