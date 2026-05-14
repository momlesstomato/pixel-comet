package com.cometproject.api.game.catalog.types;

import java.util.List;
import java.util.Map;

/**
 * Defines the i catalog page contract for the catalog subsystem.
 */
public interface ICatalogPage {
    /**
     * Returns the offer size associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getOfferSize();

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
     * Returns the icon associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getIcon();

    /**
     * Returns the min rank associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getMinRank();

    /**
     * Returns the template associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getTemplate();

    /**
     * Returns the parent id associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getParentId();

    /**
     * Indicates whether enabled is enabled for this catalog contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isEnabled();

    /**
     * Returns the items associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Integer, ICatalogItem> getItems();

    /**
     * Returns the images associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<String> getImages();

    /**
     * Returns the texts associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<String> getTexts();

    /**
     * Returns the link name associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getLinkName();

    /**
     * Returns the extra data associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getExtraData();

    /**
     * Returns the type associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    CatalogPageType getType();

    /**
     * Returns the order associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getOrder();

    /**
     * Returns the children associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<ICatalogPage> getChildren();
}
