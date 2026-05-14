
package com.cometproject.server.network.websockets.packets.outgoing.roleplay;

/**
 * Describes role play product web packet behavior for the networking subsystem.
 */
public class RolePlayProductWebPacket {
    private String handle;
    private String id;
    private String productname;
    private String itemId;
    private String cost;
    private String currency;
    private String type;
    private String image;

    /**
     * Creates a role play product web packet instance for the networking subsystem.
     *
     * @param handle Handle supplied by the caller.
     * @param id Id supplied by the caller.
     * @param productname Productname supplied by the caller.
     * @param itemId Item id supplied by the caller.
     * @param cost Cost supplied by the caller.
     * @param currency Currency supplied by the caller.
     * @param type Type supplied by the caller.
     * @param image Image supplied by the caller.
     */
    public RolePlayProductWebPacket(String handle, int id, String productname, int itemId, int cost, int currency, String type, String image) {
        this.handle = handle;
        this.id = id + "";
        this.productname = productname;
        this.itemId = itemId + "";
        this.cost = cost + "";
        this.currency = currency + "";
        this.type = type;
        this.image = image;
    }

}