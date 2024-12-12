package example.bot;

import com.coze.openapi.client.bots.*;
import com.coze.openapi.client.bots.model.BotOnboardingInfo;
import com.coze.openapi.client.bots.model.BotPromptInfo;
import com.coze.openapi.client.files.UploadFileReq;
import com.coze.openapi.client.files.UploadFileResp;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.auth.TokenAuth;

import java.util.Arrays;

/*
This example is for describing how to create a bot, update a bot and publish a bot to the API.
The document for those interface:
* */
public class BotPublishExample {

    public static void main(String[] args) {
        // Get an access_token through personal access token or oauth.
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);

        // Init the Coze client through the access_token.
        CozeAPI coze = new CozeAPI.Builder()
                .baseURL(System.getenv("COZE_API_BASE"))
                .auth(authCli)
                .readTimeout(10000)
                .build();;

        /*
        * step one, create a bot
        * */
        String workspaceID = System.getenv("WORKSPACE_ID");

        // set the prompt of your bot
        BotPromptInfo promptInfo = new BotPromptInfo("your prompt");

        // set the onboarding info of your bot
        BotOnboardingInfo onboardingInfo = BotOnboardingInfo.builder()
                .prologue("the prologue of your bot")
                .suggestedQuestions(Arrays.asList("question 1", "question 2"))
                .build();
        // Call the upload file interface to get the avatar id.
        String avatarPath = System.getenv("IMAGE_FILE_PATH");
        UploadFileResp avatarInfo = coze.files().upload(UploadFileReq.of(avatarPath));
        System.out.println(avatarInfo);

        // build the request
        CreateBotReq createReq = CreateBotReq.builder()
                .spaceID(workspaceID)
                .description("the description of your bot")
                .name("the name of your bot")
                .promptInfo(promptInfo)
                .onboardingInfo(onboardingInfo)
                .iconFileID(avatarInfo.getFileInfo().getID())
                .build();

        // Invoke the creation interface to create a bot in the draft status, and you can get the bot id.
        CreateBotResp createResp = coze.bots().create(createReq);
        String botID = createResp.getBotID();
        System.out.println(createResp);

        /*
         * step two, update the bot, you can update the bot after being created
         * in this example, we will update the avatar of the bot
         */
        PublishBotReq publishReq = PublishBotReq.builder()
                .botID(botID)
                .connectorIDs(Arrays.asList("1024"))
                .build();

        // Call the publishing interface to publish the bot on the api channel.
        PublishBotResp updateResp = coze.bots().publish(publishReq);
        System.out.println(updateResp);

        /*
         * step three, you can also modify the bot configuration and republish it.
         * in this example, we will update the avatar of the bot
         */

        // set the onboarding info of your bot
        // Call the upload file interface to get the avatar id.
        String newAvatarPath = System.getenv("IMAGE_FILE_PATH");
        UploadFileResp newAvatarInfo = coze.files().upload(UploadFileReq.of(newAvatarPath));
        System.out.println(newAvatarInfo);
        // build the request
        UpdateBotReq updateReq = UpdateBotReq.builder()
                .botID(botID)
                .iconFileID(newAvatarInfo.getFileInfo().getID())
                .build();
        // Invoke the update interface to update a bot, It means success that no exception has been thrown.
        coze.bots().update(updateReq);
        updateResp = coze.bots().publish(publishReq);
        System.out.println(updateResp);

    }
} 