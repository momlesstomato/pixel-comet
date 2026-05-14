package com.cometproject.server.network.messages.outgoing.landing.calendar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.landing.LandingManager;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the campaign calendar data message for the Pixel Protocol client.
 */
public class CampaignCalendarDataMessageComposer extends MessageComposer {

    private boolean openBox[];
    private int openCount;
    private int lateCount;
    private int unlockDay;
    private int openSize;

    /**
     * Creates a campaign calendar data message composer instance for the network message subsystem.
     *
     * @param o O supplied by the caller.
     */
    public CampaignCalendarDataMessageComposer(boolean[] o) {
        this.openBox = o;
        this.unlockDay = LandingManager.getInstance().getUnlockDays();
        this.openSize = o.length;
    }


    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.CampaignCalendarDataMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString("xmas14");
        msg.writeString("");

        msg.writeInt(this.unlockDay); // día actual.
        msg.writeInt(LandingManager.getInstance().getTotalDays()); // días totales

        for (int i = 0; i < this.openSize; i++){
            if(this.openBox[i]) {
                this.openCount++;
            } else {
                if(this.unlockDay == i)
                    continue;

                this.lateCount++;
            }
        }

        msg.writeInt(this.openCount); // cajas abiertas.

        for (int i = 0; i < this.openSize; i++){
            if(this.openBox[i])
                msg.writeInt(i);
        }

        msg.writeInt(this.lateCount); // cajas que se han pasado de fecha.

        for (int i = 0; i < this.openSize; i++){

            if(this.unlockDay == i)
                continue;

            if(!this.openBox[i]) {
                msg.writeInt(i);
            }
        }
    }
}
