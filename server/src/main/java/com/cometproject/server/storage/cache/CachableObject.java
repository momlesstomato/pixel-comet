package com.cometproject.server.storage.cache;

import com.cometproject.api.utilities.JsonUtil;
import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Describes cachable object behavior for the storage subsystem.
 */
public abstract class CachableObject implements Serializable {
    /**
     * Executes to string for this storage contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public String toString() {
        final JsonObject jsonObject = this.toJson();

        if (jsonObject != null) {
            return JsonUtil.getInstance().toJson(jsonObject);
        }

        return JsonUtil.getInstance().toJson(this);
    }

    /**
     * Executes to JSON for this storage contract.
     *
     * @return Result produced by the operation.
     */
    public JsonObject toJson() {
        return null;
    }
}
