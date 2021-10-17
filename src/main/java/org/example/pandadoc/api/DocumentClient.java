package org.example.pandadoc.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.time.StopWatch;
import org.example.pandadoc.config.ExecutionContext;
import org.example.pandadoc.model.api.CreateDocumentRequest;
import org.example.pandadoc.model.api.CreateDocumentResponse;
import org.example.pandadoc.model.api.DocumentStatusResponse;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DocumentClient {

  private static final ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  private static final String API_URL = ExecutionContext.get("api.url");
  private static final String AUTH_HEADER_VALUE = "API-Key " + ExecutionContext.get("api.key");

  public static CreateDocumentResponse create(File document, CreateDocumentRequest requestDto, int expectedCode) {
    RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
        .addFormDataPart("file", "panda.png", RequestBody.create(MediaType.parse("application/octet-stream"), document))
        .addFormDataPart("data", toJson(requestDto))
        .build();

    Headers.Builder headersBuilder = getHeadersBuilder();
    headersBuilder.add("Content-Type", "multipart/form-data");
    Request request = getRequest(Paths.get(API_URL, "documents"), "POST", body, headersBuilder);

    Response response = HttpClient.getInstance().execute(request);
    Assert.assertEquals(response.code(), expectedCode);

    return toDto(response.body(), CreateDocumentResponse.class);
  }

  public static void delete(String documentId, int expectedCode) {
    Request request = getRequest(Paths.get(API_URL, "documents", documentId), "DELETE", getHeadersBuilder());

    Response response = HttpClient.getInstance().execute(request);
    Assert.assertEquals(response.code(), expectedCode);
  }

  public static DocumentStatusResponse getStatus(String documentId, int expectedCode) {
    Request request = getRequest(Paths.get(API_URL, "documents", documentId), "GET", getHeadersBuilder());

    Response response = HttpClient.getInstance().execute(request);
    Assert.assertEquals(response.code(), expectedCode);

    return toDto(response.body(), DocumentStatusResponse.class);
  }

  public static void waitForDocumentToHaveStatus(String documentId, String status) {
    StopWatch stopWatch = StopWatch.createStarted();
    String currentStatus;
    do {
      try {
        //Need this sleep to avoid 429
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      currentStatus = getStatus(documentId, 200).getStatus();
    } while (!status.equals(currentStatus) || stopWatch.getTime() > 15000);
  }

  private static Request getRequest(Path url, String method, RequestBody body, Headers.Builder headersBuilder) {
    return new Request.Builder()
        .url(url.toString())
        .method(method, body)
        .headers(headersBuilder.build())
        .build();
  }

  private static Request getRequest(Path url, String method, Headers.Builder headersBuilder) {
    return getRequest(url, method, null, headersBuilder);
  }

  private static Headers.Builder getHeadersBuilder() {
    Headers.Builder headersBuilder = new Headers.Builder();
    headersBuilder.add("Authorization", AUTH_HEADER_VALUE);

    return headersBuilder;
  }

  private static <T> T toDto(ResponseBody body, Class<T> clazz) {
    try {
      return MAPPER.readValue(body.bytes(), clazz);
    } catch (IOException e) {
      throw new RuntimeException("Can't convert json into dto", e);
    }
  }

  private static String toJson(Object dto) {
    try {
      return MAPPER.writeValueAsString(dto);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Can't convert dto into json", e);
    }
  }
}
