//package com.cometproject.server.network.messages.outgoing.room.alerts;
//
//import com.cometproject.api.networking.messages.IComposer;
//import com.cometproject.server.network.messages.composers.MessageComposer;
//import com.cometproject.server.protocol.headers.Composers;
//
//
/**
 * Serializes the room connection error message for the Pixel Protocol client.
 */
//public class RoomConnectionErrorMessageComposer extends MessageComposer {
//    private final int errorCode;
//    private final String extras;
//
/**
 * Creates a room connection error message composer instance for the Snow War game subsystem.
 *
 * @param errorCode Error code supplied by the caller.
 * @param extras Extras supplied by the caller.
 */
//    public RoomConnectionErrorMessageComposer(final int errorCode, final String extras) {
//        this.errorCode = errorCode;
//        this.extras = extras;
//    }
//
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
//        msg.writeInt(errorCode);
//
//        if (!extras.isEmpty())
//            msg.writeString(extras);
//
//    }
//}
