package com.cometproject.server.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.javalin.http.Context;
import org.apache.commons.lang3.StringUtils;

/**
 * Provides helpers for reading mixed legacy and JSON-based management API inputs.
 */
public final class ApiRequestUtils {
    private ApiRequestUtils() {
    }

    /**
     * Returns the first non-blank value among the provided candidates.
     *
     * @param values Candidate values in priority order.
     * @return The first non-blank value, or null when none are present.
     */
    public static String firstNonBlank(final String... values) {
        for (String value : values) {
            if (StringUtils.isNotBlank(value)) {
                return value;
            }
        }

        return null;
    }

    /**
     * Resolves an integer path parameter.
     *
     * @param context The active request context.
     * @param name The path parameter name.
     * @return The parsed integer, or null when the value is absent or invalid.
     */
    public static Integer pathInt(final Context context, final String name) {
        final String raw = context.pathParamMap().get(name);

        if (!StringUtils.isNumeric(raw)) {
            return null;
        }

        return Integer.parseInt(raw);
    }

    /**
     * Reads a string field from the JSON request body.
     *
     * @param context The active request context.
     * @param key The JSON property name.
     * @return The property value, or null when not present.
     */
    public static String bodyField(final Context context, final String key) {
        final String body = context.body();

        if (StringUtils.isBlank(body)) {
            return null;
        }

        try {
            final JsonElement parsed = JsonParser.parseString(body);

            if (!parsed.isJsonObject()) {
                return null;
            }

            final JsonObject json = parsed.getAsJsonObject();

            if (!json.has(key) || json.get(key).isJsonNull()) {
                return null;
            }

            return json.get(key).getAsString();
        } catch (Exception ignored) {
            return null;
        }
    }
}