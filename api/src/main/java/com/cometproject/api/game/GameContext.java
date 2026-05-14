package com.cometproject.api.game;

import com.cometproject.api.game.catalog.ICatalogService;
import com.cometproject.api.game.furniture.IFurnitureService;
import com.cometproject.api.game.groups.IGroupService;
import com.cometproject.api.game.players.IPlayerService;
import com.cometproject.api.game.rooms.IRoomService;
import com.cometproject.api.game.rooms.models.IRoomModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Describes game context behavior for the Comet subsystem.
 */
public class GameContext {
    private static GameContext gameContext;

    private ICatalogService catalogService;
    private IFurnitureService furnitureService;
    private IGroupService groupService;
    private IPlayerService playerService;
    private IRoomService roomService;
    private IRoomModelService roomModelService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GameContext.class);

    /**
     * Returns the catalog service for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public ICatalogService getCatalogService() {
        return catalogService;
    }

    /**
     * Updates the catalog service value for this game domain contract.
     *
     * @param catalogService Catalog service value supplied by the caller.
     */
    public void setCatalogService(ICatalogService catalogService) {
        LOGGER.info("CatalogService initialised, " + catalogService.getClass().getName());

        this.catalogService = catalogService;
    }

    /**
     * Returns the furniture service for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public IFurnitureService getFurnitureService() {
        return this.furnitureService;
    }

    /**
     * Updates the furniture service value for this game domain contract.
     *
     * @param furnitureService Furniture service value supplied by the caller.
     */
    public void setFurnitureService(IFurnitureService furnitureService) {
        LOGGER.info("FurnitureService initialised, " + furnitureService.getClass().getName());

        this.furnitureService = furnitureService;
    }

    /**
     * Returns the group service for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public IGroupService getGroupService() {
         return this.groupService;
    }

    /**
     * Updates the group service value for this game domain contract.
     *
     * @param groupService Group service value supplied by the caller.
     */
    public void setGroupService(IGroupService groupService) {
        LOGGER.info("GroupService initialised, " + groupService.getClass().getName());

        this.groupService = groupService;
    }

    /**
     * Returns the player service for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public IPlayerService getPlayerService() {
        return this.playerService;
    }

    /**
     * Updates the player service for this Comet contract.
     *
     * @param playerService Player service supplied by the caller.
     */
    public void setPlayerService(IPlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Returns the current associated with this game domain contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    public static GameContext getCurrent() {
        if(gameContext == null) {
            LOGGER.info("GameContext not configured");
            System.exit(0);
        }

        return gameContext;
    }

    /**
     * Updates the current for this Comet contract.
     *
     * @param instance Instance supplied by the caller.
     */
    public static void setCurrent(GameContext instance) {
        GameContext.gameContext = instance;
    }

    /**
     * Returns the room service for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public IRoomService getRoomService() {
        return roomService;
    }

    /**
     * Updates the room service value for this game domain contract.
     *
     * @param roomService Room service value supplied by the caller.
     */
    public void setRoomService(IRoomService roomService) {
        LOGGER.info("RoomService initialised, " + roomService.getClass().getName());

        this.roomService = roomService;
    }

    /**
     * Returns the room model service for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public IRoomModelService getRoomModelService() {
        return roomModelService;
    }

    /**
     * Updates the room model service value for this game domain contract.
     *
     * @param roomModelService Room model service value supplied by the caller.
     */
    public void setRoomModelService(IRoomModelService roomModelService) {
        LOGGER.info("RoomModelService initialised, " + roomModelService.getClass().getName());

        this.roomModelService = roomModelService;
    }
}
