package com.cometproject.api.game.moderation.guides;

import com.cometproject.api.networking.sessions.ISession;

/**
 * Defines the i help request contract for the Comet subsystem.
 */
public interface IHelpRequest {
    /**
     * Executes the decline operation for this moderation contract.
     *
     * @param playerId Player id value supplied by the caller.
     */
    void decline(int playerId);

    /**
     * Executes the declined operation for this moderation contract.
     *
     * @param playerId Player id value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean declined(int playerId);

    /**
     * Returns the player session associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ISession getPlayerSession();

    /**
     * Returns the guide session associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ISession getGuideSession();

    /**
     * Returns the player id associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getPlayerId();

    /**
     * Returns the type associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getType();

    /**
     * Returns the message associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getMessage();

    /**
     * Indicates whether this moderation contract has guide.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean hasGuide();

    /**
     * Updates the guide value for this moderation contract.
     *
     * @param guideId Guide id value supplied by the caller.
     */
    void setGuide(int guideId);

    /**
     * Executes the increment process ticks operation for this moderation contract.
     */
    void incrementProcessTicks();

    /**
     * Executes the reset process ticks operation for this moderation contract.
     */
    void resetProcessTicks();

    /**
     * Returns the process ticks associated with this moderation contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getProcessTicks();
}
