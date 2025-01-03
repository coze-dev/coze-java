package example.datasets;

import java.util.Iterator;

import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.client.dataset.ListDatasetReq;
import com.coze.openapi.client.dataset.model.Dataset;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

public class DatasetListExample {
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

    ListDatasetReq req = ListDatasetReq.builder().spaceID(spaceID).pageSize(2).pageNum(1).build();

    // 使用迭代器自动获取下一页
    PageResp<Dataset> datasets = coze.datasets().list(req);
    Iterator<Dataset> iter = datasets.getIterator();
    iter.forEachRemaining(System.out::println);

    // 页面结果会返回以下信息
    System.out.println("total: " + datasets.getTotal());
    System.out.println("has_more: " + datasets.getHasMore());
    System.out.println("logID: " + datasets.getLogID());
    for (Dataset item : datasets.getItems()) {
      System.out.println(item);
    }
  }
}
