package com.coze.openapi.service.service.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;

import com.coze.openapi.client.common.BaseResponse;
import com.coze.openapi.client.exception.CozeApiException;
import com.coze.openapi.client.exception.CozeError;
import com.coze.openapi.service.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.reactivex.FlowableEmitter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class AbstractEventCallback<T> implements Callback<ResponseBody> {
  private static final ObjectMapper mapper = Utils.getMapper();
  private static final Logger logger = CozeLoggerFactory.getLogger();

  private final ExecutorService backgroundExecutor;
  protected FlowableEmitter<T> emitter;

  public AbstractEventCallback(FlowableEmitter<T> emitter) {
    this.emitter = emitter;
    this.backgroundExecutor = Executors.newSingleThreadExecutor();

    emitter.setCancellable(backgroundExecutor::shutdownNow);
  }

  @Override
  public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
    // 将整个处理过程移到后台线程
    backgroundExecutor.execute(
        () -> {
          BufferedReader reader = null;

          try {
            String logID = Utils.getLogID(response);
            if (!response.isSuccessful()) {
              logger.warn("HTTP error: " + response.code() + " " + response.message());
              String errStr = response.errorBody().string();
              CozeError error = mapper.readValue(errStr, CozeError.class);
              CozeApiException exception =
                  new CozeApiException(
                      Integer.valueOf(response.code()), error.getErrorMessage(), logID);
              emitter.onError(exception);
              return;
            }

            // 检查 response body 是否为 BaseResponse 格式
            String contentType = response.headers().get("Content-Type");
            if (contentType != null && contentType.contains("application/json")) {
              String respStr = response.body().string();
              try {
                BaseResponse<?> baseResp = mapper.readValue(respStr, BaseResponse.class);
                if (baseResp.getCode() != 0) {
                  logger.warn("API error: {} {}", baseResp.getCode(), baseResp.getMsg());
                  CozeApiException exception =
                      new CozeApiException(baseResp.getCode(), baseResp.getMsg(), logID);
                  emitter.onError(exception);
                  return;
                }
                emitter.onComplete();
                return;
              } catch (Exception e) {
                logger.error("Failed to parse JSON response: {}", respStr, e);
                CozeApiException exception =
                    new CozeApiException(
                        -1, "Failed to parse JSON response: " + e.getMessage(), logID);
                emitter.onError(exception);
                return;
              }
            }

            InputStream in = response.body().byteStream();
            reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line;

            while (!emitter.isCancelled() && (line = reader.readLine()) != null) {
              if (processLine(line, reader, logID)) {
                break;
              }
            }

            emitter.onComplete();

          } catch (Throwable t) {
            onFailure(call, t);
          } finally {
            if (reader != null) {
              try {
                reader.close();
              } catch (IOException e) {
                // do nothing
              }
              if (response.body() != null) {
                response.body().close();
              }
            }
          }
        });
  }

  protected abstract boolean processLine(String line, BufferedReader reader, String logID)
      throws IOException;

  @Override
  public void onFailure(Call<ResponseBody> call, Throwable t) {
    try {
      emitter.onError(t);
    } finally {
      backgroundExecutor.shutdown();
    }
  }
}
