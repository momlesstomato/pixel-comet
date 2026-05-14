//package com.cometproject.server.logging.appenders;

//
//import com.cometproject.server.boot.Comet;
//import com.cometproject.server.network.monitor.MonitorClientHandler;
//import com.cometproject.server.network.monitor.MonitorMessageLibrary;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import org.apache.log4j.AppenderSkeleton;
//import org.apache.log4j.spi.LoggingEvent;


//
/**
 * Describes monitor appender behavior for the logging subsystem.
 */
//public class MonitorAppender extends AppenderSkeleton {
//    private Gson gson = new Gson();
//
/**
 * Creates a monitor appender instance for the Snow War game subsystem.
 */
//    public MonitorAppender() {
//
//    }
//
//    @Override
/**
 * Executes append for this Snow War game contract.
 *
 * @param loggingEvent Logging event supplied by the caller.
 */
//    protected void append(LoggingEvent loggingEvent) {
//        if (!MonitorClientHandler.isConnected) return;
//
//        JsonObject jsonObject = new JsonObject();
//        JsonObject logObject = new JsonObject();
//
//        logObject.add("name", gson.toJsonTree(loggingEvent.getLoggerName()));
//        logObject.add("message", gson.toJsonTree(loggingEvent.getRenderedMessage()));
//        logObject.add("time", gson.toJsonTree(loggingEvent.getTimeStamp()));
//        logObject.add("level", gson.toJsonTree(loggingEvent.getLevel().toString().toLowerCase()));
//        logObject.add("thread", gson.toJsonTree(loggingEvent.getThreadName()));
//
//        jsonObject.add("name", gson.toJsonTree("appendLog"));
//        jsonObject.add("message", logObject);
//
//        MonitorMessageLibrary.sendMessage(jsonObject.toString());
//    }
//
//    @Override
/**
 * Executes close for this Snow War game contract.
 */
//    public void close() {
//
//    }
//
//    @Override
/**
 * Executes requires layout for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
//    public boolean requiresLayout() {
//        return false;
//    }
//}
