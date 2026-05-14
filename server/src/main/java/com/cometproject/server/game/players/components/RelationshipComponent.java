package com.cometproject.server.game.players.components;

import com.cometproject.api.game.players.IPlayer;
import com.cometproject.api.game.players.data.components.PlayerRelationships;
import com.cometproject.api.game.players.data.components.messenger.RelationshipLevel;
import com.cometproject.server.game.players.types.PlayerComponent;
import com.cometproject.server.storage.queries.player.relationships.RelationshipDao;

import java.util.Map;


/**
 * Owns relationship behavior inside the player subsystem.
 */
public class RelationshipComponent extends PlayerComponent implements PlayerRelationships {
    private Map<Integer, RelationshipLevel> relationships;

    /**
     * Creates a relationship component instance for the player subsystem.
     *
     * @param player Player participating in the operation.
     */
    public RelationshipComponent(IPlayer player) {
        super(player);

        this.relationships = RelationshipDao.getRelationshipsByPlayerId(player.getId());
    }

    /**
     * Releases resources owned by this player component.
     */
    public void dispose() {
        this.relationships.clear();
        this.relationships = null;
    }

    /**
     * Executes get for this player contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    @Override
    public RelationshipLevel get(int playerId) {
        return this.relationships.get(playerId);
    }

    /**
     * Executes remove for this player contract.
     *
     * @param playerId Player identifier used by the operation.
     */
    @Override
    public void remove(int playerId) {
        this.getRelationships().remove(playerId);

        this.getPlayer().flush();
    }

    /**
     * Executes count for this player contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public int count() {
        return this.relationships.size();
    }

    /**
     * Returns the relationships for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Map<Integer, RelationshipLevel> getRelationships() {
        return this.relationships;
    }
}
