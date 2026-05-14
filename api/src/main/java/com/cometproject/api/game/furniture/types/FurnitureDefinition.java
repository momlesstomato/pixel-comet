package com.cometproject.api.game.furniture.types;

/**
 * Defines the furniture definition contract for the furniture subsystem.
 */
public interface FurnitureDefinition {

   /**
    * Indicates whether ad furni is enabled for this furniture contract.
    *
    * @return True when the contract condition is satisfied; otherwise false.
    */
   boolean isAdFurni();

   /**
    * Indicates whether room decor is enabled for this furniture contract.
    *
    * @return True when the contract condition is satisfied; otherwise false.
    */
   boolean isRoomDecor();

   /**
    * Indicates whether teleporter is enabled for this furniture contract.
    *
    * @return True when the contract condition is satisfied; otherwise false.
    */
   boolean isTeleporter();

   /**
    * Indicates whether song is enabled for this furniture contract.
    *
    * @return True when the contract condition is satisfied; otherwise false.
    */
   boolean isSong();

   /**
    * Returns the id associated with this furniture contract.
    *
    * @return Requested value, or the implementation-defined missing value documented by the contract.
    */
   int getId();

   /**
    * Returns the public name associated with this furniture contract.
    *
    * @return Requested value, or the implementation-defined missing value documented by the contract.
    */
   String getPublicName();

   /**
    * Returns the item name associated with this furniture contract.
    *
    * @return Requested value, or the implementation-defined missing value documented by the contract.
    */
   String getItemName();

   /**
    * Returns the type associated with this furniture contract.
    *
    * @return Requested value, or the implementation-defined missing value documented by the contract.
    */
   String getType();

   /**
    * Returns the width associated with this furniture contract.
    *
    * @return Requested value, or the implementation-defined missing value documented by the contract.
    */
   int getWidth();

   /**
    * Returns the height associated with this furniture contract.
    *
    * @return Requested value, or the implementation-defined missing value documented by the contract.
    */
   double getHeight();

   /**
    * Returns the sprite id associated with this furniture contract.
    *
    * @return Requested value, or the implementation-defined missing value documented by the contract.
    */
   int getSpriteId();

   /**
    * Returns the length associated with this furniture contract.
    *
    * @return Requested value, or the implementation-defined missing value documented by the contract.
    */
   int getLength();

   /**
    * Returns the interaction associated with this furniture contract.
    *
    * @return Requested value, or the implementation-defined missing value documented by the contract.
    */
   String getInteraction();

   /**
    * Returns the interaction cycle count associated with this furniture contract.
    *
    * @return Requested value, or the implementation-defined missing value documented by the contract.
    */
   int getInteractionCycleCount();

   /**
    * Returns the effect id associated with this furniture contract.
    *
    * @return Requested value, or the implementation-defined missing value documented by the contract.
    */
   int getEffectId();

   /**
    * Returns the vending ids associated with this furniture contract.
    *
    * @return Requested value, or the implementation-defined missing value documented by the contract.
    */
   String[] getVendingIds();

   /**
    * Returns the offer id associated with this furniture contract.
    *
    * @return Requested value, or the implementation-defined missing value documented by the contract.
    */
   int getOfferId();

   /**
    * Indicates whether this furniture contract can stack.
    *
    * @return True when the contract condition is satisfied; otherwise false.
    */
   boolean canStack();

   /**
    * Indicates whether this furniture contract can sit.
    *
    * @return True when the contract condition is satisfied; otherwise false.
    */
   boolean canSit();

   /**
    * Indicates whether this furniture contract can walk.
    *
    * @return True when the contract condition is satisfied; otherwise false.
    */
   boolean canWalk();

   /**
    * Indicates whether this furniture contract can trade.
    *
    * @return True when the contract condition is satisfied; otherwise false.
    */
   boolean canTrade();

   /**
    * Indicates whether this furniture contract can recycle.
    *
    * @return True when the contract condition is satisfied; otherwise false.
    */
   boolean canRecycle();

   /**
    * Indicates whether this furniture contract can market.
    *
    * @return True when the contract condition is satisfied; otherwise false.
    */
   boolean canMarket();

   /**
    * Indicates whether this furniture contract can gift.
    *
    * @return True when the contract condition is satisfied; otherwise false.
    */
   boolean canGift();

   /**
    * Indicates whether this furniture contract can inventory stack.
    *
    * @return True when the contract condition is satisfied; otherwise false.
    */
   boolean canInventoryStack();

   /**
    * Returns the variable heights associated with this furniture contract.
    *
    * @return Requested value, or the implementation-defined missing value documented by the contract.
    */
   Double[] getVariableHeights();

   /**
    * Executes the requires rights operation for this furniture contract.
    *
    * @return True when the contract condition is satisfied; otherwise false.
    */
   boolean requiresRights();

   /**
    * Indicates whether wired is enabled for this furniture contract.
    *
    * @return True when the contract condition is satisfied; otherwise false.
    */
   boolean isWired();

   /**
    * Returns the song id associated with this furniture contract.
    *
    * @return Requested value, or the implementation-defined missing value documented by the contract.
    */
   int getSongId();

    /**
     * Returns the item type associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ItemType getItemType();
}
