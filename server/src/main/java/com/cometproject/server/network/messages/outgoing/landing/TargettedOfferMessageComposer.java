package com.cometproject.server.network.messages.outgoing.landing;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the targetted offer message for the Pixel Protocol client.
 */
public class TargettedOfferMessageComposer extends MessageComposer {
    private String type;
    private boolean resize;
    private String button;
    private String image;
    private String icon;
    private String data;
    private int timer;

    /**
     * Creates a targetted offer message composer instance for the network message subsystem.
     *
     * @param type Type supplied by the caller.
     * @param button Button supplied by the caller.
     * @param image Image supplied by the caller.
     * @param icon Icon supplied by the caller.
     * @param data Data supplied by the caller.
     * @param resize Resize supplied by the caller.
     * @param timer Timer supplied by the caller.
     */
    public TargettedOfferMessageComposer(String type, String button, String image, String icon, String data, Boolean resize, int timer){
        this.type = type;
        this.button = button;
        this.image = image;
        this.icon = icon;
        this.data = data;
        this.resize = resize;
        this.timer = timer;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.TargettedOfferMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(resize ? 4 : 1); // Open 4 - Minimized 1
        msg.writeInt(1);
        msg.writeString(this.type);
        msg.writeString(this.type);
        msg.writeInt(999999999);
        msg.writeInt(999999999);
        msg.writeInt(0);
        msg.writeInt(1);
        msg.writeInt(this.timer);
        msg.writeString(this.button);
        msg.writeString(this.data);
        msg.writeString(this.image + ".png");
        msg.writeString(this.icon + ".png");
        msg.writeInt(1);
        msg.writeInt(1);
        msg.writeString("");
    }
}
