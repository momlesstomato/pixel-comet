//package com.cometproject.server.network.messages.outgoing.room.alerts;
//
//import com.cometproject.api.networking.messages.IComposer;
//import com.cometproject.server.network.messages.composers.MessageComposer;
//import com.cometproject.server.protocol.headers.Composers;
//
//
/**
 * Serializes the room full message for the Pixel Protocol client.
 */
//public class RoomFullMessageComposer extends MessageComposer {
//    @Override
/**
 * Returns the outgoing Pixel Protocol message id.
 *
 * @return Value exposed by the contract.
 */
//    public short getId() {
//        return Composers.RoomErrorNotifMessageComposer;
//    }
//
//    @Override
/**
 * Writes this message body using the Pixel Protocol field order.
 *
 * @param msg Msg supplied by the caller.
 */
//    public void compose(IComposer msg) {
//        msg.writeInt(1);
//        msg.writeString("/x363");
//
//    }
//}
