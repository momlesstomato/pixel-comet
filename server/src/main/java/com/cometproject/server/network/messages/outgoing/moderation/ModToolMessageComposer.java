package com.cometproject.server.network.messages.outgoing.moderation;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.moderation.ModerationManager;
import com.cometproject.server.game.moderation.types.tickets.HelpTicket;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the mod tool message for the Pixel Protocol client.
 */
public class ModToolMessageComposer extends MessageComposer {

    /**
     * Creates a mod tool message composer instance for the network message subsystem.
     */
    public ModToolMessageComposer() {

    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ModeratorInitMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(ModerationManager.getInstance().getTickets().size());

        for (HelpTicket helpTicket : ModerationManager.getInstance().getTickets().values()) {
            helpTicket.compose(msg);
        }

        msg.writeInt(ModerationManager.getInstance().getUserPresets().size());

        for (String preset : ModerationManager.getInstance().getUserPresets()) {
            msg.writeString(preset);
        }

        msg.writeInt(0);

        /*msg.writeInt(ModerationManager.getInstance().getActionCategories().size());

        for (ActionCategory actionCategory : ModerationManager.getInstance().getActionCategories()) {
            msg.writeString(actionCategory.getCategoryName());
            msg.writeBoolean(false); // unused bool
            msg.writeInt(actionCategory.getPresets().size());

            for (ActionPreset preset : actionCategory.getPresets()) {
                msg.writeString(preset.getName());
                msg.writeString(preset.getMessage());
                msg.writeInt(preset.getBanLength());
                msg.writeInt(preset.getAvatarBanLength());
                msg.writeInt(preset.getMuteLength());
                msg.writeInt(preset.getTradeLockLength());
                msg.writeString(preset.getDescription());
                msg.writeBoolean(false); // show habbo way
            }
        }*/

        // Fuses
        msg.writeBoolean(true); // tickets
        msg.writeBoolean(true); // chatlog
        msg.writeBoolean(true); // message, caution, user info
        msg.writeBoolean(true); // kick fuse / user info
        msg.writeBoolean(true); // ban
        msg.writeBoolean(true); // room alert
        msg.writeBoolean(true); // room kick

        msg.writeInt(ModerationManager.getInstance().getRoomPresets().size());

        for (String preset : ModerationManager.getInstance().getRoomPresets()) {
            msg.writeString(preset);
        }

        msg.writeInt(0);
    }
}
