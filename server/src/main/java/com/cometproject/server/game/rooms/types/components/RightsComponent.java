package com.cometproject.server.game.rooms.types.components;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.game.groups.types.IGroup;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.types.RoomBan;
import com.cometproject.server.game.rooms.types.components.types.RoomMute;
import com.cometproject.server.storage.queries.rooms.RightsDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Owns rights behavior inside the room processing subsystem.
 */
public class RightsComponent {
    private Room room;

    private List<Integer> rights;
    private Map<Integer, RoomBan> bannedPlayers;
    private Map<Integer, RoomMute> mutedPlayers;

    /**
     * Creates a rights component instance for the room processing subsystem.
     *
     * @param room Room participating in the operation.
     */
    public RightsComponent(Room room) {
        this.room = room;

        try {
            if (room.getCachedData() != null) {
                this.rights = room.getCachedData().getRights();
            } else {
                this.rights = RightsDao.getRightsByRoomId(room.getId());
            }
        } catch (Exception e) {
            this.rights = new CopyOnWriteArrayList<>();
            this.room.LOGGER.error("Error while loading room rights", e);
        }

        this.bannedPlayers = RightsDao.getRoomBansByRoomId(this.room.getId());
        this.mutedPlayers = new ConcurrentHashMap<>();
    }

    /**
     * Releases resources owned by this room processing component.
     */
    public void dispose() {
        this.rights.clear();
        this.bannedPlayers.clear();
    }

    /**
     * Indicates whether this room processing contract has rights.
     *
     * @param playerId Player identifier used by the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasRights(int playerId) {
        return this.hasRights(playerId, true);
    }

    /**
     * Indicates whether this room processing contract has rights.
     *
     * @param playerId Player identifier used by the operation.
     * @param includeGroupCheck Include group check supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasRights(int playerId, boolean includeGroupCheck) {
        final IGroup group = this.getRoom().getGroup();

        if (group != null && group.getData() != null && group.getMembers() != null && group.getMembers().getAll() != null) {
            if (group.getData().canMembersDecorate() && group.getMembers().getAll().containsKey(playerId)) {
                return true;
            }

            if (group.getMembers().getAdministrators().contains(playerId)) {
                return true;
            }
        }

        return this.room.getData().getOwnerId() == playerId || this.rights.contains(playerId);
    }

    /**
     * Indicates whether this room processing contract can place furniture.
     *
     * @param playerId Player identifier used by the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canPlaceFurniture(final int playerId) {
        final IGroup group = this.getRoom().getGroup();

        if (group != null && group.getData() != null && group.getMembers() != null && group.getMembers().getAll() != null) {
            if (group.getData().canMembersDecorate() && group.getMembers().getAll().containsKey(playerId)) {
                return true;
            }

            if (group.getMembers().getAdministrators().contains(playerId)) {
                return true;
            }
        }

        if (this.hasRights(playerId, false) && CometSettings.playerRightsItemPlacement) {
            return true;
        }

        return this.room.getData().getOwnerId() == playerId;
    }

    /**
     * Removes rights from this room processing contract.
     *
     * @param playerId Player identifier used by the operation.
     */
    public void removeRights(int playerId) {
        if (this.rights.contains(playerId)) {
            this.rights.remove(rights.indexOf(playerId));
            RightsDao.delete(playerId, room.getId());
        }
    }

    /**
     * Adds rights to this room processing contract.
     *
     * @param playerId Player identifier used by the operation.
     */
    public void addRights(int playerId) {
        this.rights.add(playerId);
        RightsDao.add(playerId, this.room.getId());
    }

    /**
     * Adds ban to this room processing contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param playerName Player name supplied by the caller.
     * @param expireTimestamp Expire timestamp supplied by the caller.
     */
    public void addBan(int playerId, String playerName, int expireTimestamp) {
        this.bannedPlayers.put(playerId, new RoomBan(playerId, playerName, expireTimestamp));
        RightsDao.addRoomBan(playerId, this.room.getId(), expireTimestamp);
    }

    /**
     * Adds mute to this room processing contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param minutes Minutes supplied by the caller.
     */
    public void addMute(int playerId, int minutes) {
        this.mutedPlayers.put(playerId, new RoomMute(playerId, (minutes * 60) * 2));
    }

    /**
     * Indicates whether this room processing contract has ban.
     *
     * @param userId User id supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasBan(int userId) {
        return this.bannedPlayers.containsKey(userId);
    }

    /**
     * Removes ban from this room processing contract.
     *
     * @param playerId Player identifier used by the operation.
     */
    public void removeBan(int playerId) {
        this.bannedPlayers.remove(playerId);

        // delete it from the db.
        RightsDao.deleteRoomBan(playerId, this.room.getId());
    }

    /**
     * Indicates whether this room processing contract has mute.
     *
     * @param playerId Player identifier used by the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasMute(int playerId) {
        return this.mutedPlayers.containsKey(playerId);
    }

    /**
     * Returns the mute time for this room processing contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public int getMuteTime(int playerId) {
        if (this.hasMute(playerId)) {
            return this.mutedPlayers.get(playerId).getTicksLeft() / 2;
        }

        return 0;
    }

    /**
     * Executes tick for this room processing contract.
     */
    public void tick() {
        List<RoomBan> bansToRemove = new ArrayList<>();
        List<RoomMute> mutesToRemove = new ArrayList<>();

        for (RoomBan ban : this.bannedPlayers.values()) {
            if (ban.getExpireTimestamp() <= Comet.getTime() && !ban.isPermanent()) {
                bansToRemove.add(ban);
            }
        }

        for (RoomMute mute : this.mutedPlayers.values()) {
            if (mute.getTicksLeft() <= 0) {
                mutesToRemove.add(mute);
            }

            mute.decreaseTicks();
        }


        for (RoomBan ban : bansToRemove) {
            this.bannedPlayers.remove(ban.getPlayerId());
        }

        for (RoomMute mute : mutesToRemove) {
            this.mutedPlayers.remove(mute.getPlayerId());
        }

        bansToRemove.clear();
        mutesToRemove.clear();
    }

    /**
     * Returns the banned players for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Integer, RoomBan> getBannedPlayers() {
        return this.bannedPlayers;
    }

    /**
     * Returns the all for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public List<Integer> getAll() {
        return this.rights;
    }

    /**
     * Returns the room for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public Room getRoom() {
        return this.room;
    }
}
