package com.cometproject.server.network.messages.outgoing.room.polls;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.polls.types.Poll;
import com.cometproject.server.game.polls.types.PollQuestion;
import com.cometproject.server.game.polls.types.questions.MultipleChoiceQuestion;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Map;

/**
 * Serializes the start infobus poll message for the Pixel Protocol client.
 */
public class StartInfobusPollMessageComposer extends MessageComposer {
    final private Poll poll;
    /**
     * Creates a start infobus poll message composer instance for the network message subsystem.
     *
     * @param poll Poll supplied by the caller.
     */
    public StartInfobusPollMessageComposer(Poll poll) {
        this.poll = poll;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.StartInfobusPollMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {

        for (Map.Entry<Integer, PollQuestion> pollQuestion : this.poll.getPollQuestions().entrySet()) {
            msg.writeString(pollQuestion.getValue().getQuestion());

            final int optionSizes = pollQuestion.getValue() instanceof MultipleChoiceQuestion ? ((MultipleChoiceQuestion) pollQuestion.getValue()).getChoices().size() : 1;

            msg.writeInt(optionSizes);

            if (optionSizes != 0) {
                for (int i = 0; i < optionSizes; i++) {
                    String choice = ((MultipleChoiceQuestion) pollQuestion.getValue()).getChoices().get(i);

                    msg.writeInt(i);
                    msg.writeString(choice);
                }
            }
        }
        /*
            msg.writeInt(1); // Answer Id
            msg.writeString("Una cabra."); // Answer Value
            msg.writeInt(2);
            msg.writeString("A tu madre.");
            msg.writeInt(3);
            msg.writeString("A Kev XDXDXD");*/
    }
}
