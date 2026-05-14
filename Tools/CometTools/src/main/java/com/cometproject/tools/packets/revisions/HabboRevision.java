package com.cometproject.tools.packets.revisions;

import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.tools.packets.instances.MessageComposer;
import com.cometproject.tools.packets.instances.MessageEvent;

import java.util.Map;


/**
 * Describes habbo revision behavior for the tooling subsystem.
 */
public class HabboRevision {
    private String releaseString;

    private Map<String, MessageComposer> composers;
    private Map<String, MessageEvent> events;

    /**
     * Creates a habbo revision instance for the tooling subsystem.
     *
     * @param releaseString Release string supplied by the caller.
     * @param composers Composers supplied by the caller.
     * @param events Events supplied by the caller.
     */
    public HabboRevision(String releaseString, Map<String, MessageComposer> composers, Map<String, MessageEvent> events) {
        this.releaseString = releaseString;

        this.composers = composers;
        this.events = events;
    }

    /**
     * Returns the release string for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    public String getReleaseString() {
        return this.releaseString;
    }

    /**
     * Returns the composers for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<String, MessageComposer> getComposers() {
        return composers;
    }

    /**
     * Returns the events for this tooling contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<String, MessageEvent> getEvents() {
        return events;
    }
}
