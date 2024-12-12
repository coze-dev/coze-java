/* (C)2024 */
package example.audio.voice;

import com.coze.openapi.client.audio.common.AudioFormat;
import com.coze.openapi.client.audio.voices.CloneVoiceReq;
import com.coze.openapi.client.audio.voices.CloneVoiceResp;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

public class VoiceCloneExample {

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

    CloneVoiceReq req =
        CloneVoiceReq.builder()
            .filePath(voiceFilePath)
            .voiceName("your voice name")
            .audioFormat(AudioFormat.M4A)
            .build();
    CloneVoiceResp resp = coze.audio().voices().clone(req);
    System.out.println(resp);
  }
}
