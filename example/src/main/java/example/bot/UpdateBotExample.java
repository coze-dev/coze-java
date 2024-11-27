package example.bot;

import com.coze.openapi.client.bots.UpdateBotReq;
import com.coze.openapi.client.bots.model.BotOnboardingInfo;
import com.coze.openapi.client.bots.model.BotPromptInfo;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import java.util.Arrays;

public class UpdateBotExample {

    public static void main(String[] args) {
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);
        CozeAPI coze = new CozeAPI(authCli);
        String botID = System.getenv("BOT_ID");

        UpdateBotReq.UpdateBotReqBuilder builder = UpdateBotReq.builder();
        BotPromptInfo promptInfo = new BotPromptInfo();
        promptInfo.setPrompt("你是一个测试 bot, 你是一个测试 bot2, 你是一个测试 bot3");
        BotOnboardingInfo onboardingInfo = new BotOnboardingInfo();
        onboardingInfo.setPrologue("这是一个 bot");
        onboardingInfo.setSuggestedQuestions(Arrays.asList("你是一个测试 bot", "你是一个测试 bot2", "你是一个测试 bot3"));
        builder.description("这是一个测试 bot")
               .name("测试智能体，修改后")
               .promptInfo(promptInfo)
               .onboardingInfo(onboardingInfo);

        try {
            coze.bots().update(botID, builder.build());
            System.out.println("update success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 