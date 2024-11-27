package example.bot;

import com.coze.openapi.client.bots.CreateBotReq;
import com.coze.openapi.client.bots.CreateBotResp;
import com.coze.openapi.client.bots.model.BotOnboardingInfo;
import com.coze.openapi.client.bots.model.BotPromptInfo;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

public class CreateBotExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        String spaceID = System.getenv("SPACE_ID");

        CreateBotReq.CreateBotReqBuilder builder = CreateBotReq.builder();
        BotPromptInfo promptInfo = new BotPromptInfo();
        promptInfo.setPrompt("你是一个测试 bot");
        BotOnboardingInfo onboardingInfo = new BotOnboardingInfo();
        onboardingInfo.setPrologue("这是一个 bot");
        onboardingInfo.setSuggestedQuestions(null);
        builder.spaceID(spaceID)
               .description("这是一个测试 bot")
               .name("test bot")
               .promptInfo(promptInfo)
               .onboardingInfo(onboardingInfo);

        CreateBotResp resp = coze.bots().create(builder.build());
        System.out.println(resp);
    }
} 