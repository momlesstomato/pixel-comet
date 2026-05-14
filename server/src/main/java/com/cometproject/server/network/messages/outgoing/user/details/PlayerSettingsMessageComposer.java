package com.cometproject.server.network.messages.outgoing.user.details;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.players.types.PlayerSettings;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the player settings message for the Pixel Protocol client.
 */
public class PlayerSettingsMessageComposer extends MessageComposer {
    private final PlayerSettings playerSettings;
    private int roomToolStatus;

    /**
     * Creates a player settings message composer instance for the network message subsystem.
     *
     * @param playerSettings Player settings supplied by the caller.
     * @param status Status supplied by the caller.
     */
    public PlayerSettingsMessageComposer(final PlayerSettings playerSettings, int status) {
        this.playerSettings = playerSettings;
        this.roomToolStatus = status;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.SoundSettingsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.playerSettings.getVolumes().getSystemVolume());
        msg.writeInt(this.playerSettings.getVolumes().getFurniVolume());
        msg.writeInt(this.playerSettings.getVolumes().getTraxVolume());
        msg.writeBoolean(this.playerSettings.isUseOldChat()); // old chat enabled?
        msg.writeBoolean(this.playerSettings.ignoreEvents()); // ignore room invites
        msg.writeBoolean(this.playerSettings.roomCameraFollow()); //disable_room_camera_follow_checkbox
        msg.writeInt(this.roomToolStatus);
        msg.writeInt(this.playerSettings.getBubbleId());
    }
}
