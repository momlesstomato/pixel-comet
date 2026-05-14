package com.cometproject.server.network.messages.incoming.catalog.pets;

import com.cometproject.api.game.pets.IPetRace;
import com.cometproject.server.composers.catalog.pets.PetRacesMessageComposer;
import com.cometproject.server.game.pets.PetManager;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Represents the pet races message event published by the network message subsystem.
 */
public class PetRacesMessageEvent
implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void handle(Session client, MessageEvent msg) {
        String petRace = msg.readString();
        String[] splitRace = petRace.split("a0 pet");
        if (splitRace.length < 2 || !StringUtils.isNumeric((String)splitRace[1])) {
            return;
        }
        int raceId = Integer.parseInt(splitRace[1]);
        List<IPetRace> races = PetManager.getInstance().getRacesByRaceId(raceId);
        client.send(new PetRacesMessageComposer(petRace, races));
    }
}

