package com.cometproject.server.game.polls.types.questions;

import com.cometproject.api.utilities.JsonUtil;
import com.cometproject.server.game.polls.types.PollQuestion;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes multiple choice question behavior for the Comet subsystem.
 */
public class MultipleChoiceQuestion extends PollQuestion {
    private List<String> choices;

    /**
     * Creates a multiple choice question instance for the Comet subsystem.
     *
     * @param question Question supplied by the caller.
     * @param questionData Question data supplied by the caller.
     */
    public MultipleChoiceQuestion(String question, String questionData) {
        super(question);

        this.choices = JsonUtil.getInstance().fromJson(questionData, new TypeToken<ArrayList<String>>() {
        }.getType());
    }

    /**
     * Returns the choices for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public List<String> getChoices() {
        return this.choices;
    }

    /**
     * Returns the type for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getType() {
        return 2;
    }
}
