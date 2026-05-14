package com.cometproject.server.game.guides.types;

import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.sessions.Session;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Describes help request behavior for the Comet subsystem.
 */
public class HelpRequest {
    private final int playerId;

    private final int type;
    private final String message;

    private boolean recommendation = false;
    private boolean IsOK = false;
    private boolean IsST = false;

    private int processTicks = 60;
    private Set<Integer> declinedGuides = Sets.newConcurrentHashSet();

    public int guideId = -1;

    /**
     * Creates a help request instance for the Comet subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param type Type supplied by the caller.
     * @param message Message supplied by the caller.
     */
    public HelpRequest(final int playerId, final int type, final String message) {
        this.playerId = playerId;
        this.type = type;
        this.message = message;
    }

    /**
     * Executes decline for this Comet contract.
     *
     * @param playerId Player identifier used by the operation.
     */
    public void decline(final int playerId) {
        this.declinedGuides.add(playerId);
    }

    /**
     * Executes declined for this Comet contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean declined(final int playerId) {
        return this.declinedGuides.contains(playerId);
    }

    /**
     * Executes is begin for this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean IsBEGIN() {
        return this.IsOK;
    }

    /**
     * Updates the is begin for this Comet contract.
     */
    public void setIsBEGIN() {
        this.IsOK = true;
    }

    /**
     * Executes is stop for this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean IsStop() {
        return this.IsST;
    }

    /**
     * Updates the is stop for this Comet contract.
     */
    public void setIsStop() {
        this.IsST = true;
    }

    /**
     * Returns the player session for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public Session getPlayerSession() {
        return NetworkManager.getInstance().getSessions().getByPlayerId(this.playerId);
    }

    /**
     * Returns the guide session for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public Session getGuideSession() {
        return this.guideId > 0 ? NetworkManager.getInstance().getSessions().getByPlayerId(this.guideId) : null;
    }

    /**
     * Returns the player id for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Returns the type for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getType() {
        return type;
    }

    /**
     * Returns the message for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Indicates whether this Comet contract has guide.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasGuide() {
        return this.guideId > 0;
    }

    /**
     * Updates the guide for this Comet contract.
     *
     * @param guideId Guide id supplied by the caller.
     */
    public void setGuide(final int guideId) {
        this.guideId = guideId;
    }

    /**
     * Executes increment process ticks for this Comet contract.
     */
    public void incrementProcessTicks() {
        this.processTicks++;
    }

    /**
     * Executes reset process ticks for this Comet contract.
     */
    public void resetProcessTicks() {
        this.processTicks = 0;
    }

    /**
     * Returns the process ticks for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getProcessTicks() {
        return this.processTicks;
    }

    /**
     * Returns the other element for this Comet contract.
     *
     * @param other Other supplied by the caller.
     * @param r R supplied by the caller.
     * @return Value exposed by the contract.
     */
    public Session getOtherElement(Session other, HelpRequest r){
        if(other == r.getGuideSession())
            return r.getPlayerSession();

        if(other == r.getPlayerSession())
            return r.getGuideSession();

        else return null;
    }
}
