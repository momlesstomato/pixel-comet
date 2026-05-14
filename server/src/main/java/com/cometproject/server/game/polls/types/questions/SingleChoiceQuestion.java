package com.cometproject.server.game.polls.types.questions;

/**
 * Describes single choice question behavior for the Comet subsystem.
 */
public class SingleChoiceQuestion extends MultipleChoiceQuestion {
    /**
     * Creates a single choice question instance for the Comet subsystem.
     *
     * @param question Question supplied by the caller.
     * @param questionData Question data supplied by the caller.
     */
    public SingleChoiceQuestion(String question, String questionData) {
        super(question, questionData);
    }

    /**
     * Returns the type for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getType() {
        return 1;
    }
}
