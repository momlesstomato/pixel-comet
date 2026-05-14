package com.cometproject.server.game.polls.types;

/**
 * Describes poll question behavior for the Comet subsystem.
 */
public abstract class PollQuestion {
    private String question;

    /**
     * Creates a poll question instance for the Comet subsystem.
     *
     * @param question Question supplied by the caller.
     */
    public PollQuestion(String question) {
        this.question = question;
    }

    /**
     * Returns the question for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns the type for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public abstract int getType();
}
