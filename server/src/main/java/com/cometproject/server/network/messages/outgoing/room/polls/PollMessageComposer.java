package com.cometproject.server.network.messages.outgoing.room.polls;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.polls.types.Poll;
import com.cometproject.server.game.polls.types.PollQuestion;
import com.cometproject.server.game.polls.types.questions.MultipleChoiceQuestion;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Map;

/**
 * Serializes the poll message for the Pixel Protocol client.
 */
public class PollMessageComposer extends MessageComposer {

    private final Poll poll;

    /**
     * Creates a poll message composer instance for the network message subsystem.
     *
     * @param poll Poll supplied by the caller.
     */
    public PollMessageComposer(final Poll poll) {
        this.poll = poll;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.PollMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.poll.getPollId());
        msg.writeString(this.poll.getPollTitle());
        msg.writeString(this.poll.getThanksMessage());
        msg.writeInt(this.poll.getPollQuestions().size());

        for (Map.Entry<Integer, PollQuestion> pollQuestion : this.poll.getPollQuestions().entrySet()) {
            msg.writeInt(pollQuestion.getKey());
            msg.writeInt(0);
            msg.writeInt(pollQuestion.getValue().getType());
            msg.writeString(pollQuestion.getValue().getQuestion());
            msg.writeInt(0);

            final int minimumSelections = pollQuestion.getValue() instanceof MultipleChoiceQuestion ? 1 : 0;
            final int optionSizes = pollQuestion.getValue() instanceof MultipleChoiceQuestion ? ((MultipleChoiceQuestion) pollQuestion.getValue()).getChoices().size() : 0;

            msg.writeInt(minimumSelections);
            msg.writeInt(optionSizes);

            if (optionSizes != 0) {
                for (int i = 0; i < optionSizes; i++) {
                    String choice = ((MultipleChoiceQuestion) pollQuestion.getValue()).getChoices().get(i);

                    msg.writeString(i + "");
                    msg.writeString(choice);
                    msg.writeInt(i);
                }
            }

            msg.writeInt(0);
        }

        msg.writeBoolean(true);
    }
}
