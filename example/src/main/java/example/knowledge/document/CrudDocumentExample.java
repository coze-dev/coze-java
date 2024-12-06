package example.knowledge.document;

import com.coze.openapi.client.dataset.document.CreateDocumentReq;
import com.coze.openapi.client.dataset.document.CreateDocumentResp;
import com.coze.openapi.client.dataset.document.DeleteDocumentReq;
import com.coze.openapi.client.dataset.document.UpdateDocumentReq;
import com.coze.openapi.client.dataset.document.model.Document;
import com.coze.openapi.client.dataset.document.model.DocumentBase;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CrudDocumentExample {

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
        /*
         * create document in to specific dataset
         * */
        CreateDocumentReq createReq = CreateDocumentReq.builder()
                .datasetID(datasetID)
                .documentBases(Arrays.asList(
                        DocumentBase.buildWebPage("web doc example", "https://your-website.com"),
                        DocumentBase.buildLocalFile("file doc example", "your file content", "txt")))
                .build();
        CreateDocumentResp creatResp = coze.datasets().documents().create(createReq);
        List<Long> documentIDs = new ArrayList<>();
        for (Document documentBase : creatResp.getDocumentInfos()) {
            documentIDs.add(Long.parseLong(documentBase.getDocumentID()));
        }

        /*
         * update document. It means success that no exception has been thrown
         * */
        UpdateDocumentReq updateReq = UpdateDocumentReq.builder()
                .documentID(documentIDs.get(0))
                .documentName("new name")
                .build();
        coze.datasets().documents().update(updateReq);

        /*
         * delete document. It means success that no exception has been thrown
         * */
        coze.datasets().documents().delete(DeleteDocumentReq.builder().documentIDs(Collections.singletonList(documentIDs.get(0))).build());
    }



}
