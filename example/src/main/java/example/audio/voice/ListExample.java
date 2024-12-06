package example.audio.voice;

import com.coze.openapi.client.audio.voices.ListVoiceReq;
import com.coze.openapi.client.audio.voices.model.Voice;
import com.coze.openapi.client.common.pagination.PageResp;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import java.util.Iterator;

public class ListExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI.Builder()
                .baseURL(System.getenv("COZE_API_BASE"))
                .auth(authCli)
                .readTimeout(10000)
                .connectTimeout(10000)
                .build();

        PageResp<Voice> resp = coze.audio().voices().list(ListVoiceReq.builder().pageSize(10).build());
        Iterator<Voice> iterator = resp.getIterator();
        while (iterator.hasNext()) {
            Voice voice = iterator.next();
            System.out.println(voice);
        }
    }
} 