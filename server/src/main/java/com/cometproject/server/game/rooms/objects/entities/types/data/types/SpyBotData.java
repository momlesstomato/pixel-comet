package com.cometproject.server.game.rooms.objects.entities.types.data.types;

import com.cometproject.server.game.rooms.objects.entities.types.data.BotDataObject;

import java.util.List;

/**
 * Carries spy bot data data for the room subsystem.
 */
public class SpyBotData implements BotDataObject {
    private List<String> visitors;

    /**
     * Creates a spy bot data instance for the room subsystem.
     *
     * @param visitors Visitors supplied by the caller.
     */
    public SpyBotData(List<String> visitors) {
        this.visitors = visitors;
    }

    /**
     * Returns the visitors for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public List<String> getVisitors() {
        return visitors;
    }
}
