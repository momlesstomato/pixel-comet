package com.cometproject.server.boot;

import java.util.Locale;

import org.slf4j.Logger;

import com.cometproject.api.config.ConfigurationKeys;
import com.cometproject.api.config.logging.LoggingConfiguration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.Configurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.spi.ContextAwareBase;
import io.github.cdimascio.dotenv.Dotenv;
import net.logstash.logback.encoder.LogstashEncoder;

/**
 * Configures Logback before application loggers are created.
 *
 * <p>This configurator reads logging settings directly from the process environment or local
 * {@code .env} file so startup logging can be controlled before the runtime configuration chain is
 * initialized.
 */
public final class LoggingConfigurator extends ContextAwareBase implements Configurator {
    private static final String DEFAULT_CONSOLE_PATTERN = "%highlight(%-5level) %cyan(%-36logger{36}) - %msg%n";
    private static final String DEFAULT_LOG_LEVEL = "info";
    private static final String DEFAULT_LOG_FORMAT = "console";
    private static final String LOG_LEVEL_ENV_KEY = "LOG_LEVEL";
    private static final String LOG_FORMAT_ENV_KEY = "LOG_FORMAT";
    private static final String JSON_FORMAT = "json";

    /**
     * Applies the startup logging configuration.
     *
     * @param loggerContext The Logback logger context.
     * @return A status instructing Logback not to continue with other configurators.
     */
    @Override
    public ExecutionStatus configure(final LoggerContext loggerContext) {
        final Level rootLevel = this.resolveLogLevel();
        final String logFormat = this.resolveLogFormat();

        loggerContext.reset();

        final ConsoleAppender<ILoggingEvent> consoleAppender = this.createConsoleAppender(loggerContext, logFormat);
        final ch.qos.logback.classic.Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);

        rootLogger.setLevel(rootLevel);
        rootLogger.addAppender(consoleAppender);

        this.configureLogger(loggerContext, "io.netty", this.atLeast(rootLevel, Level.INFO));
        this.configureLogger(loggerContext, "com.zaxxer.hikari", this.atLeast(rootLevel, Level.ERROR));
        this.configureLogger(loggerContext, "io.javalin", this.atLeast(rootLevel, Level.WARN));
        this.configureLogger(loggerContext, "org.eclipse.jetty", this.atLeast(rootLevel, Level.WARN));
        this.configureLogger(loggerContext, "org.flywaydb.core.FlywayExecutor", this.atLeast(rootLevel, Level.WARN));
        this.configureLogger(loggerContext, "org.flywaydb.core.internal.schemahistory", this.atLeast(rootLevel, Level.WARN));
        this.configureLogger(loggerContext, "org.flywaydb.core.internal.database.base.Database", this.atLeast(rootLevel, Level.ERROR));
        this.configureLogger(loggerContext, "org.flywaydb.core.internal.command.DbMigrate", this.atLeast(rootLevel, Level.ERROR));

        return ExecutionStatus.DO_NOT_INVOKE_NEXT_IF_ANY;
    }

    private ConsoleAppender<ILoggingEvent> createConsoleAppender(
            final LoggerContext loggerContext,
            final String logFormat
    ) {
        final ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();

        consoleAppender.setContext(loggerContext);
        consoleAppender.setName("Console");
        consoleAppender.setWithJansi(this.shouldUseJansi(logFormat));

        if (JSON_FORMAT.equals(logFormat)) {
            final LogstashEncoder encoder = new LogstashEncoder();

            encoder.setContext(loggerContext);
            encoder.start();
            consoleAppender.setEncoder(encoder);
        } else {
            final PatternLayoutEncoder encoder = new PatternLayoutEncoder();

            encoder.setContext(loggerContext);
            encoder.setPattern(DEFAULT_CONSOLE_PATTERN);
            encoder.start();
            consoleAppender.setEncoder(encoder);
        }

        consoleAppender.start();
        return consoleAppender;
    }

    private void configureLogger(final LoggerContext loggerContext, final String loggerName, final Level level) {
        final ch.qos.logback.classic.Logger logger = loggerContext.getLogger(loggerName);

        logger.setLevel(level);
        logger.setAdditive(true);
    }

    private Level resolveLogLevel() {
        return Level.toLevel(this.resolveValue(LOG_LEVEL_ENV_KEY, LoggingConfiguration.LEVEL, DEFAULT_LOG_LEVEL), Level.INFO);
    }

    private String resolveLogFormat() {
        final String configuredFormat = this.resolveValue(LOG_FORMAT_ENV_KEY, LoggingConfiguration.FORMAT, DEFAULT_LOG_FORMAT);
        return JSON_FORMAT.equals(configuredFormat) ? JSON_FORMAT : DEFAULT_LOG_FORMAT;
    }

    private String resolveValue(final String shortEnvKey, final String propertyKey, final String defaultValue) {
        final String environmentValue = this.firstNonBlank(
                System.getenv(shortEnvKey),
                System.getenv(ConfigurationKeys.toEnvKey(propertyKey))
        );

        if (environmentValue != null) {
            return environmentValue;
        }

        final Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        return this.firstNonBlank(
                dotenv.get(shortEnvKey),
                dotenv.get(ConfigurationKeys.toEnvKey(propertyKey)),
                defaultValue
        );
    }

    private String firstNonBlank(final String... candidates) {
        for (String candidate : candidates) {
            if (candidate != null && !candidate.isBlank()) {
                return candidate.trim().toLowerCase(Locale.ROOT);
            }
        }

        return null;
    }

    private Level atLeast(final Level rootLevel, final Level minimumLevel) {
        return rootLevel.toInt() >= minimumLevel.toInt() ? rootLevel : minimumLevel;
    }

    private boolean shouldUseJansi(final String logFormat) {
        return !JSON_FORMAT.equals(logFormat)
                && System.getProperty("os.name", "").startsWith("Windows")
                && !System.getProperty("java.class.path", "").contains("idea_rt.jar");
    }
}