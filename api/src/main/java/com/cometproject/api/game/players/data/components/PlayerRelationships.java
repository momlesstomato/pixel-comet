package com.cometproject.api.game.players.data.components;

import com.cometproject.api.game.players.data.components.messenger.RelationshipLevel;

import java.util.Map;

/**
 * Defines the player relationships contract for the player subsystem.
 */
public interface PlayerRelationships {
    /**
     * Executes the count by level operation for this player contract.
     *
     * @param level Level value supplied by the caller.
     * @param relationships Relationships value supplied by the caller.
     * @return Result produced by the operation.
     */
    static int countByLevel(RelationshipLevel level, Map<Integer, RelationshipLevel> relationships) {
        int levelCount = 0;

        for (RelationshipLevel relationship : relationships.values()) {
            if (relationship == level) levelCount++;
        }

        return levelCount;
    }

    /**
     * Executes the get operation for this player contract.
     *
     * @param playerId Player id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    RelationshipLevel get(int playerId);

    /**
     * Executes the remove operation for this player contract.
     *
     * @param playerId Player id value supplied by the caller.
     */
    void remove(int playerId);

    /**
     * Executes the count operation for this player contract.
     *
     * @return Result produced by the operation.
     */
    int count();

    /**
     * Returns the relationships associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Integer, RelationshipLevel> getRelationships();
}
