/* (C)2024 */
package example.datasets.document;

import java.util.Iterator;

import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.client.dataset.document.ListDocumentReq;
import com.coze.openapi.client.dataset.document.model.Document;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

public class DocumentListExample {
  public static void main(String[] args) {
    // Get an access_token through personal access token or oauth.
    String token = System.getenv("COZE_API_TOKEN");
    TokenAuth authCli = new TokenAuth(token);

    // Init the Coze client through the access_token.
    CozeAPI coze =
        new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(authCli)
            .readTimeout(10000)
            .build();
    ;

    Long datasetID = Long.parseLong(System.getenv("DATASET_ID"));

    ListDocumentReq req = ListDocumentReq.builder().size(2).datasetID(datasetID).page(1).build();

    // you can use iterator to automatically retrieve next page
    PageResp<Document> documents = coze.datasets().documents().list(req);
    Iterator<Document> iter = documents.getIterator();
    iter.forEachRemaining(System.out::println);

    // the page result will return followed information
    System.out.println("total: " + documents.getTotal());
    System.out.println("has_more: " + documents.getHasMore());
    System.out.println("logID: " + documents.getLogID());
    for (Document item : documents.getItems()) {
      System.out.println(item);
    }
  }
}
