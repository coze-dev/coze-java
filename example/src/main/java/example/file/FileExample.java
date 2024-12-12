/* (C)2024 */
package example.file;

import com.coze.openapi.client.files.RetrieveFileReq;
import com.coze.openapi.client.files.UploadFileReq;
import com.coze.openapi.client.files.model.FileInfo;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

public class FileExample {

  public static void main(String[] args) {
    String token = System.getenv("COZE_API_TOKEN");
    TokenAuth authCli = new TokenAuth(token);
    CozeAPI coze =
        new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(authCli)
            .readTimeout(10000)
            .build();
    ;
    String filePath = System.getenv("FILE_PATH");

    // *** 上传文件 ***//
    FileInfo fileInfo = coze.files().upload(UploadFileReq.of(filePath)).getFileInfo();

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // *** 获取文件 ***//
    FileInfo retrievedInfo =
        coze.files().retrieve(RetrieveFileReq.of(fileInfo.getID())).getFileInfo();
    System.out.println(retrievedInfo);
  }
}
