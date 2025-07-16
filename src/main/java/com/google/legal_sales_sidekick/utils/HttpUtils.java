package com.google.legal_sales_sidekick.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Objects;

public class HttpUtils {

    private static final Gson gson = new Gson();

    public static Map<String, String> getPostResponse(String url, Object requestBody, Pair<String, String>... queryParams) throws IOException, InterruptedException {
        try (HttpClient client = HttpClient.newHttpClient()) {
            StringBuilder urlBuilder = new StringBuilder(url);
            if (Objects.nonNull(queryParams)) {
                urlBuilder.append("?");
                for (Map.Entry<String, String> queryParam : queryParams) {
                    urlBuilder.append(queryParam.getKey()).append("=").append(queryParam.getValue());
                    urlBuilder.append("&");
                }
                url = urlBuilder.toString();
            }
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(Objects.isNull(requestBody) ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(gson.toJson(requestBody)))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (Objects.isNull(response) || response.statusCode() != 200) {
                throw new RuntimeException("Error while getting post response");
            }
            Type type = new TypeToken<Map<String, String>>() {
            }.getType();
            return gson.fromJson(response.body(), type);
        }
    }
}
