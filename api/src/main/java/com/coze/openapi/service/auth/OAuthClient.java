package com.coze.openapi.service.auth;

import com.coze.openapi.api.CozeAuthAPI;
import com.coze.openapi.client.auth.GetAccessTokenReq;
import com.coze.openapi.client.auth.OAuthToken;
import com.coze.openapi.client.auth.GrantType;
import com.coze.openapi.client.exception.CozeError;
import com.coze.openapi.client.exception.CozeAuthException;
import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Single;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

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
import static com.coze.openapi.service.config.Consts.*;

public abstract class OAuthClient {
    private final String AuthorizeHeader ="Authorization";
    private static final String logHeader = "x-tt-logid";
    protected final String clientSecret;
    protected final String clientID;
    protected final String baseURL;
    protected final CozeAuthAPI api;
    protected final ExecutorService executorService;
    private static final ObjectMapper mapper = Utils.defaultObjectMapper();

    protected OAuthClient(String clientID, String clientSecret) {
        this.clientSecret = clientSecret;
        this.clientID = clientID;
        this.baseURL = COZE_COM_BASE_URL;

        ObjectMapper mapper = Utils.defaultObjectMapper();
        OkHttpClient client = defaultClient(Duration.ofMillis(3000));
        Retrofit retrofit = defaultRetrofit(client, mapper, getBaseURL());

        this.api = retrofit.create(CozeAuthAPI.class);
        this.executorService = client.dispatcher().executorService();
    }


    protected OAuthClient(String clientID, String clientSecret, String baseURL) {
        this.clientSecret = clientSecret;
        this.clientID = clientID;
        this.baseURL = baseURL;

        ObjectMapper mapper = Utils.defaultObjectMapper();
        OkHttpClient client = defaultClient(Duration.ofMillis(3000));
        Retrofit retrofit = defaultRetrofit(client, mapper, getBaseURL());

        this.api = retrofit.create(CozeAuthAPI.class);
        this.executorService = client.dispatcher().executorService();
    }

    protected String getOAuthURL(@NotNull String redirectURI, String state) {
        return this._getOAuthURL(redirectURI, state, null, null, null);
    }

    protected String getOAuthURL(@NotNull String redirectURI, String state, @NotNull String codeChallenge) {
        return this._getOAuthURL(redirectURI, state, codeChallenge, null, null);
    }

    protected String getOAuthURL(@NotNull String redirectURI, String state, @NotNull String codeChallenge, @NotNull String codeChallengeMethod) {
        return this._getOAuthURL(redirectURI, state, codeChallenge, codeChallengeMethod, null);
    }

    protected String getOAuthURL(@NotNull String redirectURI, String state, @NotNull String codeChallenge, @NotNull String codeChallengeMethod, @NotNull String workspaceID) {
        return this._getOAuthURL(redirectURI, state, codeChallenge, codeChallengeMethod, workspaceID);
    }

    private String _getOAuthURL(
            String redirectUri,
            String state,
            String codeChallenge,
            String codeChallengeMethod,
            String workspaceID){

        Map<String, String> params = new HashMap<>();
        params.put("response_type", "code");
        if (this.clientID!= null){
            params.put("client_id", this.clientID);
        }
        if (redirectUri != null) {
            params.put("redirect_uri", redirectUri);
        }
        if (state != null){
            params.put("state", state);
        }
        if (codeChallenge != null) {
            params.put("code_challenge", codeChallenge);
        }
        if (codeChallengeMethod != null) {
            params.put("code_challenge_method", codeChallengeMethod);
        }

        String uri = baseURL + "/api/permission/oauth2/authorize";
        if (workspaceID != null) {
            uri = baseURL + String.format("/api/permission/oauth2/workspace_id/%s/authorize", workspaceID);
        }

        String queryString = params.entrySet().stream()
                .map(entry -> {
                    try {
                        return URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()) + "=" +
                                URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString());
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.joining("&"));

        return uri + "?" + queryString;
    }

    protected OkHttpClient defaultClient(Duration timeout) {
        return new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS))
                .readTimeout(timeout.toMillis(), TimeUnit.MILLISECONDS)
                .build();
    }

    protected Retrofit defaultRetrofit(OkHttpClient client, ObjectMapper mapper, String baseURL) {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private String getBaseURL(){
        return "https://api.coze.cn";
    }

    protected OAuthToken getAccessToken(GrantType type, String code, String clientSecret, String redirectURI) {
        return request(code, clientSecret, type, null, redirectURI);
    }

    protected OAuthToken getAccessToken(GrantType type, String clientSecret, String redirectURI) {
        return request(null, clientSecret, type, null, redirectURI);
    }

    protected OAuthToken getAccessToken(String secret, GetAccessTokenReq req) {
        Map<String, String> headers = new HashMap<>();
        if (secret != null){
            headers.put(AuthorizeHeader, String.format("Bearer %s", secret));
        }
        return execute(this.api.OauthAccessToken(headers, req));
    }

    protected OAuthToken refreshAccessToken(String refreshToken, String clientSecret) {
        return request(null, clientSecret, GrantType.RefreshToken, refreshToken, null);
    }

    protected OAuthToken refreshAccessToken(String refreshToken) {
        return request(null, null, GrantType.RefreshToken, refreshToken, null);
    }

    public void shutdownExecutor() {
        Objects.requireNonNull(this.executorService, "executorService must be set in order to shut down");
        this.executorService.shutdown();
    }


    private OAuthToken request(String code, String secret, GrantType grantType, String refreshToken, String redirectURI){
        GetAccessTokenReq.GetAccessTokenReqBuilder builder = GetAccessTokenReq.builder();
        builder.clientID(this.clientID).
                grantType(grantType.getValue()).
                code(code).
                refreshToken(refreshToken).
                redirectUri(redirectURI);
        GetAccessTokenReq req = builder.build();

        Map<String, String> headers = new HashMap<>();
        if (secret != null){
            headers.put(AuthorizeHeader, String.format("Bearer %s", secret));
        }
        return execute(this.api.OauthAccessToken(headers, req));
    }

    protected static <T> T execute(Single<T> apiCall) {
        try {
            return apiCall.blockingGet();
        } catch (HttpException e) {
            try (ResponseBody errorBody = e.response().errorBody()){
                if (errorBody == null) {
                    throw e;
                }
                String logID = e.response().raw().headers().get(logHeader);

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

}
