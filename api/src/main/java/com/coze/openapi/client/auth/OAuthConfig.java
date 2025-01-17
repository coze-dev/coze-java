package com.coze.openapi.client.auth;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuthConfig {
  @JsonProperty("client_type")
  private String clientType;

  @JsonProperty("client_id")
  private String clientId;

  @JsonProperty("client_secret")
  private String clientSecret;

  @JsonProperty("private_key")
  private String privateKey;

  @JsonProperty("public_key_id")
  private String publicKeyId;

  @JsonProperty("coze_api_base")
  private String cozeApiBase;

  @JsonProperty("coze_www_base")
  private String cozeWwwBase;

  public static OAuthConfig load(String configFilePath) {
    return doLoad(configFilePath);
  }

  private static OAuthConfig doLoad(String configFilePath) {
    try (InputStream inputStream = new FileInputStream(configFilePath)) {
      ObjectMapper mapper = new ObjectMapper();
      mapper.findAndRegisterModules();

      return mapper.readValue(inputStream, OAuthConfig.class);

    } catch (FileNotFoundException e) {
      throw new RuntimeException("Config file not found: " + configFilePath, e);
    } catch (StreamReadException e) {
      throw new RuntimeException("JSON file format error: " + e.getMessage(), e);
    } catch (DatabindException e) {
      throw new RuntimeException("Failed to map config file to object: " + e.getMessage(), e);
    } catch (IOException e) {
      throw new RuntimeException("I/O error while reading config file: " + e.getMessage(), e);
    }
  }
}
