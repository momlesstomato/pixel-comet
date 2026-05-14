package com.cometproject.server.network.messages.outgoing.room.polls;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the quick poll vote message for the Pixel Protocol client.
 */
public class QuickPollVoteMessageComposer extends MessageComposer {

    private final int playerId;
    private final String myVote;

    private final int yesVotesCount;
    private final int noVotesCount;

    /**
     * Creates a quick poll vote message composer instance for the network message subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param myVote My vote supplied by the caller.
     * @param yesVotesCount Yes votes count supplied by the caller.
     * @param noVotesCount No votes count supplied by the caller.
     */
    public QuickPollVoteMessageComposer(int playerId, String myVote, int yesVotesCount, int noVotesCount) {
        this.playerId = playerId;
        this.myVote = myVote;
        this.yesVotesCount = yesVotesCount;
        this.noVotesCount = noVotesCount;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.QuickPollResultMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.playerId);
        msg.writeString(myVote);
        msg.writeInt(2);
        msg.writeString("1");
        msg.writeInt(this.yesVotesCount);

        msg.writeString("0");
        msg.writeInt(this.noVotesCount);
    }
}
