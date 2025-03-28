package example.chat;

import java.util.Arrays;
import java.util.Collections;

import com.coze.openapi.client.chat.CreateChatReq;
import com.coze.openapi.client.chat.model.ChatEvent;
import com.coze.openapi.client.chat.model.ChatEventType;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.client.connversations.message.model.MessageObjectString;
import com.coze.openapi.client.files.UploadFileReq;
import com.coze.openapi.client.files.model.FileInfo;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

import io.reactivex.Flowable;

/*
 * This example is about how to use the streaming interface to start a chat request
 * with image upload and handle chat events
 * */
public class ChatWithImageExample {
  public static void main(String[] args) {

    // Get an access_token through personal access token or oauth.
    String token = System.getenv("COZE_API_TOKEN");
    String botID = System.getenv("PUBLISHED_BOT_ID");
    String userID = System.getenv("USER_ID");

    TokenAuth authCli = new TokenAuth(token);

    // Init the Coze client through the access_token.
    CozeAPI coze =
        new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(authCli)
            .readTimeout(10000)
            .build();
    ;

    // Call the upload file interface to get the image id.
    String imagePath = System.getenv("IMAGE_FILE_PATH");
    FileInfo imageInfo = coze.files().upload(UploadFileReq.of(imagePath)).getFileInfo();

    /*
     * Step one, create chat
     * Call the coze.chat().stream() method to create a chat. The create method is a streaming
     * chat and will return a Flowable ChatEvent. Developers should iterate the iterator to get
     * chat event and handle them.
     * */
    CreateChatReq req =
        CreateChatReq.builder()
            .botID(botID)
            .userID(userID)
            .messages(
                Collections.singletonList(
                    Message.buildUserQuestionObjects(
                        Arrays.asList(
                            MessageObjectString.buildText("Describe this picture"),
                            MessageObjectString.buildImageByID(imageInfo.getID())))))
            .build();

    Flowable<ChatEvent> resp = coze.chat().stream(req);
    resp.blockingForEach(
        event -> {
          if (ChatEventType.CONVERSATION_MESSAGE_DELTA.equals(event.getEvent())) {
            System.out.print(event.getMessage().getContent());
          }
        });
  }
}
