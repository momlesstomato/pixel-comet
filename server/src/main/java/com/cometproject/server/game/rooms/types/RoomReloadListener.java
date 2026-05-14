package com.cometproject.server.game.rooms.types;

import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.google.common.collect.Sets;

import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Describes room reload listener behavior for the room subsystem.
 */
public class RoomReloadListener {
    private final Set<Player> players = Sets.newConcurrentHashSet();
    private final BiConsumer<Set<Player>, Room> consumer;

    /**
     * Creates a room reload listener instance for the room subsystem.
     *
     * @param room Room participating in the operation.
     * @param consumer Consumer supplied by the caller.
     */
    public RoomReloadListener(final Room room, BiConsumer<Set<Player>, Room> consumer) {
        this.consumer = consumer;

        this.addPlayers(room);
    }

    /**
     * Handles the reloaded callback for this room contract.
     *
     * @param room Room participating in the operation.
     */
    public void onReloaded(Room room) {
        this.consumer.accept(this.players, room);
    }

    private void addPlayers(Room room) {
        for (PlayerEntity playerEntity : room.getEntities().getPlayerEntities()) {
            if (playerEntity.getPlayer() != null) {
                this.players.add(playerEntity.getPlayer());
            }
        }
    }
}
