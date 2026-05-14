package com.cometproject.logger;

import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.logger.routes.Routes;
import com.cometproject.logger.tasks.CometThreadManagement;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.ResponseTransformer;


/**
 * Describes comet logger behavior for the tooling subsystem.
 */
public class CometLogger {
    public static JSONTransformer JSON_TRANSFORMER = new JSONTransformer();

    private static CometThreadManagement threadManagement;

    private static final class JSONTransformer implements ResponseTransformer {
        private final Gson gson = new GsonBuilder().create();

        /**
         * Executes render for this tooling contract.
         *
         * @param model Model supplied by the caller.
         * @return Result produced by the operation.
         * @throws Exception When the operation cannot complete.
         */
        @Override
        public String render(Object model) throws Exception {
            if (model instanceof ResponseBuilder) { // in case you forget to call .get() i'll handle it for you ;)
                ResponseBuilder rb = (ResponseBuilder) model;
                return gson.toJson(rb.get());
            }

            return gson.toJson(model);
        }
    }

    /**
     * Executes main for this tooling contract.
     *
     * @param args Args supplied by the caller.
     */
    public static void main(String[] args) {
        threadManagement = new CometThreadManagement();

        Routes.init();
    }

    /**
     * Returns the thread management for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    public static CometThreadManagement getThreadManagement() {
        return threadManagement;
    }
}
