package example.datasets.image;

import java.util.Iterator;

import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.client.dataset.image.ListImageReq;
import com.coze.openapi.client.dataset.image.model.Photo;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

public class ImageListExample {
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

    ListImageReq req = ListImageReq.builder().datasetID(datasetID).pageSize(10).pageNum(1).build();

    // 使用迭代器自动获取下一页
    PageResp<Photo> photos = coze.datasets().images().list(req);
    Iterator<Photo> iter = photos.getIterator();
    iter.forEachRemaining(System.out::println);

    // 页面结果会返回以下信息
    System.out.println("total: " + photos.getTotal());
    System.out.println("has_more: " + photos.getHasMore());
    System.out.println("logID: " + photos.getLogID());
    for (Photo item : photos.getItems()) {
      System.out.println(item);
    }
  }
}
