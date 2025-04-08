package example.commerce.benefit.limitations;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import com.coze.openapi.client.commerce.benefit.common.ActiveMode;
import com.coze.openapi.client.commerce.benefit.common.BenefitEntityType;
import com.coze.openapi.client.commerce.benefit.common.BenefitType;
import com.coze.openapi.client.commerce.benefit.limitation.*;
import com.coze.openapi.client.commerce.benefit.limitation.model.BenefitInfo;
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

    //      for (int i = 0; i < 5; i++) {
    //          create(coze);
    //      }
    TimeUnit.SECONDS.sleep(1);
    list(coze);
  }

  private static void list(CozeAPI coze) {
    ListBenefitLimitationReq listReq =
        ListBenefitLimitationReq.builder()
            .entityType(BenefitEntityType.BENEFIT_ENTITY_TYPE_SINGLE_DEVICE)
            .pageSize(4)
            .build();
    PageResp<BenefitInfo> listResp = coze.commerces().benefits().limitations().list(listReq);
    Iterator<BenefitInfo> liter = listResp.getIterator();
    Integer count = 0;
    while (liter.hasNext()) {
      BenefitInfo benefitInfo = liter.next();
      System.out.println(benefitInfo);
      count++;
    }
    System.out.println(count);
    System.out.println("========list done========");
  }

  private static BenefitInfo create(CozeAPI coze) {
    long now = System.currentTimeMillis();
    BenefitInfo benefitInfo =
        BenefitInfo.builder()
            .activeMode(ActiveMode.ACTIVE_MODE_ABSOLUTE_TIME)
            .startedAt(now / 1000)
            .endedAt(2 * now / 1000)
            .benefitType(BenefitType.BENEFIT_TYPE_RESOURCE_POINT)
            .limit(1000)
            .build();

    CreateBenefitLimitationReq req =
        CreateBenefitLimitationReq.builder()
            .benefitInfo(benefitInfo)
            .entityID(generateRandomString(16))
            .entityType(BenefitEntityType.BENEFIT_ENTITY_TYPE_SINGLE_DEVICE)
            .build();

    CreateBenefitLimitationResp resp = coze.commerces().benefits().limitations().create(req);
    System.out.println(resp);
    return resp.getBenefitInfo();
  }

  private static void update(CozeAPI coze, String benefitID) {
    UpdateBenefitLimitationReq req =
        UpdateBenefitLimitationReq.builder().benefitID(benefitID).limit(2000).build();
    UpdateBenefitLimitationResp resp = coze.commerces().benefits().limitations().update(req);
    System.out.println(resp);
  }

  public static String generateRandomString(int length) {
    SecureRandom random = new SecureRandom();
    byte[] bytes = new byte[length];
    random.nextBytes(bytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, length);
  }
}
