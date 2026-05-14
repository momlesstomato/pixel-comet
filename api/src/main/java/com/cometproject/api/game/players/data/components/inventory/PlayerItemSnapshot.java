package com.cometproject.api.game.players.data.components.inventory;

/**
 * Defines the player item snapshot contract for the player subsystem.
 */
public interface PlayerItemSnapshot {
   /**
    * Returns the id associated with this player contract.
    *
    * @return Requested value, or the implementation-defined missing value documented by the contract.
    */
   long getId();

   /**
    * Returns the base item id associated with this player contract.
    *
    * @return Requested value, or the implementation-defined missing value documented by the contract.
    */
   int getBaseItemId();

   /**
    * Returns the extra data associated with this player contract.
    *
    * @return Requested value, or the implementation-defined missing value documented by the contract.
    */
   String getExtraData();
}
