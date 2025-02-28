package com.coze.openapi.service.service;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import com.coze.openapi.api.*;
import com.coze.openapi.service.auth.Auth;
import com.coze.openapi.service.config.Consts;
import com.coze.openapi.service.service.audio.AudioService;
import com.coze.openapi.service.service.bots.BotService;
import com.coze.openapi.service.service.chat.ChatService;
import com.coze.openapi.service.service.common.CozeLoggerFactory;
import com.coze.openapi.service.service.conversation.ConversationService;
import com.coze.openapi.service.service.dataset.DatasetService;
import com.coze.openapi.service.service.file.FileService;
import com.coze.openapi.service.service.template.TemplateService;
import com.coze.openapi.service.service.websocket.WebsocketClient;
import com.coze.openapi.service.service.workflow.WorkflowService;
import com.coze.openapi.service.service.workspace.WorkspaceService;
import com.coze.openapi.service.utils.UserAgentInterceptor;
import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.lang.Strings;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class CozeAPI {
  private static final Logger logger = CozeLoggerFactory.getLogger();
  private final String baseURL;
  private final ExecutorService executorService;
  private final Auth auth;
  private final WorkspaceService workspaceAPI;
  private final BotService botAPI;
  private final ConversationService conversationAPI;
  private final FileService fileAPI;
  private final DatasetService datasetAPI;
  private final WorkflowService workflowAPI;
  private final ChatService chatAPI;
  private final AudioService audioAPI;
  private final TemplateService templateAPI;
  private final WebsocketClient websocket;

  private CozeAPI(
      String baseURL,
      ExecutorService executorService,
      Auth auth,
      WorkspaceService workspaceAPI,
      BotService botAPI,
      ConversationService conversationAPI,
      FileService fileAPI,
      DatasetService knowledgeAPI,
      WorkflowService workflowAPI,
      ChatService chatAPI,
      AudioService audioAPI,
      TemplateService templateAPI,
      WebsocketClient websocket) {
    this.baseURL = baseURL;
    this.executorService = executorService;
    this.auth = auth;
    this.workspaceAPI = workspaceAPI;
    this.botAPI = botAPI;
    this.conversationAPI = conversationAPI;
    this.fileAPI = fileAPI;
    this.datasetAPI = knowledgeAPI;
    this.workflowAPI = workflowAPI;
    this.chatAPI = chatAPI;
    this.audioAPI = audioAPI;
    this.templateAPI = templateAPI;
    this.websocket = websocket;
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

  public DatasetService datasets() {
    return this.datasetAPI;
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

  public TemplateService templates() {
    return this.templateAPI;
  }

  public WebsocketClient websocket() {
    return this.websocket;
  }

  public void shutdownExecutor() {
    Objects.requireNonNull(
        this.executorService, "executorService must be set in order to shut down");
    this.executorService.shutdown();
  }

  public static class Builder {
    private String baseURL = Consts.COZE_COM_BASE_URL;
    private Auth auth;
    private OkHttpClient client;
    private int readTimeout = 5000;
    private int connectTimeout = 5000;

    public Builder logger(Logger logger) {
      CozeLoggerFactory.setLogger(logger);
      return this;
    }

    public Builder baseURL(String url) {
      this.baseURL = url;
      return this;
    }

    public Builder auth(Auth auth) {
      this.auth = auth;
      return this;
    }

    public Builder client(OkHttpClient client) {
      this.client = client;
      return this;
    }

    public Builder readTimeout(int readTimeout) {
      this.readTimeout = readTimeout;
      return this;
    }

    public Builder connectTimeout(int connectTimeout) {
      this.connectTimeout = connectTimeout;
      return this;
    }

    public CozeAPI build() {
      if (this.auth == null) {
        throw new IllegalArgumentException("auth must be set");
      }
      if (this.client == null) {
        this.client =
            defaultClient(
                Duration.ofMillis(this.readTimeout), Duration.ofMillis(this.connectTimeout));
      } else {
        this.client = parseClient(this.client);
      }
      if (this.baseURL == null || this.baseURL.isEmpty()) {
        this.baseURL = Consts.COZE_COM_BASE_URL;
      }

      ObjectMapper mapper = Utils.getMapper();
      Retrofit retrofit = defaultRetrofit(client, mapper, this.baseURL);
      ExecutorService executorService = client.dispatcher().executorService();
      WorkspaceService workspaceAPI = new WorkspaceService(retrofit.create(WorkspaceAPI.class));
      BotService botAPI = new BotService(retrofit.create(BotAPI.class));
      ConversationService conversationAPI =
          new ConversationService(
              retrofit.create(ConversationAPI.class),
              retrofit.create(ConversationMessageAPI.class));
      FileService fileAPI = new FileService(retrofit.create(FileAPI.class));
      DatasetService knowledgeAPI =
          new DatasetService(
              retrofit.create(DatasetAPI.class),
              retrofit.create(DatasetDocumentAPI.class),
              retrofit.create(DatasetImageAPI.class));
      WorkflowService workflowAPI =
          new WorkflowService(
              retrofit.create(WorkflowRunAPI.class),
              retrofit.create(WorkflowRunHistoryAPI.class),
              retrofit.create(WorkflowChatAPI.class));
      ChatService chatAPI =
          new ChatService(retrofit.create(ChatAPI.class), retrofit.create(ChatMessageAPI.class));
      AudioService audioAPI =
          new AudioService(
              retrofit.create(AudioVoiceAPI.class),
              retrofit.create(AudioRoomAPI.class),
              retrofit.create(AudioSpeechAPI.class),
              retrofit.create(AudioTranscriptionAPI.class));
      TemplateService templateAPI = new TemplateService(retrofit.create(TemplateAPI.class));
      WebsocketClient websocket =
          new WebsocketClient(
              this.client, Strings.replace(this.baseURL, "https://api", "wss://ws"));
      return new CozeAPI(
          this.baseURL,
          executorService,
          this.auth,
          workspaceAPI,
          botAPI,
          conversationAPI,
          fileAPI,
          knowledgeAPI,
          workflowAPI,
          chatAPI,
          audioAPI,
          templateAPI,
          websocket);
    }

    // 确保加上了 Auth 拦截器
    private OkHttpClient parseClient(OkHttpClient client) {
      boolean hasAuthInterceptor = false;
      boolean hasTimeoutInterceptor = false;
      boolean hasUserAgentInterceptor = false;
      for (Interceptor interceptor : client.interceptors()) {
        if (interceptor instanceof AuthenticationInterceptor) {
          hasAuthInterceptor = true;
        }
        if (interceptor instanceof TimeoutInterceptor) {
          hasTimeoutInterceptor = true;
        }
        if (interceptor instanceof UserAgentInterceptor) {
          hasUserAgentInterceptor = true;
        }
      }
      if (hasAuthInterceptor && hasTimeoutInterceptor && hasUserAgentInterceptor) {
        return client;
      }
      OkHttpClient.Builder builder = new OkHttpClient.Builder(client);
      if (!hasAuthInterceptor) {
        builder.addInterceptor(new AuthenticationInterceptor(this.auth));
      }
      if (!hasTimeoutInterceptor) {
        builder.addInterceptor(new TimeoutInterceptor());
      }
      if (!hasUserAgentInterceptor) {
        builder.addInterceptor(new UserAgentInterceptor());
      }
      return builder.build();
    }

    private OkHttpClient defaultClient(Duration readTimeout, Duration connectTimeout) {
      return new OkHttpClient.Builder()
          .connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS))
          .readTimeout(readTimeout.toMillis(), TimeUnit.MILLISECONDS)
          .connectTimeout(connectTimeout.toMillis(), TimeUnit.MILLISECONDS)
          .addInterceptor(new AuthenticationInterceptor(this.auth)) // 添加拦截器，在请求头中增加 token
          .addInterceptor(new TimeoutInterceptor()) // 添加拦截器，设置超时时间
          .addInterceptor(new UserAgentInterceptor()) // 添加拦截器，设置 user-agent
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
  }
}
