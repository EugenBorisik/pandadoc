package org.example.pandadoc.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

public final class HttpClient {

  private static HttpClient instance;
  private final OkHttpClient okHttpClient;

  private HttpClient() {
    okHttpClient = new OkHttpClient().newBuilder()
        .build();
  }

  public static HttpClient getInstance() {
    if (Objects.isNull(instance)) {
      instance = new HttpClient();
    }

    return instance;
  }

  public OkHttpClient getClient() {
    return okHttpClient;
  }

  public Response execute(Request request) {
    try {
      return getClient().newCall(request).execute();
    } catch (IOException e) {
      throw new RuntimeException("Can't execute http request", e);
    }
  }
}
