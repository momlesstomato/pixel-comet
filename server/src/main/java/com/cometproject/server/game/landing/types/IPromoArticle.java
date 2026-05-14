package com.cometproject.server.game.landing.types;

/**
 * Defines the i promo article contract for the Comet subsystem.
 */
public interface IPromoArticle {
    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    int getId();

    /**
     * Returns the title for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    String getTitle();

    /**
     * Returns the message for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    String getMessage();

    /**
     * Returns the button text for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    String getButtonText();

    /**
     * Returns the button link for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    String getButtonLink();

    /**
     * Returns the image path for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    String getImagePath();
}
