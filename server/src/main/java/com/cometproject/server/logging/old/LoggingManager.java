//package com.cometproject.server.logging.old;

//
//import com.cometproject.server.boot.Comet;
//
//import java.util.concurrent.ScheduledFuture;
//import java.util.concurrent.TimeUnit;


//
/**
 * Manages logging runtime state for the logging subsystem.
 */
//public class LoggingManager {
//    private final int FLUSH_SECONDS = 0;//Integer.parseInt(Configuration.currentConfig().get("comet.game.logging.flush.seconds"));
//    private final String TOKEN = "";//Configuration.currentConfig().get("comet.game.logging.token");
//
//    private final LoggingQueue queue;
//    private ScheduledFuture process;
//
//    private volatile boolean active = false;
//
/**
 * Creates a logging manager instance for the Snow War game subsystem.
 */
//    public LoggingManager() {
//        this.queue = new LoggingQueue(this.TOKEN);
//        //this.start();
//    }
//
/**
 * Executes start for this Snow War game contract.
 */
//    public void start() {
//        if (this.active) { return; }
//        this.active = true;
//        this.process = Comet.getServer().getThreadManagement().executePeriodic(this.queue, FLUSH_SECONDS, FLUSH_SECONDS, TimeUnit.SECONDS);
//    }
//
/**
 * Executes stop for this Snow War game contract.
 */
//    public void stop() {
//        if (!this.active) { return; }
//        this.active = false;
//        this.process.cancel(false);
//    }
//
/**
 * Executes queue for this Snow War game contract.
 *
 * @param entry Entry supplied by the caller.
 */
//    public void queue(AbstractLogEntry entry) {
//        this.queue.addEntry(entry);
//    }
//}
