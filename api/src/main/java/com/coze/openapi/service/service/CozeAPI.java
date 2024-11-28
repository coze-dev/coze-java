package com.coze.openapi.service.service;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import com.coze.openapi.api.*;
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
    private final WorkspaceService workspaceAPI;
    private final BotService botAPI;
    private final ConversationService conversationAPI;
    private final FileService fileAPI;
    private final KnowledgeService knowledgeAPI;
    private final WorkflowService workflowAPI;
    private final ChatService chatAPI;
    private final AudioService audioAPI;

    public CozeAPI(Auth auth) {
        this.auth = auth;
        this.baseURL = Consts.COZE_COM_BASE_URL;
        ObjectMapper mapper = Utils.defaultObjectMapper();
        OkHttpClient client = defaultClient(Duration.ofMillis(300000));
        Retrofit retrofit = defaultRetrofit(client, mapper, this.baseURL);

        this.executorService = client.dispatcher().executorService();
        this.workspaceAPI = new WorkspaceService(retrofit.create(WorkspaceAPI.class));
        this.botAPI = new BotService(retrofit.create(BotAPI.class));
        this.conversationAPI = new ConversationService(retrofit.create(ConversationAPI.class), retrofit.create(ConversationMessageAPI.class));
        this.fileAPI = new FileService(retrofit.create(FileAPI.class));
        this.knowledgeAPI = new KnowledgeService(retrofit.create(DocumentAPI.class));
        this.workflowAPI = new WorkflowService(retrofit.create(WorkflowRunAPI.class), retrofit.create(WorkflowRunHistoryAPI.class));
        this.chatAPI = new ChatService(retrofit.create(ChatAPI.class), retrofit.create(ChatMessageAPI.class));
        this.audioAPI = new AudioService(retrofit.create(AudioVoiceAPI.class), retrofit.create(AudioRoomAPI.class), retrofit.create(AudioSpeechAPI.class));
    }

    public CozeAPI(Auth auth, String baseURL) {
        this.auth = auth;
        this.baseURL = baseURL;
        ObjectMapper mapper = Utils.defaultObjectMapper();
        OkHttpClient client = defaultClient(Duration.ofMillis(300000));
        Retrofit retrofit = defaultRetrofit(client, mapper, this.baseURL);

        this.executorService = client.dispatcher().executorService();
        this.workspaceAPI = new WorkspaceService(retrofit.create(WorkspaceAPI.class));
        this.botAPI = new BotService(retrofit.create(BotAPI.class));
        this.conversationAPI = new ConversationService(retrofit.create(ConversationAPI.class), retrofit.create(ConversationMessageAPI.class));
        this.fileAPI = new FileService(retrofit.create(FileAPI.class));
        this.knowledgeAPI = new KnowledgeService(retrofit.create(DocumentAPI.class));
        this.workflowAPI = new WorkflowService(retrofit.create(WorkflowRunAPI.class), retrofit.create(WorkflowRunHistoryAPI.class));
        this.chatAPI = new ChatService(retrofit.create(ChatAPI.class), retrofit.create(ChatMessageAPI.class));
        this.audioAPI = new AudioService(retrofit.create(AudioVoiceAPI.class), retrofit.create(AudioRoomAPI.class), retrofit.create(AudioSpeechAPI.class));
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
                .addCallAdapterFactory(APIResponseCallAdapterFactory.create()) 
                .build();
    }

    public WorkspaceService workspaces() {
        return this.workspaceAPI;
    }

    public BotService bots() {
        return this.botAPI;
    }   

    public ConversationService conversations() {
        return this.conversationAPI;
    }   

    public FileService files() {
        return this.fileAPI;
    }

    public KnowledgeService knowledge() {
        return this.knowledgeAPI;
    }

    public WorkflowService workflows() {
        return this.workflowAPI;
    }

    public ChatService chat() {
        return this.chatAPI;
    }

    public AudioService audio() {
        return this.audioAPI;
    }

    public void shutdownExecutor() {
        Objects.requireNonNull(this.executorService, "executorService must be set in order to shut down");
        this.executorService.shutdown();
    }
}
