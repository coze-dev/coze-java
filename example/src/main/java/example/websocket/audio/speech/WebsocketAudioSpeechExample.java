package example.websocket.audio.speech;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import com.coze.openapi.client.websocket.event.downstream.*;
import com.coze.openapi.client.websocket.event.model.OutputAudio;
import com.coze.openapi.client.websocket.event.model.PCMConfig;
import com.coze.openapi.client.websocket.event.model.SpeechUpdateEventData;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.service.websocket.audio.speech.WebsocketsAudioSpeechCallbackHandler;
import com.coze.openapi.service.service.websocket.audio.speech.WebsocketsAudioSpeechClient;
import com.coze.openapi.service.service.websocket.audio.speech.WebsocketsAudioSpeechCreateReq;

import example.utils.ExampleUtils;

/*
This example demonstrates how to use the WebSocket audio speech interface to:
- Create a speech session
- Configure audio parameters
- Send text input
- Process and save the resulting audio output
*/
public class WebsocketAudioSpeechExample {

  private static boolean isDone = false;

  private static class CallbackHandler extends WebsocketsAudioSpeechCallbackHandler {
    private final ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 10); // 分配 10MB 缓冲区

    public CallbackHandler() {
      super();
    }

    // 语音创建成功事件 (speech.created)
    @Override
    public void onSpeechCreated(WebsocketsAudioSpeechClient client, SpeechCreatedEvent event) {
      System.out.println("==== Speech Created ====");
      System.out.println(event);
    }

    // 语音配置更新事件 (speech.update)
    @Override
    public void onSpeechUpdated(WebsocketsAudioSpeechClient client, SpeechUpdatedEvent event) {
      System.out.println("==== Speech Updated ====");
      System.out.println(event);
    }

    // 语音数据更新事件 (speech.audio.update)
    @Override
    public void onSpeechAudioUpdate(
        WebsocketsAudioSpeechClient client, SpeechAudioUpdateEvent event) {
      buffer.put(event.getDelta());
    }

    // 语音数据完成事件 (speech.audio.completed)
    @Override
    public void onSpeechAudioCompleted(
        WebsocketsAudioSpeechClient client, SpeechAudioCompletedEvent event) {
      try {
        ExampleUtils.writePcmToWavFile(buffer.array(), "output_speech.wav");
        System.out.println("========= On Speech Audio Completed =========");
        isDone = true;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    // 文本缓冲区完成事件 (input_text_buffer.completed)
    @Override
    public void onInputTextBufferCompleted(
        WebsocketsAudioSpeechClient client, InputTextBufferCompletedEvent event) {
      System.out.println("==== Input Text Buffer Completed ====");
      System.out.println(event);
    }

    @Override
    public void onError(WebsocketsAudioSpeechClient client, ErrorEvent event) {
      System.out.println(event);
    }
  }

  // For non-streaming chat API, it is necessary to create a chat first and then poll the chat
  // results.
  public static void main(String[] args) throws Exception {
    // Get an access_token through personal access token or oauth.
    String token = System.getenv("COZE_API_TOKEN");
    String voiceID = System.getenv("COZE_VOICE_ID");
    TokenAuth authCli = new TokenAuth(token);

    // Init the Coze client through the access_token.
    CozeAPI coze =
        new CozeAPI.Builder()
            .baseURL(System.getenv("COZE_API_BASE"))
            .auth(authCli)
            .readTimeout(10000)
            .build();

    WebsocketsAudioSpeechClient client = null;
    try {
      client =
          coze.websockets()
              .audio()
              .speech()
              .create(new WebsocketsAudioSpeechCreateReq(new CallbackHandler()));
      OutputAudio outputAudio =
          OutputAudio.builder()
              .voiceId(voiceID)
              .codec("pcm")
              .speechRate(50)
              .pcmConfig(PCMConfig.builder().sampleRate(24000).build())
              .build();
      client.speechUpdate(new SpeechUpdateEventData(outputAudio));
      client.inputTextBufferAppend("hello world, nice to meet you!");
      client.inputTextBufferComplete();
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
