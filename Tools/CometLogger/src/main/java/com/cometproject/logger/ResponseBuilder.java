package com.cometproject.logger;

import com.cometproject.api.networking.messages.IMessageComposer;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Describes response builder behavior for the tooling subsystem.
 */
public class ResponseBuilder {
    private final Map<String, Object> map = new LinkedHashMap<>();

    /**
     * Executes builder for this tooling contract.
     *
     * @return Result produced by the operation.
     */
    public static ResponseBuilder builder() {
        return new ResponseBuilder();
    }

    /**
     * Executes add for this tooling contract.
     *
     * @param key Key supplied by the caller.
     * @param val Val supplied by the caller.
     * @return Result produced by the mutation.
     */
    public ResponseBuilder add(String key, Object val) {
        this.map.put(key, val);
        return this;
    }

    /**
     * Executes get for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<String, Object> get() {
        return this.map;
    }
}
