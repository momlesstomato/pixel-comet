package com.cometproject.server.network.messages.incoming.landing;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.landing.LTDCountdownMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * Represents the ltd countdown message event published by the network message subsystem.
 */
public class LTDCountdownMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final String text = msg.readString();
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        f.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));

        Date date = f.parse(text);

        int timeSpan = (int)(date.getTime() / 1000 - Comet.getTime());

        client.send(new LTDCountdownMessageComposer(text, timeSpan));
    }
}
