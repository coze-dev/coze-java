package example.audio.speech;

import com.coze.openapi.client.audio.speech.CreateSpeechReq;
import com.coze.openapi.client.common.FileResponse;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import java.io.IOException;

public class CreateExample {

    public static void main(String[] args) {
        String token = System.getenv("TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        String saveFilePath = "";
        String voiceID = "";
        String content = "快来试一试吧";

        CreateSpeechReq req = CreateSpeechReq.builder()
                                             .input(content)
                                             .voiceId(voiceID)
                                             .build();

        FileResponse resp = coze.audio().speech().create(req);
        System.out.println("=============== create speech ===============");
        System.out.println(resp);
        System.out.println("=============== create speech ===============");
        try {
            resp.writeToFile(saveFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 