package com.cometproject.api.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Describes JSON util behavior for the Comet subsystem.
 */
public class JsonUtil {
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    /**
     * Returns the instance for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public static Gson getInstance() {
        return gson;
    }
}
