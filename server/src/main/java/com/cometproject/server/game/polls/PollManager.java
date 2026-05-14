package com.cometproject.server.game.polls;

import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.game.polls.types.Poll;
import com.cometproject.server.game.polls.types.PollQuestion;
import com.cometproject.server.game.polls.types.questions.InfobusQuestion;
import com.cometproject.server.game.polls.types.questions.MultipleChoiceQuestion;
import com.cometproject.server.storage.queries.polls.PollDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages poll runtime state for the Comet subsystem.
 */
public class PollManager implements Startable {
    private static Logger LOGGER = LoggerFactory.getLogger(PollManager.class.getName());
    private final Map<Integer, Integer> roomIdToPollId;
    private Map<Integer, Poll> polls;

    /**
     * Creates a poll manager instance for the Comet subsystem.
     */
    public PollManager() {
        this.polls = new ConcurrentHashMap<>();
        this.roomIdToPollId = new ConcurrentHashMap<>();
    }

    /**
     * Returns the instance for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public static PollManager getInstance() {
        return CometBootstrap.resolve(PollManager.class);
    }

    /**
     * Starts this Comet component.
     */
    @Override
    public void start() {
        if (this.polls != null) {
            for (Poll poll : this.polls.values()) {
                for (PollQuestion pollQuestion : poll.getPollQuestions().values()) {
                    if (pollQuestion instanceof MultipleChoiceQuestion) {
                        ((MultipleChoiceQuestion) pollQuestion).getChoices().clear();
                    }
                }

                poll.getPollQuestions().clear();
            }

            this.polls.clear();
            this.roomIdToPollId.clear();
        }

        this.polls = PollDao.getAllPolls();

        for (Poll poll : this.getPolls().values()) {
            if (!this.roomIdToPollId.containsKey(poll.getRoomId())) {
                this.roomIdToPollId.put(poll.getRoomId(), poll.getPollId());
            }
        }

        LOGGER.info("Loaded " + this.getPolls().size() + " poll(s)");
    }

    /**
     * Executes room has poll for this Comet contract.
     *
     * @param roomId Room identifier used by the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean roomHasPoll(int roomId) {
        Poll poll = this.getPollByRoomId(roomId);

        if(poll == null)
            return false;

        for (Map.Entry<Integer, PollQuestion> pollQuestion : poll.getPollQuestions().entrySet()) {
            if(pollQuestion.getValue() instanceof InfobusQuestion)
                return false;
        }

        return this.roomIdToPollId.containsKey(roomId);
    }

    /**
     * Returns the poll by room id for this Comet contract.
     *
     * @param roomId Room identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public Poll getPollByRoomId(int roomId) {
        if (!this.roomIdToPollId.containsKey(roomId)) {
            return null;
        }

        return this.getPollbyId(this.roomIdToPollId.get(roomId));
    }

    /**
     * Returns the pollby id for this Comet contract.
     *
     * @param pollId Poll id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public Poll getPollbyId(int pollId) {
        return this.polls.get(pollId);
    }

    /**
     * Returns the polls for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Integer, Poll> getPolls() {
        return polls;
    }
}
