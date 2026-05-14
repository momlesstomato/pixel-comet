package com.cometproject.server.network.messages.outgoing.room.polls;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the get infobus poll results message for the Pixel Protocol client.
 */
public class GetInfobusPollResultsMessageComposer extends MessageComposer {
    final Room room;
    /**
     * Creates a get infobus poll results message composer instance for the network message subsystem.
     *
     * @param room Room participating in the operation.
     */
    public GetInfobusPollResultsMessageComposer(Room room) {
        this.room = room;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.GetInfobusPollResultsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        int totalVotes = this.room.getInfobusChoice1().size() + this.room.getInfobusChoice2().size() + this.room.getInfobusChoice3().size();
        msg.writeString("Test"); // Question
        msg.writeInt(3); // Amount of answers

        msg.writeInt(0); // Answer Id
        msg.writeString(""); // Answer Value
        msg.writeInt(this.room.getInfobusChoice1().size());

        msg.writeInt(0);
        msg.writeString("");
        msg.writeInt(this.room.getInfobusChoice2().size());

        msg.writeInt(0);
        msg.writeString("");
        msg.writeInt(this.room.getInfobusChoice3().size());

        msg.writeInt(totalVotes); // Total Votes
    }
}
