package example.audio.transcriptions;

import com.coze.openapi.client.audio.transcriptions.CreateTranscriptionsReq;
import com.coze.openapi.client.audio.transcriptions.CreateTranscriptionsResp;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

public class CreateTranscriptionExample {
  public static void main(String[] args) {
    String token = System.getenv("COZE_API_TOKEN");
    TokenAuth authCli = new TokenAuth(token);
    CozeAPI coze =
        new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(authCli)
            .readTimeout(10000)
            .connectTimeout(10000)
            .build();
    String voiceFilePath = System.getenv("VOICE_FILE_PATH");

    CreateTranscriptionsReq req = CreateTranscriptionsReq.of(voiceFilePath);
    CreateTranscriptionsResp resp = coze.audio().transcription().create(req);
    System.out.println(resp);
  }
}
