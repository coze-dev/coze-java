package example.datasets;

import java.util.concurrent.TimeUnit;

import com.coze.openapi.client.dataset.CreateDatasetReq;
import com.coze.openapi.client.dataset.CreateDatasetResp;
import com.coze.openapi.client.dataset.DeleteDatasetReq;
import com.coze.openapi.client.dataset.DeleteDatasetResp;
import com.coze.openapi.client.dataset.UpdateDatasetReq;
import com.coze.openapi.client.dataset.UpdateDatasetResp;
import com.coze.openapi.client.dataset.document.model.DocumentFormatType;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

public class DatasetCrudExample {
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

    String spaceID = System.getenv("WORKSPACE_ID");

    /*
     * create dataset
     */
    CreateDatasetReq createReq =
        CreateDatasetReq.builder()
            .name("test dataset")
            .spaceID(spaceID)
            .formatType(DocumentFormatType.DOCUMENT)
            .description("test dataset description")
            .build();
    CreateDatasetResp createResp = coze.datasets().create(createReq);
    System.out.println("Created dataset ID: " + createResp.getDatasetID());

    String datasetID = createResp.getDatasetID();

    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    /*
     * update dataset
     */
    UpdateDatasetReq updateReq =
        UpdateDatasetReq.builder()
            .datasetID(datasetID)
            .name("updated dataset name")
            .description("updated dataset description")
            .build();
    UpdateDatasetResp updateResp = coze.datasets().update(updateReq);
    System.out.println("Update dataset response: " + updateResp);

    /*
     * delete dataset
     */
    DeleteDatasetReq deleteReq = DeleteDatasetReq.builder().datasetID(datasetID).build();
    DeleteDatasetResp deleteResp = coze.datasets().delete(deleteReq);
    System.out.println("Delete dataset response: " + deleteResp);
  }
}
