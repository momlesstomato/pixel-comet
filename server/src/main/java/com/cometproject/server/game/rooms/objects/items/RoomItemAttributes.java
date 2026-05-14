package com.cometproject.server.game.rooms.objects.items;

/**
 * Defines the room item attributes contract for the room subsystem.
 */
public interface RoomItemAttributes {
    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    int getId();

    /**
     * Returns the item id for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    int getItemId();

    /**
     * Returns the owner for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    int getOwner();

    /**
     * Returns the x for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    int getX();

    /**
     * Returns the y for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    int getY();

    /**
     * Returns the rotation for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    int getRotation();
}