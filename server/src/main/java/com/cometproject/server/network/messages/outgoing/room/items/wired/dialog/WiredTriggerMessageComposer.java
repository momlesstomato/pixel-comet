package com.cometproject.server.network.messages.outgoing.room.items.wired.dialog;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.WiredUtil;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredActionItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredTriggerItem;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;


/**
 * Serializes the wired trigger message for the Pixel Protocol client.
 */
public class WiredTriggerMessageComposer extends MessageComposer {
    private final List<WiredActionItem> incompatibleActions;
    private final WiredTriggerItem wiredTrigger;

    /**
     * Creates a wired trigger message composer instance for the network message subsystem.
     *
     * @param wiredTriggerItem Wired trigger item supplied by the caller.
     */
    public WiredTriggerMessageComposer(final WiredTriggerItem wiredTriggerItem) {
        this.wiredTrigger = wiredTriggerItem;
        this.incompatibleActions = wiredTriggerItem.getIncompatibleActions();
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.WiredTriggerConfigMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(false); // advanced
        msg.writeInt(WiredUtil.MAX_FURNI_SELECTION);

        msg.writeInt(wiredTrigger.getWiredData().getSelectedIds().size());

        for (Long itemId : wiredTrigger.getWiredData().getSelectedIds()) {
            msg.writeInt(ItemManager.getInstance().getItemVirtualId(itemId));
        }

        msg.writeInt(wiredTrigger.getDefinition().getSpriteId());
        msg.writeInt(wiredTrigger.getVirtualId());

        msg.writeString(wiredTrigger.getWiredData().getText());

        msg.writeInt(wiredTrigger.getWiredData().getParams().size());

        for (int param : wiredTrigger.getWiredData().getParams().values()) {
            msg.writeInt(param);
        }

        msg.writeInt(wiredTrigger.getWiredData().getSelectionType());
        msg.writeInt(wiredTrigger.getInterface());

        msg.writeInt(incompatibleActions.size());

        for (WiredActionItem incompatibleAction : incompatibleActions) {
            msg.writeInt(incompatibleAction.getDefinition().getSpriteId());
        }
    }

    /**
     * Releases resources owned by this network message component.
     */
    @Override
    public void dispose() {
        this.incompatibleActions.clear();
    }
}
