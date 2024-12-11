package example.audio.speech;

import com.coze.openapi.client.audio.speech.CreateSpeechReq;
import com.coze.openapi.client.audio.speech.CreateSpeechResp;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import java.io.IOException;

public class CreateExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI.Builder()
                .baseURL(System.getenv("COZE_API_BASE"))
                .auth(authCli)
                .readTimeout(10000)
                .build();;
        String saveFilePath = System.getenv("SAVE_FILE_PATH");
        String voiceID = System.getenv("COZE_VOICE_ID");
        String content = "快来试一试吧";

        CreateSpeechReq req = CreateSpeechReq.builder()
                                             .input(content)
                                             .voiceID(voiceID)
                                             .build();

        CreateSpeechResp resp = coze.audio().speech().create(req);
        System.out.println(resp);
        try {
            resp.writeToFile(saveFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 