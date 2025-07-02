package com.coze.openapi.service.auth;

import static com.coze.openapi.service.config.Consts.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;

import com.coze.openapi.api.CozeAuthAPI;
import com.coze.openapi.client.auth.GetAccessTokenReq;
import com.coze.openapi.client.auth.GrantType;
import com.coze.openapi.client.auth.OAuthToken;
import com.coze.openapi.client.common.BaseResp;
import com.coze.openapi.client.exception.CozeAuthException;
import com.coze.openapi.client.exception.CozeError;
import com.coze.openapi.service.utils.UserAgentInterceptor;
import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.reactivex.Single;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public abstract class OAuthClient {
  private static final String AuthorizeHeader = "Authorization";
  private static final ObjectMapper mapper = Utils.getMapper();

  protected final String clientSecret;
  protected final String clientID;
  protected final String baseURL;
  protected final String wwwURL;
  protected final CozeAuthAPI api;
  protected final ExecutorService executorService;
  protected final String hostName;

  protected OAuthClient(OAuthBuilder<?> builder) {
    builder.init();
    this.clientSecret = builder.clientSecret;
    this.clientID = builder.clientID;
    this.baseURL = builder.baseURL;
    if (builder.wwwURL != null) {
      this.wwwURL = builder.wwwURL;
    } else {
      this.wwwURL = builder.baseURL.replace("api.", "www.");
    }
    if (this.baseURL != null && !this.baseURL.isEmpty()) {
      try {
        java.net.URL url = new java.net.URL(this.baseURL);
        this.hostName = url.getHost();
      } catch (Exception e) {
        throw new RuntimeException("Invalid base URL: " + this.baseURL, e);
      }
    } else {
      throw new RuntimeException("Base URL is required");
    }

    Retrofit retrofit = defaultRetrofit(builder.client, mapper, getBaseURL());

    this.api = retrofit.create(CozeAuthAPI.class);
    this.executorService = builder.client.dispatcher().executorService();
  }

  protected String getOAuthURL(String redirectURI, String state) {
    return this._getOAuthURL(redirectURI, state, null, null, null);
  }

  protected String getOAuthURL(String redirectURI, String state, String workspace) {
    return this._getOAuthURL(redirectURI, state, null, null, workspace);
  }

  protected String getOAuthURL(
      String redirectURI, String state, String codeChallenge, String codeChallengeMethod) {
    return this._getOAuthURL(redirectURI, state, codeChallenge, codeChallengeMethod, null);
  }

  protected String getOAuthURL(
      String redirectURI,
      String state,
      String codeChallenge,
      String codeChallengeMethod,
      String workspaceID) {
    return this._getOAuthURL(redirectURI, state, codeChallenge, codeChallengeMethod, workspaceID);
  }

  private String _getOAuthURL(
      String redirectUri,
      String state,
      String codeChallenge,
      String codeChallengeMethod,
      String workspaceID) {

    Map<String, String> params = new HashMap<>();
    params.put("response_type", "code");
    if (this.clientID != null) {
      params.put("client_id", this.clientID);
    }
    if (redirectUri != null) {
      params.put("redirect_uri", redirectUri);
    }
    if (state != null) {
      params.put("state", state);
    }
    if (codeChallenge != null) {
      params.put("code_challenge", codeChallenge);
    }
    if (codeChallengeMethod != null) {
      params.put("code_challenge_method", codeChallengeMethod);
    }

    String uri = wwwURL + "/api/permission/oauth2/authorize";
    if (workspaceID != null) {
      uri = wwwURL + String.format("/api/permission/oauth2/workspace_id/%s/authorize", workspaceID);
    }

    String queryString =
        params.entrySet().stream()
            .map(
                entry -> {
                  try {
                    return URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString())
                        + "="
                        + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString());
                  } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                  }
                })
            .collect(Collectors.joining("&"));

    return uri + "?" + queryString;
  }

  protected Retrofit defaultRetrofit(OkHttpClient client, ObjectMapper mapper, String baseURL) {
    return new Retrofit.Builder()
        .baseUrl(baseURL)
        .client(client)
        .addConverterFactory(JacksonConverterFactory.create(mapper))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
  }

  private String getBaseURL() {
    return this.baseURL;
  }

  protected OAuthToken getAccessToken(
      GrantType type, String code, String clientSecret, String redirectURI) {
    return request(code, clientSecret, type, null, redirectURI);
  }

  protected OAuthToken getAccessToken(GrantType type, String clientSecret, String redirectURI) {
    return request(null, clientSecret, type, null, redirectURI);
  }

  protected OAuthToken getAccessToken(String secret, GetAccessTokenReq req) {
    Map<String, String> headers = new HashMap<>();
    if (secret != null) {
      headers.put(AuthorizeHeader, String.format("Bearer %s", secret));
    }
    return execute(this.api.retrieve(headers, req));
  }

  protected OAuthToken getAccountAccessToken(
      String secret, String accountID, GetAccessTokenReq req) {
    Map<String, String> headers = new HashMap<>();
    if (secret != null) {
      headers.put(AuthorizeHeader, String.format("Bearer %s", secret));
    }
    return execute(this.api.account(headers, accountID, req));
  }

  protected OAuthToken getEnterpriseAccessToken(
      String secret, String enterpriseID, GetAccessTokenReq req) {
    Map<String, String> headers = new HashMap<>();
    if (secret != null) {
      headers.put(AuthorizeHeader, String.format("Bearer %s", secret));
    }
    return execute(this.api.enterprise(headers, enterpriseID, req));
  }

  protected OAuthToken refreshAccessToken(String refreshToken, String clientSecret) {
    return request(null, clientSecret, GrantType.REFRESH_TOKEN, refreshToken, null);
  }

  protected OAuthToken refreshAccessToken(String refreshToken) {
    return request(null, null, GrantType.REFRESH_TOKEN, refreshToken, null);
  }

  public void shutdownExecutor() {
    Objects.requireNonNull(
        this.executorService, "executorService must be set in order to shut down");
    this.executorService.shutdown();
  }

  private OAuthToken request(
      String code, String secret, GrantType grantType, String refreshToken, String redirectURI) {
    GetAccessTokenReq.GetAccessTokenReqBuilder<?, ?> builder = GetAccessTokenReq.builder();
    builder
        .clientID(this.clientID)
        .grantType(grantType.getValue())
        .code(code)
        .refreshToken(refreshToken)
        .redirectUri(redirectURI);
    GetAccessTokenReq req = builder.build();

    Map<String, String> headers = new HashMap<>();
    if (secret != null) {
      headers.put(AuthorizeHeader, String.format("Bearer %s", secret));
    }
    return execute(this.api.retrieve(headers, req));
  }

  protected static <T> T execute(Single<Response<T>> apiCall) {
    try {
      Response<T> response = apiCall.blockingGet();
      if (!response.isSuccessful()) {
        try (ResponseBody errorBody = response.errorBody()) {
          if (errorBody == null) {
            throw new HttpException(response);
          }
          String logID = Utils.getLogID(response);
          String errStr = errorBody.string();
          CozeError error = mapper.readValue(errStr, CozeError.class);
          throw new CozeAuthException(error, new HttpException(response), response.code(), logID);
        } catch (IOException ex) {
          throw new HttpException(response);
        }
      }
      T body = response.body();
      if (body instanceof BaseResp) {
        ((BaseResp) body).setLogID(Utils.getLogID(response));
      }
      return body;
    } catch (HttpException e) {
      try (ResponseBody errorBody = e.response().errorBody()) {
        if (errorBody == null) {
          throw e;
        }
        String logID = Utils.getLogID(e.response());

        String errStr = e.response().errorBody().string();

        CozeError error = mapper.readValue(errStr, CozeError.class);
        throw new CozeAuthException(error, e, e.code(), logID);
      } catch (IOException ex) {
        // couldn't parse OpenAI error
        throw e;
      }
    }
  }

  public abstract OAuthToken refreshToken(String refreshToken);

  public abstract static class OAuthBuilder<T extends OAuthBuilder<T>> {
    protected String clientID;
    protected String clientSecret;
    protected String baseURL;
    protected String wwwURL;
    protected int readTimeout;
    protected int connectTimeout;
    protected OkHttpClient client;
    protected Logger logger;

    @SuppressWarnings("unchecked")
    protected T self() {
      return (T) this;
    }

    public T clientID(String clientID) {
      this.clientID = clientID;
      return self();
    }

    public T clientSecret(String clientSecret) {
      this.clientSecret = clientSecret;
      return self();
    }

    public T baseURL(String baseURL) {
      this.baseURL = baseURL;
      return self();
    }

    public T wwwURL(String wwwURL) {
      this.wwwURL = wwwURL;
      return self();
    }

    public T readTimeout(int readTimeout) {
      this.readTimeout = readTimeout;
      return self();
    }

    public T connectTimeout(int connectTimeout) {
      this.connectTimeout = connectTimeout;
      return self();
    }

    public T client(OkHttpClient client) {
      this.client = client;
      return self();
    }

    public T logger(Logger logger) {
      this.logger = logger;
      return self();
    }

    public abstract OAuthClient build() throws Exception;

    protected T init() {
      if (this.logger != null) {
        AuthLogFactory.setLogger(this.logger);
      }
      if (this.baseURL == null || this.baseURL.isEmpty()) {
        this.baseURL = COZE_COM_BASE_URL;
      }

      if (this.readTimeout == 0) {
        this.readTimeout = 5000;
      }

      if (this.connectTimeout == 0) {
        this.connectTimeout = 5000;
      }

      if (this.client == null) {
        this.client =
            defaultClient(
                Duration.ofMillis(this.readTimeout), Duration.ofMillis(this.connectTimeout));
      } else {
        OkHttpClient.Builder builder = client.newBuilder();
        builder.addInterceptor(new UserAgentInterceptor());

        this.client = builder.build();
      }
      return this.self();
    }

    protected OkHttpClient defaultClient(Duration readTimeout, Duration connectTimeout) {
      return new OkHttpClient.Builder()
          .connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES))
          .readTimeout(readTimeout.toMillis(), TimeUnit.MILLISECONDS)
          .connectTimeout(connectTimeout.toMillis(), TimeUnit.MILLISECONDS)
          .addInterceptor(new UserAgentInterceptor())
          .build();
    }
  }
}
