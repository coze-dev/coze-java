package example.audio.voice;

import com.coze.openapi.client.audio.voices.CloneVoiceReq;
import com.coze.openapi.client.audio.voices.CloneVoiceResp;
import com.coze.openapi.client.audio.common.AudioFormat;
import com.coze.openapi.client.audio.common.LanguageCode;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

public class CloneExample {

    public static void main(String[] args) {
        String token = System.getenv("TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        String voiceFilePath = System.getenv("VOICE_FILE_PATH");

        CloneVoiceReq req = CloneVoiceReq.builder()
                                         .filePath(voiceFilePath)
                                         .voiceName("ggq test")
                                         .audioFormat(AudioFormat.M4A)
                                         .language(LanguageCode.ZH)
                                         .voiceId("7433805584002154522")
                                         .text("音色克隆测试")
                                         .previewText("这是我的声音，你也来试一试吧")
                                         .build();
        CloneVoiceResp resp = coze.audio().voice().clone(req);
        System.out.println("=============== clone voice ===============");
        System.out.println(resp);
        System.out.println("=============== clone voice ===============");
    }
} 