package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive.custom;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredConditionItem;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.storage.api.data.currency.CurrencyUseCases;
import com.cometproject.storage.api.services.ICurrencyService;


/**
 * Describes wired condition custom has diamonds behavior for the room subsystem.
 */
public class WiredConditionCustomHasDiamonds extends WiredConditionItem {
    public static int PARAM_DIAMONDS_ID = 0;

    /**
     * Creates a wired condition custom has diamonds instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredConditionCustomHasDiamonds(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Returns the interface for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getInterface() {
        return 12;
    }

    /**
     * Executes evaluate for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param data Data supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean evaluate(RoomEntity entity, Object data) {
        if (entity == null) return false;

        if (this.getWiredData().getParams().size() != 1) {
            return false;
        }

        final int diamonds = this.getWiredData().getParams().get(PARAM_DIAMONDS_ID);
        boolean hasDiamonds = false;

        PlayerEntity playerEntity = (PlayerEntity) entity;

        if (playerEntity.getPlayer().getData().getCurrencyBalance(currencyCodeForUseCase(CurrencyUseCases.WIRED_CONDITION_SECONDARY)) >= diamonds) {
            hasDiamonds = true;
        }


        return isNegative != hasDiamonds;
    }

    private static String currencyCodeForUseCase(final String useCase) {
        return CometBootstrap.resolve(ICurrencyService.class).currencyCodeForUseCase(useCase);
    }
}
