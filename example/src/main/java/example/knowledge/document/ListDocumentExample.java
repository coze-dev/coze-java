package example.knowledge.document;

import com.coze.openapi.client.common.pagination.PageResult;
import com.coze.openapi.client.knowledge.document.ListDocumentReq;
import com.coze.openapi.client.knowledge.document.model.Document;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

import java.util.Iterator;

public class ListDocumentExample {
    public static void main(String[] args) {
        // Get an access_token through personal access token or oauth.
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);

        // Init the Coze client through the access_token.
        CozeAPI coze = new CozeAPI.Builder()
                .baseURL(System.getenv("COZE_API_BASE_URL"))
                .auth(authCli)
                .readTimeout(10000)
                .build();;

        Long datasetID = Long.parseLong(System.getenv("DATASET_ID"));

        ListDocumentReq req = ListDocumentReq.builder()
                .size(2)
                .datasetID(datasetID)
                .page(1)
                .build();

        // you can use iterator to automatically retrieve next page
        PageResult<Document> documents = coze.knowledge().documents().list(req);
        Iterator<Document> iter = documents.getIterator();
        iter.forEachRemaining(System.out::println);

        // you can manually retrieve next page
        int pageNum = 1;
        while (documents.getHasMore()){
            pageNum++;
            req.setPage(pageNum);
            documents = coze.knowledge().documents().list(req);
            for (Document item : documents.getItems()) {
                System.out.println(item);
            }
        }
    }
}
