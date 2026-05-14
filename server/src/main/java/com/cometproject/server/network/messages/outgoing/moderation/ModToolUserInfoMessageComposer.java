package com.cometproject.server.network.messages.outgoing.moderation;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.game.players.data.PlayerData;
import com.cometproject.server.game.players.types.PlayerStatistics;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Date;


/**
 * Serializes the mod tool user info message for the Pixel Protocol client.
 */
public class ModToolUserInfoMessageComposer extends MessageComposer {
    private final PlayerData playerData;
    private final PlayerStatistics playerStatistics;

    /**
     * Creates a mod tool user info message composer instance for the network message subsystem.
     *
     * @param playerData Player data supplied by the caller.
     * @param playerStatistics Player statistics supplied by the caller.
     */
    public ModToolUserInfoMessageComposer(final PlayerData playerData, final PlayerStatistics playerStatistics) {
        this.playerData = playerData;
        this.playerStatistics = playerStatistics;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ModeratorUserInfoMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(playerData.getId());
        msg.writeString(playerData.getUsername());
        msg.writeString(playerData.getFigure());

        msg.writeInt((int) (Comet.getTime() - playerData.getRegTimestamp()) / 60);
        msg.writeInt((int) (Comet.getTime() - playerData.getLastVisit()) / 60);

        msg.writeBoolean(PlayerManager.getInstance().isOnline(playerData.getId()));

        msg.writeInt(playerStatistics.getHelpTickets());
        msg.writeInt(playerStatistics.getAbusiveHelpTickets());
        msg.writeInt(playerStatistics.getCautions());
        msg.writeInt(playerStatistics.getBans());
        msg.writeInt(0); // TODO: FInd out what this is

        boolean isTradeLocked = playerStatistics.getTradeLock() > 0;
        Date date = new Date(playerStatistics.getTradeLock() * 1000);

        msg.writeString(isTradeLocked ? date.toGMTString() : "N/A"); //trading_lock_expiry_txt

        msg.writeString("N/A"); // TODO: purchase logging
        msg.writeInt(1); // ???
        msg.writeInt(0); // Banned accounts

        // TODO: Find banned accounts using this IP address or linked to this e-mail address (for hotels that use the Habbo ID system)

        msg.writeString("Email: " + playerData.getEmail());
        msg.writeString("Rank: " + playerData.getRank() + "    IP: " + playerData.getIpAddress());

        if(isTradeLocked) {
            msg.writeString("Trade Block");
            msg.writeInt(8); // Red Color // Sanction Degree
            return;
        }

        msg.writeString("");
        msg.writeInt(0);
    }
}
