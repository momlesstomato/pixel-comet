package com.cometproject.api.game.players.data.components.permissions;

/**
 * Defines the player rank contract for the player subsystem.
 */
public interface PlayerRank {
    /**
     * Returns the id associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Returns the name associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getName();

    /**
     * Executes the flood bypass operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean floodBypass();

    /**
     * Executes the flood time operation for this player contract.
     *
     * @return Result produced by the operation.
     */
    int floodTime();

    /**
     * Executes the disconnectable operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean disconnectable();

    /**
     * Executes the bannable operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean bannable();

    /**
     * Executes the mod tool operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean modTool();

    /**
     * Executes the alfa tool operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean alfaTool();

    /**
     * Executes the room kickable operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean roomKickable();

    /**
     * Executes the room full control operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean roomFullControl();

    /**
     * Executes the room mute bypass operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean roomMuteBypass();

    /**
     * Executes the room filter bypass operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean roomFilterBypass();

    /**
     * Executes the room ignorable operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean roomIgnorable();

    /**
     * Executes the room enter full operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean roomEnterFull();

    /**
     * Executes the room enter locked operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean roomEnterLocked();

    /**
     * Executes the room staff pick operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean roomStaffPick();

    /**
     * Executes the messenger staff chat operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean messengerStaffChat();
    /**
     * Executes the messenger mod chat operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean messengerModChat();

    /**
     * Executes the messenger log chat operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean messengerLogChat();

    /**
     * Executes the messenger alfa chat operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean messengerAlfaChat();

    /**
     * Executes the messenger max friends operation for this player contract.
     *
     * @return Result produced by the operation.
     */
    int messengerMaxFriends();

    /**
     * Executes the about detailed operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean aboutDetailed();

    /**
     * Executes the about stats operation for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean aboutStats();
}
