package com.cometproject.server.game.rooms.objects.entities.types.ai.bots;

import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.ai.AbstractBotAI;


/**
 * Describes default ai behavior for the room subsystem.
 */
public class DefaultAI extends AbstractBotAI {

    /**
     * Creates a default ai instance for the room subsystem.
     *
     * @param entity Entity supplied by the caller.
     */
    public DefaultAI(RoomEntity entity) {
        super(entity);
    }
}
