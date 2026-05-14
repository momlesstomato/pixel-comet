package com.cometproject.api.game.catalog;

import com.cometproject.api.game.catalog.types.*;
import com.cometproject.api.game.catalog.types.purchase.ICatalogPurchaseHandler;
import com.cometproject.api.utilities.Startable;

import java.util.List;
import java.util.Map;

/**
 * Defines the i catalog service contract for the catalog subsystem.
 */
public interface ICatalogService extends Startable {
    /**
     * Returns the catalog offers associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Integer, ICatalogOffer> getCatalogOffers();

    /**
     * Loads items and pages data for this catalog contract.
     */
    void loadItemsAndPages();

    /**
     * Loads gift boxes data for this catalog contract.
     */
    void loadGiftBoxes();

    /**
     * Loads clothing items data for this catalog contract.
     */
    void loadClothingItems();

    /**
     * Returns the pages for rank associated with this catalog contract.
     *
     * @param rank Rank value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<ICatalogPage> getPagesForRank(int rank);

    /**
     * Returns the catalog item by offer id associated with this catalog contract.
     *
     * @param offerId Offer id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ICatalogItem getCatalogItemByOfferId(int offerId);

    /**
     * Returns the catalog page by catalog item id associated with this catalog contract.
     *
     * @param id Id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ICatalogPage getCatalogPageByCatalogItemId(int id);

    /**
     * Returns the catalog item by item id associated with this catalog contract.
     *
     * @param itemId Item id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ICatalogItem getCatalogItemByItemId(int itemId);

    /**
     * Returns the items for page associated with this catalog contract.
     *
     * @param pageId Page id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Integer, ICatalogItem> getItemsForPage(int pageId);

    /**
     * Returns the page associated with this catalog contract.
     *
     * @param id Id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ICatalogPage getPage(int id);

    /**
     * Returns the catalog item associated with this catalog contract.
     *
     * @param catalogItemId Catalog item id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ICatalogItem getCatalogItem(int catalogItemId);

    /**
     * Executes the page exists operation for this catalog contract.
     *
     * @param id Id value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean pageExists(int id);

    /**
     * Returns the pages associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Integer, ICatalogPage> getPages();

    /**
     * Returns the purchase handler associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ICatalogPurchaseHandler getPurchaseHandler();

    /**
     * Returns the gift boxes new associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<Integer> getGiftBoxesNew();

    /**
     * Returns the gift boxes old associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<Integer> getGiftBoxesOld();

    /**
     * Returns the front page entries associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<ICatalogFrontPageEntry> getFrontPageEntries();

    /**
     * Returns the clothing items associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<String, IClothingItem> getClothingItems();

    /**
     * Returns the parent pages associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<ICatalogPage> getParentPages();
}
