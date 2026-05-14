package com.cometproject.api.game.catalog.types;

/**
 * Defines the i catalog front page entry contract for the catalog subsystem.
 */
public interface ICatalogFrontPageEntry {

    /**
     * Returns the id associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Returns the caption associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getCaption();

    /**
     * Returns the image associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getImage();

    /**
     * Returns the page link associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getPageLink();

    /**
     * Returns the page id associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getPageId();

}
