package example.websocket.audio.transcriptions;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.coze.openapi.client.audio.common.AudioFormat;
import com.coze.openapi.client.audio.speech.CreateSpeechReq;
import com.coze.openapi.client.audio.speech.CreateSpeechResp;
import com.coze.openapi.client.websocket.event.downstream.*;
import com.coze.openapi.client.websocket.event.model.InputAudio;
import com.coze.openapi.client.websocket.event.model.TranscriptionsUpdateEventData;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.service.websocket.audio.transcriptions.WebsocketsAudioTranscriptionsCallbackHandler;
import com.coze.openapi.service.service.websocket.audio.transcriptions.WebsocketsAudioTranscriptionsClient;
import com.coze.openapi.service.service.websocket.audio.transcriptions.WebsocketsAudioTranscriptionsCreateReq;

/*
This example demonstrates how to use the WebSocket transcription API to transcribe audio data,
process transcription events, and handle the results through callback methods.
 */
public class WebsocketTranscriptionsExample {

  public static boolean isDone = false;

  private static class CallbackHandler extends WebsocketsAudioTranscriptionsCallbackHandler {
    private final ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 10); // 分配 10MB 缓冲区

    public CallbackHandler() {
      super();
    }

    @Override
    public void onError(WebsocketsAudioTranscriptionsClient client, ErrorEvent event) {
      System.out.println(event);
    }

    @Override
    public void onClientException(WebsocketsAudioTranscriptionsClient client, Throwable e) {
      e.printStackTrace();
    }

    // 转录配置更新事件 (transcriptions.updated)
    @Override
    public void onTranscriptionsUpdated(
        WebsocketsAudioTranscriptionsClient client, TranscriptionsUpdatedEvent event) {
      System.out.println("=== Transcriptions Updated ===");
      System.out.println(event);
    }

    // 转录创建事件 (transcriptions.created)
    @Override
    public void onTranscriptionsCreated(
        WebsocketsAudioTranscriptionsClient client, TranscriptionsCreatedEvent event) {
      System.out.println("=== Transcriptions Created ===");
      System.out.println(event);
    }

    // 转录消息更新事件 (transcriptions.message.update)
    @Override
    public void onTranscriptionsMessageUpdate(
        WebsocketsAudioTranscriptionsClient client, TranscriptionsMessageUpdateEvent event) {
      System.out.println(event.getData().getContent());
    }

    // 转录消息完成事件 (transcriptions.message.completed)
    @Override
    public void onTranscriptionsMessageCompleted(
        WebsocketsAudioTranscriptionsClient client, TranscriptionsMessageCompletedEvent event) {
      System.out.println("=== Transcriptions Message Completed ===");
      System.out.println(event);
      isDone = true;
    }

    // 语音缓冲区完成事件 (input_audio_buffer.completed)
    @Override
    public void onInputAudioBufferCompleted(
        WebsocketsAudioTranscriptionsClient client, InputAudioBufferCompletedEvent event) {
      System.out.println("=== Input Audio Buffer Completed ===");
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

    WebsocketsAudioTranscriptionsClient client = null;
    try {
      client =
          coze.websockets()
              .audio()
              .transcriptions()
              .create(new WebsocketsAudioTranscriptionsCreateReq(new CallbackHandler()));
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

      InputAudio inputAudio =
          InputAudio.builder().sampleRate(24000).codec("pcm").format("wav").channel(2).build();
      client.transcriptionsUpdate(new TranscriptionsUpdateEventData(inputAudio));

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
