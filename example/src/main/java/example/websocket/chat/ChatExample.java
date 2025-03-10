package example.websocket.chat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.coze.openapi.client.audio.common.AudioFormat;
import com.coze.openapi.client.audio.speech.CreateSpeechReq;
import com.coze.openapi.client.audio.speech.CreateSpeechResp;
import com.coze.openapi.client.chat.model.ChatToolCall;
import com.coze.openapi.client.chat.model.ToolOutput;
import com.coze.openapi.client.websocket.event.downstream.*;
import com.coze.openapi.client.websocket.event.upstream.ConversationChatSubmitToolOutputsEvent;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.service.websocket.chat.WebsocketsChatCallbackHandler;
import com.coze.openapi.service.service.websocket.chat.WebsocketsChatClient;
import com.coze.openapi.service.service.websocket.chat.WebsocketsChatCreateReq;
import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.annotation.JsonProperty;

import example.utils.ExampleUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
This example describes how to use the chat interface to initiate conversations,
poll the status of the conversation, and obtain the messages after the conversation is completed.
* */
public class ChatExample {

  private static boolean isDone;

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  private static class Weather {
    @JsonProperty("weather")
    private String weather;
  }

  private static class CallbackHandler extends WebsocketsChatCallbackHandler {
    private final ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 10); // 分配 10MB 缓冲区

    public CallbackHandler() {
      super();
    }

    @Override
    public void onChatCreated(WebsocketsChatClient client, ChatCreatedEvent event) {
      System.out.println(event);
      //            client.sendEvent(new BaseEvent());
    }

    @Override
    public void onConversationMessageDelta(
        WebsocketsChatClient client, ConversationMessageDeltaEvent event) {
      System.out.printf("Revieve: %s\n", event.getData().getContent());
    }

    @Override
    public void onError(WebsocketsChatClient client, ErrorEvent event) {
      System.out.println(event);
    }

    @Override
    public void onInputAudioBufferCompleted(
        WebsocketsChatClient client, InputAudioBufferCompletedEvent event) {
      System.out.println("========= Input Audio Buffer Completed =========");
      System.out.println(event);
    }

    @Override
    public void onConversationAudioCompleted(
        WebsocketsChatClient client, ConversationAudioCompletedEvent event) {
      try {
        ExampleUtils.writePcmToWavFile(buffer.array(), "output.wav");
        System.out.println("========= Output Audio Completed =========");
        isDone = true;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    @Override
    public void onClientException(WebsocketsChatClient client, Throwable e) {
      e.printStackTrace();
    }

    @Override
    public void onConversationAudioDelta(
        WebsocketsChatClient client, ConversationAudioDeltaEvent event) {
      byte[] audioData = event.getData().getAudio();
      buffer.put(audioData);
    }

    @Override
    public void onConversationChatRequiresAction(
        WebsocketsChatClient client, ConversationChatRequiresActionEvent event) {
      List<ToolOutput> toolOutputs = new ArrayList<>();
      for (ChatToolCall call :
          event.getData().getRequiredAction().getSubmitToolOutputs().getToolCalls()) {
        toolOutputs.add(
            ToolOutput.builder()
                .toolCallID(call.getID())
                // 模拟端插件返回
                .output(Utils.toJson(new Weather("今天深圳的天气是 10 到 20 摄氏度")))
                .build());
      }
      ConversationChatSubmitToolOutputsEvent.Data data =
          ConversationChatSubmitToolOutputsEvent.Data.builder()
              .chatID(event.getData().getID())
              .toolOutputs(toolOutputs)
              .build();
      client.conversationChatSubmitToolOutputs(data);
      System.out.println("========= Conversation Chat Submit Tool Outputs =========");
    }
  }

  // For non-streaming chat API, it is necessary to create a chat first and then poll the chat
  // results.
  public static void main(String[] args) throws Exception {
    // Get an access_token through personal access token or oauth.
    String token = System.getenv("COZE_API_TOKEN");
    String botID = System.getenv("PUBLISHED_BOT_ID");
    String voiceID = System.getenv("COZE_VOICE_ID");
    TokenAuth authCli = new TokenAuth(token);

    // Init the Coze client through the access_token.
    CozeAPI coze =
        new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(authCli)
            .readTimeout(10000)
            .build();

    WebsocketsChatClient client = null;
    try {
      client =
          coze.websockets()
              .chat()
              .create(new WebsocketsChatCreateReq(botID, new CallbackHandler()));
      CreateSpeechResp speechResp =
          coze.audio()
              .speech()
              .create(
                  CreateSpeechReq.builder()
                      .input("今天深圳的天气怎么样?")
                      .voiceID(voiceID)
                      .responseFormat(AudioFormat.WAV)
                      .sampleRate(24000)
                      .build());

      try (InputStream inputStream = speechResp.getResponse().byteStream()) {
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
          client.inputAudioBufferAppend(Arrays.copyOf(buffer, bytesRead));
          // 模拟人说话的间隔
          TimeUnit.MILLISECONDS.sleep(100);
        }
        client.inputAudioBufferComplete();
      } catch (IOException e) {
        e.printStackTrace();
      }
      while (!isDone) {
        TimeUnit.MILLISECONDS.sleep(100);
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (client != null) {
        client.close();
      }
      coze.shutdownExecutor();
    }
  }
}
