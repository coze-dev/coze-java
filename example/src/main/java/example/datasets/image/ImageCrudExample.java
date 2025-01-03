package example.datasets.image;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import com.coze.openapi.client.dataset.ProcessDatasetReq;
import com.coze.openapi.client.dataset.ProcessDatasetResp;
import com.coze.openapi.client.dataset.document.CreateDocumentReq;
import com.coze.openapi.client.dataset.document.CreateDocumentResp;
import com.coze.openapi.client.dataset.document.model.DocumentBase;
import com.coze.openapi.client.dataset.document.model.DocumentFormatType;
import com.coze.openapi.client.dataset.document.model.DocumentStatus;
import com.coze.openapi.client.dataset.image.UpdateImageReq;
import com.coze.openapi.client.dataset.image.UpdateImageResp;
import com.coze.openapi.client.files.UploadFileReq;
import com.coze.openapi.client.files.UploadFileResp;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

public class ImageCrudExample {
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

    String datasetID = System.getenv("DATASET_ID");

    /*
     * Create image document
     * */

    /*
     * Step 1, upload image to coze
     * */
    String imagePath = System.getenv("IMAGE_FILE_PATH");
    UploadFileResp imageInfo = coze.files().upload(UploadFileReq.of(imagePath));
    System.out.println(imageInfo);

    /*
     * Step 2, create document
     * */
    CreateDocumentReq createReq =
        CreateDocumentReq.builder()
            .datasetID(Long.parseLong(datasetID))
            .documentBases(
                Collections.singletonList(
                    DocumentBase.buildImage(
                        imageInfo.getFileInfo().getFileName(),
                        Long.parseLong(imageInfo.getFileInfo().getID()))))
            .formatType(DocumentFormatType.IMAGE)
            .build();
    CreateDocumentResp creatResp = coze.datasets().documents().create(createReq);
    System.out.println(creatResp);
    /*
     * step 3: make sure upload is completed
     * */
    String documentID = creatResp.getDocumentInfos().get(0).getDocumentID();

    ProcessDatasetReq processReq =
        ProcessDatasetReq.builder()
            .datasetID(datasetID)
            .documentIDs(Collections.singletonList(documentID))
            .build();
    while (true) {
      ProcessDatasetResp processResp = coze.datasets().process(processReq);
      System.out.println(processResp);
      if (processResp.getData().get(0).getStatus().equals(DocumentStatus.PROCESSING)) {
        System.out.println(
            "upload is not completed, please wait, process:"
                + processResp.getData().get(0).getProgress());
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      } else if (processResp.getData().get(0).getStatus().equals(DocumentStatus.FAILED)) {
        System.out.println("upload is failed, please check");
        return;
      } else {
        break;
      }
    }

    /*
     * update image caption
     */
    UpdateImageReq updateReq =
        UpdateImageReq.builder()
            .datasetID(datasetID)
            .documentID(documentID)
            .caption("new image caption")
            .build();
    UpdateImageResp updateResp = coze.datasets().images().update(updateReq);
    System.out.println(updateResp);
  }
}
