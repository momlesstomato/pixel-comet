package com.cometproject.storage.api;

import com.cometproject.storage.api.repositories.*;

/**
 * Describes storage context behavior for the storage subsystem.
 */
public final class StorageContext {
    private static StorageContext storageContext;

    private IGroupRepository groupRepository;
    private IGroupMemberRepository groupMemberRepository;
    private IGroupForumRepository groupForumRepository;

    private IRoomItemRepository roomItemRepository;
    private IRoomRepository roomRepository;
    private IInventoryRepository inventoryRepository;
    private IRewardRepository rewardRepository;
    private IPhotoRepository photoRepository;
    private IPlayerRepository playerRepository;
    private ICurrencyRepository currencyRepository;

    /**
     * Returns the group repository for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public IGroupRepository getGroupRepository() {
        return groupRepository;
    }

    /**
     * Updates the group repository for this storage contract.
     *
     * @param groupRepository Group repository supplied by the caller.
     */
    public void setGroupRepository(IGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * Returns the group member repository for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public IGroupMemberRepository getGroupMemberRepository() {
        return groupMemberRepository;
    }

    /**
     * Updates the group member repository for this storage contract.
     *
     * @param groupMemberRepository Group member repository supplied by the caller.
     */
    public void setGroupMemberRepository(IGroupMemberRepository groupMemberRepository) {
        this.groupMemberRepository = groupMemberRepository;
    }

    /**
     * Updates the current context for this storage contract.
     *
     * @param ctx Netty channel context for the current operation.
     */
    public static void setCurrentContext(StorageContext ctx) {
        storageContext = ctx;
    }

    /**
     * Returns the current context for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public static StorageContext getCurrentContext() {
        return storageContext;
    }

    /**
     * Returns the group forum repository for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public IGroupForumRepository getGroupForumRepository() {
        return groupForumRepository;
    }

    /**
     * Updates the group forum repository for this storage contract.
     *
     * @param groupForumRepository Group forum repository supplied by the caller.
     */
    public void setGroupForumRepository(IGroupForumRepository groupForumRepository) {
        this.groupForumRepository = groupForumRepository;
    }

    /**
     * Returns the room item repository for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public IRoomItemRepository getRoomItemRepository() {
        return roomItemRepository;
    }

    /**
     * Updates the room item repository for this storage contract.
     *
     * @param roomItemRepository Room item repository supplied by the caller.
     */
    public void setRoomItemRepository(IRoomItemRepository roomItemRepository) {
        this.roomItemRepository = roomItemRepository;
    }

    /**
     * Returns the inventory repository for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public IInventoryRepository getInventoryRepository() {
        return inventoryRepository;
    }

    /**
     * Updates the inventory repository for this storage contract.
     *
     * @param inventoryRepository Inventory repository supplied by the caller.
     */
    public void setInventoryRepository(IInventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Returns the room repository for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public IRoomRepository getRoomRepository() {
        return roomRepository;
    }

    /**
     * Updates the room repository for this storage contract.
     *
     * @param roomRepository Room repository supplied by the caller.
     */
    public void setRoomRepository(IRoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * Returns the reward repository for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public IRewardRepository getRewardRepository() {
        return rewardRepository;
    }

    /**
     * Updates the reward repository for this storage contract.
     *
     * @param rewardRepository Reward repository supplied by the caller.
     */
    public void setRewardRepository(IRewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    /**
     * Returns the photo repository for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public IPhotoRepository getPhotoRepository() {
        return photoRepository;
    }

    /**
     * Updates the photo repository for this storage contract.
     *
     * @param photoRepository Photo repository supplied by the caller.
     */
    public void setPhotoRepository(IPhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    /**
     * Returns the repository responsible for player account persistence.
     *
     * @return the configured player repository.
     */
    public IPlayerRepository getPlayerRepository() {
        return playerRepository;
    }

    /**
     * Sets the repository responsible for player account persistence.
     *
     * @param playerRepository the player repository implementation.
     */
    public void setPlayerRepository(IPlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Returns the repository responsible for currency inventory persistence.
     *
     * @return the configured currency repository.
     */
    public ICurrencyRepository getCurrencyRepository() {
        return currencyRepository;
    }

    /**
     * Sets the repository responsible for currency inventory persistence.
     *
     * @param currencyRepository the currency repository implementation.
     */
    public void setCurrencyRepository(ICurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }
}
