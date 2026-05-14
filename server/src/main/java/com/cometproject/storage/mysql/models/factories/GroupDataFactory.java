package com.cometproject.storage.mysql.models.factories;

import com.cometproject.api.game.groups.types.GroupType;
import com.cometproject.api.game.groups.types.IGroupData;
import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.storage.mysql.models.GroupData;

/**
 * Creates group data instances for the MySQL storage subsystem.
 */
public class GroupDataFactory {

    /**
     * Executes create for this MySQL storage contract.
     *
     * @param id Id supplied by the caller.
     * @param title Title supplied by the caller.
     * @param description Description supplied by the caller.
     * @param badge Badge supplied by the caller.
     * @param ownerId Owner id supplied by the caller.
     * @param ownerName Owner name supplied by the caller.
     * @param roomId Room identifier used by the operation.
     * @param created Created supplied by the caller.
     * @param type Type supplied by the caller.
     * @param colourA Colour a supplied by the caller.
     * @param colourB Colour b supplied by the caller.
     * @param canMembersDecorate Can members decorate supplied by the caller.
     * @param hasForum Has forum supplied by the caller.
     * @param playerAvatar Player avatar supplied by the caller.
     * @return Value exposed by the contract.
     */
    public IGroupData create(int id, String title, String description, String badge, int ownerId, String ownerName,
                             int roomId, int created, GroupType type, int colourA, int colourB,
                             boolean canMembersDecorate, boolean hasForum, PlayerAvatar playerAvatar) {
        return new GroupData(id, title, description, badge, ownerId, ownerName, roomId, created, type, colourA, colourB,
                canMembersDecorate, hasForum, playerAvatar);
    }

    /**
     * Executes create for this MySQL storage contract.
     *
     * @param title Title supplied by the caller.
     * @param description Description supplied by the caller.
     * @param badge Badge supplied by the caller.
     * @param ownerId Owner id supplied by the caller.
     * @param ownerName Owner name supplied by the caller.
     * @param roomId Room identifier used by the operation.
     * @param colourA Colour a supplied by the caller.
     * @param colourB Colour b supplied by the caller.
     * @param playerAvatar Player avatar supplied by the caller.
     * @return Value exposed by the contract.
     */
    public IGroupData create(String title, String description, String badge, int ownerId,
                             String ownerName, int roomId, int colourA, int colourB, PlayerAvatar playerAvatar) {
        return new GroupData(-1, title, description, badge, ownerId, ownerName, roomId, (int) (System.currentTimeMillis() / 1000), GroupType.REGULAR, colourA, colourB, false, false, playerAvatar);
    }
}
