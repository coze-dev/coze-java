package example.audio.room;

import com.coze.openapi.client.audio.rooms.CreateRoomReq;
import com.coze.openapi.client.audio.rooms.CreateRoomResp;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

public class CreateAudioRoomsExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI.Builder()
                .baseURL(System.getenv("COZE_API_BASE"))
                .auth(authCli)
                .readTimeout(10000)
                .build();
        String botID = System.getenv("COZE_BOT_ID");
        String voiceID = System.getenv("COZE_VOICE_ID");

        CreateRoomReq req = CreateRoomReq.builder()
                                         .botID(botID)
                                         .voiceID(voiceID)
                                         .build();
        CreateRoomResp resp = coze.audio().rooms().create(req);
    }
} 