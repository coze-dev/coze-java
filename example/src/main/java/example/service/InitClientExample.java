package example.service;

import org.slf4j.LoggerFactory;

import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.config.Consts;
import com.coze.openapi.service.service.CozeAPI;

import okhttp3.OkHttpClient;

public class InitClientExample {
    public static void main(String[] args) {
           // Get an access_token through personal access token or oauth.
        String token = System.getenv("COZE_API_TOKEN");
        TokenAuth authCli = new TokenAuth(token);

        /*
         * Build with custom okhttp client
         * 
         * Coze client built on okhttp, and supports passing a custom okhttp client. when initialzing Coze.
         * Even if you pass a custom okhttp client, the Coze client will still use the a default interceptor 
         * to pass the access token to the server.
        */
        CozeAPI coze = new CozeAPI.Builder()
                .baseURL(System.getenv("COZE_API_BASE"))
                .auth(authCli)
                .client(new OkHttpClient.Builder().build()) 
                .build();
        
        /* 
         * set custom timeout
         * 
         * You can set the read timeout, write timeout, and connection timeout for the Coze client.
         * You can directly set the timeout when initializing the Coze client, set it by your okhttp client
        */
        coze = new CozeAPI.Builder()
                .baseURL(System.getenv("COZE_API_BASE"))
                .auth(authCli)
                .readTimeout(10000)
                .connectTimeout(10000)
                .build();

        /*
         * set custom logger
         * 
         * Coze client built on retrofit, and uses slf4j as the logging framework.
         * You can set a custom logger when initializing the Coze client.
        */

        coze = new CozeAPI.Builder()
                .baseURL(System.getenv("COZE_API_BASE"))
                .auth(authCli)
                .readTimeout(10000)
                .connectTimeout(10000)
                .logger(LoggerFactory.getLogger(CozeAPI.class))
                .build();
        
        /*
         * set base url
         * 
         * Coze client will request www.coze.com by default.
         * If you want to request the cn environment, you can set the base url to https://www.coze.cn.
        */
        coze = new CozeAPI.Builder()
                .baseURL(Consts.COZE_CN_BASE_URL)
                .auth(authCli)
                .readTimeout(10000)
                .connectTimeout(10000)
                .build();
    }
    
}
