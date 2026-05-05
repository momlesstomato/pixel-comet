package com.cometproject.server.api;

import io.javalin.http.Context;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Provides consistent management API response envelopes.
 */
public final class ApiResponseUtils {
    private ApiResponseUtils() {
    }

    /**
     * Writes a success response without a payload.
     *
     * @param context The active request context.
     */
    public static void success(final Context context) {
        success(context, null);
    }

    /**
     * Writes a success response with a payload under the {@code data} field.
     *
     * @param context The active request context.
     * @param data The payload to serialize.
     */
    public static void success(final Context context, final Object data) {
        final Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", true);

        if (data != null) {
            response.put("data", data);
        }

        context.status(200).json(response);
    }

    /**
     * Writes a structured error response.
     *
     * @param context The active request context.
     * @param statusCode The HTTP status code.
     * @param errorCode The machine-readable error code.
     * @param errorMessage The human-readable error message.
     */
    public static void error(final Context context, final int statusCode, final String errorCode, final String errorMessage) {
        final Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", false);
        response.put("error_code", errorCode);
        response.put("error_message", errorMessage);
        context.status(statusCode).json(response);
    }
}