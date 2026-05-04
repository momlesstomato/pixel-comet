package com.cometproject.server.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.cometproject.api.config.CometSettings;

public class ApiClient {
    private static ApiClient apiClient;
    private final HttpClient httpClient;
    private boolean isOffline = false;

    public ApiClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public static ApiClient getInstance() {
        if (apiClient == null)
            apiClient = new ApiClient();

        return apiClient;
    }

    public String saveThumbnail(final byte[] data, int roomId) {
        return savePhoto(data, roomId + "");
    }

    public String savePhoto(final byte[] data, String photoId) {
        try {
            final HttpRequest request = HttpRequest.newBuilder(URI.create(CometSettings.cameraUploadUrl.replace("%photoId%", photoId)))
                    .header("Content-Type", "application/octet-stream")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(data))
                    .build();

            return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)).body();
        } catch (Exception e) {
            this.isOffline = true;
            return "";
        }
    }
}
