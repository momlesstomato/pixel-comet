package com.cometproject.server.game.polls.types.questions;

import com.cometproject.server.game.polls.types.PollQuestion;

/**
 * Describes worded poll question behavior for the Comet subsystem.
 */
public class WordedPollQuestion extends PollQuestion {
    /**
     * Creates a worded poll question instance for the Comet subsystem.
     *
     * @param question Question supplied by the caller.
     */
    public WordedPollQuestion(String question) {
        super(question);
    }

    /**
     * Returns the type for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getType() {
        return 3;
    }

}
