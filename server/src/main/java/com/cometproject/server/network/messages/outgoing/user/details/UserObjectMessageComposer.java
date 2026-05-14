package com.cometproject.server.network.messages.outgoing.user.details;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * Serializes the user object message for the Pixel Protocol client.
 */
public class UserObjectMessageComposer extends MessageComposer {

    private final Player player;

    /**
     * Creates a user object message composer instance for the network message subsystem.
     *
     * @param player Player participating in the operation.
     */
    public UserObjectMessageComposer(final Player player) {
        this.player = player;
    }

    /**
     * Returns the date for this network message contract.
     *
     * @param timestamp Timestamp supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static String getDate(int timestamp) {
        Date d = new Date(timestamp * 1000L);
        SimpleDateFormat df = new SimpleDateFormat(Locale.getOrDefault("date.format", "MMM d yyyy 'at' HH:mm"));
        df.setTimeZone(TimeZone.getTimeZone("GMT"));

        return df.format(d);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.UserObjectMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(player.getId());
        msg.writeString(player.getData().getUsername());
        msg.writeString(player.getData().getFigure());
        msg.writeString(player.getData().getGender().toUpperCase());
        msg.writeString(player.getData().getMotto());
        msg.writeString(player.getData().getUsername().toLowerCase());
        msg.writeBoolean(true);

        msg.writeInt(8); // ??? (8)

        msg.writeInt(player.getStats().getDailyRespects()); // daily respects!
        msg.writeInt(player.getStats().getScratches()); // (3) pet respects I guess

        msg.writeBoolean(true);
        msg.writeString(getDate(player.getData().getLastVisit()));

        msg.writeBoolean(player.getData().getChangingName()); // can change username
        msg.writeBoolean(false);
    }
}
