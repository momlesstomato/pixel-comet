package com.cometproject.server.logging.sentry;

/**
 * Describes sentry dispatcher behavior for the logging subsystem.
 */
public class SentryDispatcher {
//    private static final String SENTRY_DSN = "https://2b04068799c2416a8f02d1bf6294b6a0:041cc030ac334e718fbae0f9c8dc5c8f@sentry.cometproject.com/1";
//    private static final Logger log = Logger.getLogger(SentryDispatcher.class.getName());
//
//    private Raven ravenInstance;
//
/**
 * Creates a sentry dispatcher instance for the Snow War game subsystem.
 */
//    public SentryDispatcher() {
//        try {
//            this.ravenInstance = RavenFactory.ravenInstance(new Dsn(SENTRY_DSN));
//        } catch(Exception e) {
//            log.debug("Failed to create Sentry instance! Exception thrown: " + e.getClass().getName());
//        }
//    }
//
/**
 * Executes dispatch exception for this Snow War game contract.
 *
 * @param context Context supplied by the caller.
 * @param exception Exception supplied by the caller.
 * @param level Level supplied by the caller.
 */
//    public void dispatchException(String context, Exception exception, Event.Level level) {
//        this.dispatchException("", context, exception, level, null);
//    }
//
/**
 * Executes dispatch exception for this Snow War game contract.
 *
 * @param errorCategory Error category supplied by the caller.
 * @param context Context supplied by the caller.
 * @param exception Exception supplied by the caller.
 * @param level Level supplied by the caller.
 * @param MapString Map string supplied by the caller.
 * @param extras Extras supplied by the caller.
 */
//    public void dispatchException(String errorCategory, String context, Exception exception, Event.Level level, Map<String, Object> extras) {
//        EventBuilder builder = new EventBuilder()
//                .addTag("type", "error")
//                .setMessage(context)
//                .setLevel(level)
//                .setLogger("ERROR")
//                .addSentryInterface(new ExceptionInterface(exception));
//
//        if(!errorCategory.isEmpty()) {
//            builder.addTag("error_category", errorCategory);
//        }
//
//        if(extras != null) {
//            for(Map.Entry<String, Object> entry : extras.entrySet()) {
//                builder.addExtra(entry.getKey(), entry.getValue());
//            }
//        }
//
//        this.dispatchManualEvent(builder, true);
//    }
//
/**
 * Executes dispatch manual event for this Snow War game contract.
 *
 * @param builder Builder supplied by the caller.
 * @param runBuilderHelpers Run builder helpers supplied by the caller.
 */
//    public void dispatchManualEvent(EventBuilder builder, boolean runBuilderHelpers) {
//        if(this.ravenInstance == null)
//            return;
//
//        builder.addTag("hotel_name", CometSettings.hotelName);
//        builder.addTag("hotel_url", CometSettings.hotelUrl);
//
//        // dispatch the event
//        if(runBuilderHelpers)
//            this.ravenInstance.runBuilderHelpers(builder);
//
//        this.ravenInstance.sendEvent(builder.build());
//    }
//
//    private static SentryDispatcher dispatcher;
//
/**
 * Returns the instance for this Snow War game contract.
 *
 * @return Value exposed by the contract.
 */
//    public static SentryDispatcher getInstance() {
//        if (dispatcher == null)
//            dispatcher = new SentryDispatcher();
//
//        return dispatcher;
//    }
}
