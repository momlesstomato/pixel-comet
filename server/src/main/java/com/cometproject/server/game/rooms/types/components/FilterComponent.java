package com.cometproject.server.game.rooms.types.components;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.storage.queries.rooms.RoomFilterDao;

import java.util.Set;

/**
 * Owns filter behavior inside the room processing subsystem.
 */
public class FilterComponent {

    private final Room room;
    private final Set<String> filteredWords;

    /**
     * Creates a filter component instance for the room processing subsystem.
     *
     * @param room Room participating in the operation.
     */
    public FilterComponent(Room room) {
        this.room = room;

        this.filteredWords = RoomFilterDao.getFilterForRoom(room.getId());
    }

    /**
     * Executes add for this room processing contract.
     *
     * @param word Word supplied by the caller.
     */
    public void add(String word) {
        this.filteredWords.add(word);
        RoomFilterDao.saveWord(word, this.room.getId());
    }

    /**
     * Executes remove for this room processing contract.
     *
     * @param word Word supplied by the caller.
     */
    public void remove(String word) {
        this.filteredWords.remove(word);
        RoomFilterDao.removeWord(word, this.room.getId());
    }

    /**
     * Executes filter for this room processing contract.
     *
     * @param entity Entity supplied by the caller.
     * @param message Message supplied by the caller.
     * @return Result produced by the operation.
     */
    public String filter(PlayerEntity entity, String message) {
        String msg = message;

        if (!entity.hasRights()) {
            for (String word : this.filteredWords) {
                if (message.contains(word)) {
                    msg = msg.replace(word, Locale.getOrDefault("filter.bobba", "bobba"));
                }
            }
        }

        return msg;
    }

    /**
     * Returns the filtered words for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<String> getFilteredWords() {
        return this.filteredWords;
    }
}
