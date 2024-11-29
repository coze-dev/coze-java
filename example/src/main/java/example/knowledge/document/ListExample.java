package example.knowledge.document;

import com.coze.openapi.client.knowledge.document.ListDocumentReq;
import com.coze.openapi.client.knowledge.document.model.Document;
import com.coze.openapi.client.common.pagination.PageResult;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.config.Consts;

import java.util.Iterator;

public class ListExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        Long datasetID = Long.parseLong(System.getenv("DATASET_ID"));
        ListDocumentReq req = ListDocumentReq.builder().datasetID(datasetID).build();

        Integer count = 0;
        
        try {
            PageResult<Document> resp = coze.knowledge().documents().list(req);
            Iterator<Document> iterator = resp.getIterator();
            while (iterator.hasNext()) {
                count++;
                Document document = iterator.next();
                System.out.println(document);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean hasMore = true;
        Integer pageNum = 1;
        while (hasMore) {
            PageResult<Document> resp = coze.knowledge().documents().list(ListDocumentReq.of(datasetID, pageNum, 2));
            hasMore = resp.getHasMore();
            pageNum++;
            for (Document document : resp.getItems()) {
                System.out.println(document);
            }
        }
    }
} 