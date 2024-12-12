package com.coze.openapi.service.service;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import com.coze.openapi.api.AudioRoomAPI;
import com.coze.openapi.api.AudioSpeechAPI;
import com.coze.openapi.api.AudioVoiceAPI;
import com.coze.openapi.api.BotAPI;
import com.coze.openapi.api.ChatAPI;
import com.coze.openapi.api.ChatMessageAPI;
import com.coze.openapi.api.ConversationAPI;
import com.coze.openapi.api.FileAPI;
import com.coze.openapi.api.DocumentAPI;
import com.coze.openapi.api.ConversationMessageAPI;
import com.coze.openapi.api.WorkflowRunAPI;
import com.coze.openapi.api.WorkspaceAPI;
import com.coze.openapi.service.service.bots.BotService;
import com.coze.openapi.service.service.chat.ChatService;
import com.coze.openapi.service.service.conversation.ConversationService;
import com.coze.openapi.service.service.file.FileService;
import com.coze.openapi.service.service.knowledge.KnowledgeService;
import com.coze.openapi.service.service.workflow.WorkflowService;
import com.coze.openapi.service.service.workspace.WorkspaceService;
import com.coze.openapi.service.utils.Utils;
import com.coze.openapi.service.service.audio.AudioService;
import com.coze.openapi.service.auth.Auth;
import com.coze.openapi.service.config.Consts;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class CozeAPI {
    private final String baseURL;
    private final ExecutorService executorService;
    private final Auth auth;
    private final WorkspaceService workspceApi;
    private final BotService botApi;
    private final ConversationService conversationApi;
    private final FileService fileApi;
    private final KnowledgeService knowledgeApi;
    private final WorkflowService workflowApi;
    private final ChatService chatApi;
    private final AudioService audioApi;

    public CozeAPI(Auth auth) {
        this.auth = auth;
        this.baseURL = Consts.COZE_CN_BASE_URL;
        ObjectMapper mapper = Utils.defaultObjectMapper();
        OkHttpClient client = defaultClient(Duration.ofMillis(300000));
        Retrofit retrofit = defaultRetrofit(client, mapper, this.baseURL);

        this.executorService = client.dispatcher().executorService();
        this.workspceApi = new WorkspaceService(retrofit.create(WorkspaceAPI.class));
        this.botApi = new BotService(retrofit.create(BotAPI.class));
        this.conversationApi = new ConversationService(retrofit.create(ConversationAPI.class), retrofit.create(ConversationMessageAPI.class));
        this.fileApi = new FileService(retrofit.create(FileAPI.class));
        this.knowledgeApi = new KnowledgeService(retrofit.create(DocumentAPI.class));
        this.workflowApi = new WorkflowService(retrofit.create(WorkflowRunAPI.class));
        this.chatApi = new ChatService(retrofit.create(ChatAPI.class), retrofit.create(ChatMessageAPI.class));
        this.audioApi = new AudioService(retrofit.create(AudioVoiceAPI.class), retrofit.create(AudioRoomAPI.class), retrofit.create(AudioSpeechAPI.class));
    }

   
    private OkHttpClient defaultClient(Duration timeout) {
        return new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS))
                .readTimeout(timeout.toMillis(), TimeUnit.MILLISECONDS)
                // .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new AuthenticationInterceptor(this.auth)) // 添加拦截器，在请求头中增加 token
                .build();
    }

    private Retrofit defaultRetrofit(OkHttpClient client, ObjectMapper mapper, String baseURL) {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(ApiResponseCallAdapterFactory.create()) 
                .build();
    }

    public WorkspaceService workspaces() {
        return this.workspceApi;
    }

    public BotService bots() {
        return this.botApi;
    }   

    public ConversationService conversations() {
        return this.conversationApi;
    }   

    public FileService files() {
        return this.fileApi;
    }

    public KnowledgeService knowledge() {
        return this.knowledgeApi;
    }

    public WorkflowService workflows() {
        return this.workflowApi;
    }

    public ChatService chats() {
        return this.chatApi;
    }

    public AudioService audio() {
        return this.audioApi;
    }

    public void shutdownExecutor() {
        Objects.requireNonNull(this.executorService, "executorService must be set in order to shut down");
        this.executorService.shutdown();
    }
}
