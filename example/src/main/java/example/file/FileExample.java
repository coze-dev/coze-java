package example.file;

import com.coze.openapi.client.files.model.FileInfo;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

public class FileExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        String filePath = System.getenv("FILE_PATH");

        //*** 上传文件 ***//
        FileInfo fileInfo = coze.files().upload(filePath);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //*** 获取文件 ***//
        FileInfo retrievedInfo = coze.files().retrieve(fileInfo.getId());
        System.out.println(retrievedInfo);
    }
} 