package com.cometproject.server.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.cometproject.api.config.CometSettings;

/**
 * Describes API client behavior for the HTTP API subsystem.
 */
public class ApiClient {
    private static ApiClient apiClient;
    private final HttpClient httpClient;
    private boolean isOffline = false;

    /**
     * Creates a API client instance for the HTTP API subsystem.
     */
    public ApiClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Returns the instance for this HTTP API contract.
     *
     * @return Value exposed by the contract.
     */
    public static ApiClient getInstance() {
        if (apiClient == null)
            apiClient = new ApiClient();

        return apiClient;
    }

    /**
     * Persists thumbnail for this HTTP API contract.
     *
     * @param data Data supplied by the caller.
     * @param roomId Room identifier used by the operation.
     * @return Result produced by the mutation.
     */
    public String saveThumbnail(final byte[] data, int roomId) {
        return savePhoto(data, roomId + "");
    }

    /**
     * Persists photo for this HTTP API contract.
     *
     * @param data Data supplied by the caller.
     * @param photoId Photo id supplied by the caller.
     * @return Result produced by the mutation.
     */
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
