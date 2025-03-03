package example.websocket.audio.speech;

import com.coze.openapi.client.websocket.event.downstream.*;
import com.coze.openapi.client.websocket.event.model.OutputAudio;
import com.coze.openapi.client.websocket.event.model.PCMConfig;
import com.coze.openapi.client.websocket.event.model.SpeechUpdateEventData;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;
import com.coze.openapi.service.service.websocket.audio.speech.WebsocketAudioSpeechCallbackHandler;
import com.coze.openapi.service.service.websocket.audio.speech.WebsocketAudioSpeechClient;
import com.coze.openapi.service.service.websocket.audio.speech.WebsocketAudioSpeechCreateReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import example.utils.ExampleUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;


/*
This example describes how to use the chat interface to initiate conversations,
poll the status of the conversation, and obtain the messages after the conversation is completed.
* */
public class WebsocketAudioSpeechExample {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Weather {
        @JsonProperty("weather")
        private String weather;
    }

    private static class CallbackHandler extends WebsocketAudioSpeechCallbackHandler {
        private final ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 10); // 分配 10MB 缓冲区

        public CallbackHandler() {
            super();
        }

        // 语音创建成功事件 (speech.created)
        @Override
        public void onSpeechCreated(WebsocketAudioSpeechClient client, SpeechCreatedEvent event) {
            System.out.println("==== Speech Created ====");
            System.out.println(event);
        }

        // 语音配置更新事件 (speech.update)
        @Override
        public void onSpeechUpdated(WebsocketAudioSpeechClient client, SpeechUpdatedEvent event) {
            System.out.println("==== Speech Updated ====");
            System.out.println(event);
        }

        // 语音数据更新事件 (speech.audio.update)
        @Override
        public void onSpeechAudioUpdate(
                WebsocketAudioSpeechClient client, SpeechAudioUpdateEvent event) {
            buffer.put(event.getDelta());
        }

        // 语音数据完成事件 (speech.audio.completed)
        @Override
        public void onSpeechAudioCompleted(
                WebsocketAudioSpeechClient client, SpeechAudioCompletedEvent event) {
            try {
                ExampleUtils.writePcmToWavFile(buffer.array(), "output_speech.wav");
                System.out.println("========= On Speech Audio Completed =========");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 文本缓冲区完成事件 (input_text_buffer.completed)
        @Override
        public void onInputTextBufferCompleted(
                WebsocketAudioSpeechClient client, InputTextBufferCompletedEvent event) {
            System.out.println("==== Input Text Buffer Completed ====");
            System.out.println(event);
        }

        @Override
        public void onError(WebsocketAudioSpeechClient client, ErrorEvent event) {
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

        WebsocketAudioSpeechClient client = null;
        try {
            client =
                    coze.websocket().audio().speech().create(new WebsocketAudioSpeechCreateReq(new CallbackHandler()));
            OutputAudio outputAudio = OutputAudio.builder()
                    .voiceId(voiceID)
                    .codec("pcm")
                    .speechRate(50)
                    .pcmConfig(PCMConfig.builder()
                            .sampleRate(24000)
                            .build())
                    .build();
            client.speechUpdate(new SpeechUpdateEventData(outputAudio));
            client.inputTextBufferAppend("hello world, nice to meet you!");
            client.inputTextBufferComplete();
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
