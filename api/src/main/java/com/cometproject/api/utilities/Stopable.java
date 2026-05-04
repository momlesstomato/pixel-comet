package com.cometproject.api.utilities;

/**
 * Defines the shutdown contract for lifecycle-managed services.
 *
 * <p>Implementations may override the default no-op behaviour when they own
 * resources that must be released during server shutdown.
 */
public interface Stopable {
    /**
     * Stops the component and releases any owned resources.
     */
    default void stop() {
    }
}