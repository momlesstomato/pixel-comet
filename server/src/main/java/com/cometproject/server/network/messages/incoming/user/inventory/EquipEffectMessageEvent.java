package com.cometproject.server.network.messages.incoming.user.inventory;

import com.cometproject.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.user.inventory.EffectsInventoryMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the equip effect message event published by the network message subsystem.
 */
public class EquipEffectMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final int effectId = msg.readInt();

        if (client.getPlayer() == null || client.getPlayer().getInventory() == null) {
            return;
        }

        if (!client.getPlayer().getInventory().hasEffect(effectId)) {
            return;
        }

        client.getPlayer().getInventory().setEquippedEffect(effectId);
        client.getPlayer().getEntity().applyEffect(new PlayerEffect(effectId, false));

        client.send(new EffectsInventoryMessageComposer(client.getPlayer().getInventory().getEffects(), client.getPlayer().getInventory().getEquippedEffect()));
    }
}
