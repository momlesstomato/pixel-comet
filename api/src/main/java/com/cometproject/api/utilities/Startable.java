package com.cometproject.api.utilities;

/**
 * Defines the startup contract for lifecycle-managed services.
 *
 * <p>Startable components are also stopable so the bootstrapper can manage a
 * symmetric start and stop lifecycle.
 */
public interface Startable extends Stopable {
    /**
     * Starts the component.
     */
    void start();
}