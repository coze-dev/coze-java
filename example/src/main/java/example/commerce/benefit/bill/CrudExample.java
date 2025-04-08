package example.commerce.benefit.bill;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import com.coze.openapi.client.commerce.benefit.bill.CreateBillDownloadTaskReq;
import com.coze.openapi.client.commerce.benefit.bill.CreateBillDownloadTaskResp;
import com.coze.openapi.client.commerce.benefit.bill.ListBillDownloadTaskReq;
import com.coze.openapi.client.commerce.benefit.bill.model.BillTaskInfo;
import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

public class CrudExample {
  public static void main(String[] args) throws Exception {
    String token = System.getenv("COZE_API_TOKEN");
    TokenAuth authCli = new TokenAuth(token);

    // Init the Coze client through the access_token.
    CozeAPI coze =
        new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(authCli)
            .readTimeout(10000)
            .build();

    for (int i = 0; i < 5; i++) {
      create(coze);
    }
    TimeUnit.SECONDS.sleep(1);
    list(coze);
  }

  private static void list(CozeAPI coze) {
    ListBillDownloadTaskReq listReq = ListBillDownloadTaskReq.builder().pageSize(4).build();
    PageResp<BillTaskInfo> listResp = coze.commerces().benefits().bills().list(listReq);
    Iterator<BillTaskInfo> liter = listResp.getIterator();
    Integer count = 0;
    while (liter.hasNext()) {
      BillTaskInfo BillTaskInfo = liter.next();
      System.out.println(BillTaskInfo);
      count++;
    }
    System.out.println(count);
    System.out.println("========list done========");
  }

  private static BillTaskInfo create(CozeAPI coze) {
    CreateBillDownloadTaskReq req =
        CreateBillDownloadTaskReq.builder().startedAt(1743004800).endedAt(1745424000).build();

    CreateBillDownloadTaskResp resp = coze.commerces().benefits().bills().create(req);
    System.out.println(resp);
    return resp.getBillTaskInfo();
  }
}
