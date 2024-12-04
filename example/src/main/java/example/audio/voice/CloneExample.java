package example.audio.voice;

import com.coze.openapi.client.audio.voices.CloneVoiceReq;
import com.coze.openapi.client.audio.voices.CloneVoiceResult;
import com.coze.openapi.client.audio.common.AudioFormat;
import com.coze.openapi.client.audio.common.LanguageCode;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

public class CloneExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI.Builder()
                .baseURL(System.getenv("COZE_API_BASE_URL"))
                .auth(authCli)
                .readTimeout(10000)
                .connectTimeout(10000)
                .build();
        String voiceFilePath = System.getenv("VOICE_FILE_PATH");

        CloneVoiceReq req = CloneVoiceReq.builder()
                                         .filePath(voiceFilePath)
                                         .voiceName("your voice name")
                                         .audioFormat(AudioFormat.M4A)
                                         .language(LanguageCode.ZH)
                                         .voiceID(System.getenv("VOICE_ID"))
                                         .text("your text")
                                         .previewText("your preview text")
                                         .build();
        CloneVoiceResult resp = coze.audio().voices().clone(req);
        System.out.println("=============== clone voice ===============");
        System.out.println(resp);
        System.out.println("=============== clone voice ===============");
    }
} 