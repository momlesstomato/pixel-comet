package com.cometproject.server.composers.catalog.pets;

import com.cometproject.api.game.pets.IPetRace;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;

/**
 * Serializes the pet races message for the Pixel Protocol client.
 */
public class PetRacesMessageComposer extends MessageComposer {
    private final String raceString;
    private final List<IPetRace> races;

    /**
     * Creates a pet races message composer instance for the pet subsystem.
     *
     * @param raceString Race string value supplied by the caller.
     * @param races Races value supplied by the caller.
     */
    public PetRacesMessageComposer(final String raceString, final List<IPetRace> races) {
        this.raceString = raceString;
        this.races = races;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.SellablePetBreedsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.raceString);
        msg.writeInt(this.races.size());

        for (IPetRace race : this.races) {
            msg.writeInt(race.getRaceId());
            msg.writeInt(race.getColour1());
            msg.writeInt(race.getColour2());
            msg.writeBoolean(race.hasColour1());
            msg.writeBoolean(race.hasColour2());
        }
    }

    /**
     * Releases references held by this protocol message.
     */
    @Override
    public void dispose() {
        this.races.clear();
    }
}
