package example.audio.room;

import com.coze.openapi.client.audio.rooms.CreateRoomReq;
import com.coze.openapi.client.audio.rooms.CreateRoomResp;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

public class CreateExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        String botID = System.getenv("BOT_ID");
        String voiceId = System.getenv("VOICE_ID");

        CreateRoomReq req = CreateRoomReq.builder()
                                         .botID(botID)
                                         .voiceID(voiceId)
                                         .build();
        CreateRoomResp resp = coze.audio().room().createRoom(req);
        System.out.println("=============== create room ===============");
        System.out.println(resp);
        System.out.println("=============== create room ===============");
    }
} 