package example.audio.voice;

import com.coze.openapi.client.audio.voices.ListVoiceReq;
import com.coze.openapi.client.audio.voices.model.Voice;
import com.coze.openapi.client.common.pagination.PageResult;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import java.util.Iterator;

public class ListExample {

    public static void main(String[] args) {
        String token = System.getenv("TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);

        PageResult<Voice> resp = coze.audio().voice().list(ListVoiceReq.builder().pageSize(1).build());
        Iterator<Voice> iterator = resp.getIterator();
        while (iterator.hasNext()) {
            Voice voice = iterator.next();
            System.out.println("=============== voice ===============");
            System.out.println(voice);
            System.out.println("=============== voice ===============");
        }
    }
} 