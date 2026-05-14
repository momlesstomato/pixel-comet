package com.cometproject.server.game.rooms.types.mapping;

/**
 * Describes room tile status behavior for the room subsystem.
 */
public class RoomTileStatus {
    private RoomTileStatusType statusType;
    private int effectId;

    private int positionX;
    private int positionY;
    private int rotation;

    private double interactionHeight;

    /**
     * Creates a room tile status instance for the room subsystem.
     *
     * @param type Type supplied by the caller.
     * @param effectId Effect id supplied by the caller.
     * @param positionX Position x supplied by the caller.
     * @param positionY Position y supplied by the caller.
     * @param rotation Rotation supplied by the caller.
     * @param interactionHeight Interaction height supplied by the caller.
     */
    public RoomTileStatus(RoomTileStatusType type, int effectId, int positionX, int positionY, int rotation, double interactionHeight) {
        this.statusType = type;
        this.effectId = effectId;

        this.positionX = positionX;
        this.positionY = positionY;
        this.rotation = rotation;

        this.interactionHeight = interactionHeight;
    }

    /**
     * Returns the interaction height for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public double getInteractionHeight() {
        return this.interactionHeight;
    }
}
