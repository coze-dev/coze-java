package example.conversation;

import com.coze.openapi.client.connversations.CreateConversationReq;
import com.coze.openapi.client.connversations.CreateConversationResp;
import com.coze.openapi.client.connversations.GetConversationReq;
import com.coze.openapi.client.connversations.GetConversationResp;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.connversations.message.model.MessageObjectString;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CreateConversationExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        Map<String, String> metaData = new HashMap<>();
        metaData.put("user_id", "1234567890");
        metaData.put("user_name", "test user");

        System.out.println("=============== create conversation ===============");
        CreateConversationReq.CreateConversationReqBuilder builder = CreateConversationReq.builder();
        builder.metaData(metaData);
        builder.messages(Arrays.asList(
                Message.buildAssistantAnswer("你好"),
                Message.buildUserQuestionText("你是谁"),
                Message.buildUserQuestionObjects(Arrays.asList(
                        MessageObjectString.buildText("你好"),
                        MessageObjectString.buildImage(null, System.getenv("PICTURE_URL")),
                        MessageObjectString.buildFile(null, System.getenv("PICTURE_URL"))
                ))
        ));
        CreateConversationResp resp = coze.conversations().create(builder.build());
        System.out.println(resp);

        String conversationID = resp.getId();
        System.out.println("=============== get conversation ===============");
        GetConversationResp getResp = coze.conversations().retrieve(GetConversationReq.of(conversationID));
        System.out.println(getResp);
    }
} 