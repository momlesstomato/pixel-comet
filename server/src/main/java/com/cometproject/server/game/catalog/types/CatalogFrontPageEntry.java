package com.cometproject.server.game.catalog.types;

import com.cometproject.api.game.catalog.types.ICatalogFrontPageEntry;

/**
 * Describes catalog front page entry behavior for the catalog subsystem.
 */
public class CatalogFrontPageEntry implements ICatalogFrontPageEntry {
    private final int id;
    private final String caption;
    private final String image;
    private final String pageLink;
    private final int pageId;

    /**
     * Creates a catalog front page entry instance for the catalog subsystem.
     *
     * @param id Id supplied by the caller.
     * @param caption Caption supplied by the caller.
     * @param image Image supplied by the caller.
     * @param pageLink Page link supplied by the caller.
     * @param pageId Page id supplied by the caller.
     */
    public CatalogFrontPageEntry(int id, String caption, String image, String pageLink, int pageId) {
        this.id = id;
        this.caption = caption;
        this.image = image;
        this.pageLink = pageLink;
        this.pageId = pageId;
    }

    /**
     * Returns the id for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Returns the caption for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getCaption() {
        return caption;
    }

    /**
     * Returns the image for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getImage() {
        return image;
    }

    /**
     * Returns the page link for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPageLink() {
        return pageLink;
    }

    /**
     * Returns the page id for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getPageId() {
        return pageId;
    }
}
