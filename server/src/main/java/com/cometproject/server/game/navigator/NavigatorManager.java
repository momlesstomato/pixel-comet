package com.cometproject.server.game.navigator;

import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.game.navigator.types.Category;
import com.cometproject.server.game.navigator.types.categories.NavigatorCategoryType;
import com.cometproject.server.game.navigator.types.publics.PublicRoom;
import com.cometproject.server.storage.queries.navigator.NavigatorDao;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Manages navigator runtime state for the navigator subsystem.
 */
public class NavigatorManager implements Startable {
    private final Logger LOGGER = LoggerFactory.getLogger(NavigatorManager.class.getName());
    private Map<Integer, Category> categories;
    private List<Category> userCategories;
    private Map<Integer, PublicRoom> publicRooms;
    private Set<Integer> staffPicks;
    private Set<Integer> rolePlayRooms;

    /**
     * Creates a navigator manager instance for the navigator subsystem.
     */
    public NavigatorManager() {
    }

    /**
     * Returns the instance for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public static NavigatorManager getInstance() {
        return CometBootstrap.resolve(NavigatorManager.class);
    }

    /**
     * Starts this navigator component.
     */
    @Override
    public void start() {
        this.loadCategories();
        this.loadPublicRooms();
        this.loadStaffPicks();
        this.loadRolePlayRooms();

        LOGGER.info("NavigatorManager initialized");
    }

    /**
     * Loads public rooms for this navigator contract.
     */
    public void loadPublicRooms() {
        try {
            if (this.publicRooms != null && this.publicRooms.size() != 0) {
                this.publicRooms.clear();
            }

            this.publicRooms = NavigatorDao.getPublicRooms();

        } catch (Exception e) {
            /**
             * Executes error for this navigator contract.
             *
             * @param rooms Rooms supplied by the caller.
             * @param e E supplied by the caller.
             * @return Result produced by the operation.
             */
            LOGGER.error("Error while loading public rooms", e);
        }

        LOGGER.info("Loaded " + this.publicRooms.size() + " featured rooms");
    }

    /**
     * Loads staff picks for this navigator contract.
     */
    public void loadStaffPicks() {
        try {
            if (this.staffPicks != null && this.staffPicks.size() != 0) {
                this.staffPicks.clear();
            }

            this.staffPicks = NavigatorDao.getStaffPicks();

        } catch (Exception e) {
            LOGGER.error("Error while loading staff picked rooms", e);
        }

        LOGGER.info("Loaded " + this.publicRooms.size() + " staff picks");
    }

    /**
     * Loads role play rooms for this navigator contract.
     */
    public void loadRolePlayRooms() {
        try {
            if (this.rolePlayRooms != null && this.rolePlayRooms.size() != 0) {
                this.rolePlayRooms.clear();
            }

            this.rolePlayRooms = NavigatorDao.getRPRooms();

        } catch (Exception e) {
            LOGGER.error("Error while loading roleplay picked rooms", e);
        }

        LOGGER.info("Loaded " + this.rolePlayRooms.size() + " roleplay picks");
    }

    /**
     * Loads categories for this navigator contract.
     */
    public void loadCategories() {
        try {
            if (this.categories != null && this.categories.size() != 0) {
                this.categories.clear();
            }

            if (this.userCategories == null) {
                this.userCategories = Lists.newArrayList();
            } else {
                this.userCategories.clear();
            }

            this.categories = NavigatorDao.getCategories();

            for (Category category : this.categories.values()) {
                if (category.getCategoryType() == NavigatorCategoryType.CATEGORY) {
                    this.userCategories.add(category);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error while loading navigator categories", e);
        }

        LOGGER.info("Loaded " + (this.getCategories() == null ? 0 : this.getCategories().size()) + " room categories");
    }

    /**
     * Returns the category for this navigator contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public Category getCategory(int id) {
        return this.categories.get(id);
    }

    /**
     * Indicates whether staff picked applies to this navigator contract.
     *
     * @param roomId Room identifier used by the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isStaffPicked(int roomId) {
        return this.staffPicks.contains(roomId);
    }

    /**
     * Returns the public room for this navigator contract.
     *
     * @param roomId Room identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public PublicRoom getPublicRoom(int roomId) {
        return this.publicRooms.get(roomId);
    }

    /**
     * Returns the categories for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Integer, Category> getCategories() {
        return this.categories;
    }

    /**
     * Returns the public rooms for this navigator contract.
     *
     * @param category Category supplied by the caller.
     * @return Value exposed by the contract.
     */
    public Map<Integer, PublicRoom> getPublicRooms(String category) {
        Map<Integer, PublicRoom> pRooms = new LinkedHashMap<>();

        for (PublicRoom publicRoom : this.publicRooms.values()) {
           if(publicRoom.getCategory().equals(category))
               pRooms.put(publicRoom.getRoomId(), publicRoom);

        }

        return pRooms;
    }

    /**
     * Indicates whether public room applies to this navigator contract.
     *
     * @param roomId Room identifier used by the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isPublicRoom(int roomId){
        for (PublicRoom publicRoom : this.publicRooms.values()) {
            if(publicRoom.getRoomId() == roomId)
                return true;
        }
        return false;
    }

    /**
     * Returns the staff picks for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<Integer> getStaffPicks() {
        return staffPicks;
    }

    /**
     * Returns the role play rooms for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<Integer> getRolePlayRooms() {
        return rolePlayRooms;
    }

    /**
     * Returns the user categories for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public List<Category> getUserCategories() {
        return userCategories;
    }
}
