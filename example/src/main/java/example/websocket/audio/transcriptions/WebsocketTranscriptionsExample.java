package example.websocket.audio.transcriptions;

import com.coze.openapi.client.audio.common.AudioFormat;
import com.coze.openapi.client.audio.speech.CreateSpeechReq;
import com.coze.openapi.client.audio.speech.CreateSpeechResp;
import com.coze.openapi.client.websocket.event.downstream.*;
import com.coze.openapi.client.websocket.event.model.InputAudio;
import com.coze.openapi.client.websocket.event.model.TranscriptionsUpdateEventData;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.service.websocket.audio.transcriptions.WebsocketAudioTranscriptionsCallbackHandler;
import com.coze.openapi.service.service.websocket.audio.transcriptions.WebsocketAudioTranscriptionsClient;
import com.coze.openapi.service.service.websocket.audio.transcriptions.WebsocketAudioTranscriptionsCreateReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

/*
This example describes how to use the chat interface to initiate conversations,
poll the status of the conversation, and obtain the messages after the conversation is completed.
* */
public class WebsocketTranscriptionsExample {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Weather {
        @JsonProperty("weather")
        private String weather;
    }

    private static class CallbackHandler extends WebsocketAudioTranscriptionsCallbackHandler {
        private final ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 10); // 分配 10MB 缓冲区

        public CallbackHandler() {
            super();
        }


        @Override
        public void onError(WebsocketAudioTranscriptionsClient client, ErrorEvent event) {
            System.out.println(event);
        }


        @Override
        public void onClientException(WebsocketAudioTranscriptionsClient client, Throwable e) {
            e.printStackTrace();
        }

        // 转录配置更新事件 (transcriptions.updated)
        @Override
        public void onTranscriptionsUpdated(
                WebsocketAudioTranscriptionsClient client, TranscriptionsUpdatedEvent event) {
            System.out.println("=== Transcriptions Updated ===");
            System.out.println(event);
        }

        // 转录创建事件 (transcriptions.created)
        @Override
        public void onTranscriptionsCreated(
                WebsocketAudioTranscriptionsClient client, TranscriptionsCreatedEvent event) {
            System.out.println("=== Transcriptions Created ===");
            System.out.println(event);
        }

        // 转录消息更新事件 (transcriptions.message.update)
        @Override
        public void onTranscriptionsMessageUpdate(
                WebsocketAudioTranscriptionsClient client, TranscriptionsMessageUpdateEvent event) {
            System.out.println(event.getData().getContent());
        }

        // 转录消息完成事件 (transcriptions.message.completed)
        @Override
        public void onTranscriptionsMessageCompleted(
                WebsocketAudioTranscriptionsClient client, TranscriptionsMessageCompletedEvent event) {
            System.out.println("=== Transcriptions Message Completed ===");
            System.out.println(event);
        }


        // 语音缓冲区完成事件 (input_audio_buffer.completed)
        @Override
        public void onInputAudioBufferCompleted(
                WebsocketAudioTranscriptionsClient client, InputAudioBufferCompletedEvent event) {
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

        WebsocketAudioTranscriptionsClient client = null;
        try {
            client =
                    coze.websocket().audio().transcriptions().create(new WebsocketAudioTranscriptionsCreateReq(new CallbackHandler()));
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

            InputAudio inputAudio = InputAudio.builder()
                    .sampleRate(24000)
                    .codec("pcm")
                    .format("wav")
                    .channel(2)
                    .build();
            client.transcriptionsUpdate(new TranscriptionsUpdateEventData(inputAudio));

            try (InputStream inputStream = speechResp.getResponse().byteStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    // 将读取到的字节转换为 base64 编码
                    String base64Data = Base64.getEncoder().encodeToString(Arrays.copyOf(buffer, bytesRead));
                    client.inputAudioBufferAppend(base64Data);
                }
                client.inputAudioBufferComplete();
            } catch (IOException e) {
                e.printStackTrace();
            }

            TimeUnit.SECONDS.sleep(100);
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
