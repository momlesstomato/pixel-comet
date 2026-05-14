package com.cometproject.server.game.rooms.objects.items.types.floor.wired.base;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.WiredFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions.WiredActionRandomEffect;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions.WiredActionShowMessage;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions.custom.WiredCustomEnable;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.addons.WiredAddonUnseenEffect;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.negative.WiredNegativeConditionTriggererOnFurni;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive.WiredConditionTriggererOnFurni;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers.WiredTriggerWalksOffFurni;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers.WiredTriggerWalksOnFurni;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.items.wired.dialog.WiredTriggerMessageComposer;
import com.cometproject.server.protocol.messages.MessageComposer;
import com.cometproject.server.utilities.RandomUtil;
import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Describes wired trigger item behavior for the room subsystem.
 */
public abstract class WiredTriggerItem extends WiredFloorItem {

    /**
     * Creates a wired trigger item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredTriggerItem(RoomItemData itemData, Room room) {
        super(itemData, room);
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
        boolean wasSuccess;

        if (this.suppliesPlayer() && entity == null) {
            return false;
        }

        this.flash();

        wasSuccess = WiredTriggerItem.startExecute(entity, data, this.getItemsOnStack(), true);
        return wasSuccess;
    }

    /**
     * Executes start execute for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param data Data supplied by the caller.
     * @param items Items supplied by the caller.
     * @param requiredConditions Required conditions supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public static boolean startExecute(RoomEntity entity, Object data, List<RoomItemFloor> items, boolean requiredConditions) {
        try {
            List<WiredActionItem> wiredActions = Lists.newArrayList();
            List<WiredConditionItem> wiredConditions = Lists.newArrayList();

            // used by addons
            boolean useRandomEffect = false;
            WiredAddonUnseenEffect unseenEffectItem = null;
            boolean canExecute = true;

            for (RoomItemFloor floorItem : items) {
                if (floorItem instanceof WiredActionItem) {
                    wiredActions.add(((WiredActionItem) floorItem));
                }
                if (requiredConditions) {
                    if (floorItem instanceof WiredConditionItem) {
                        wiredConditions.add((WiredConditionItem) floorItem);
                    } else if (floorItem instanceof WiredAddonUnseenEffect && unseenEffectItem == null) {
                        unseenEffectItem = ((WiredAddonUnseenEffect) floorItem);
                    } else if (floorItem instanceof WiredActionRandomEffect) {
                        useRandomEffect = true;
                    }
                }
            }

            if (unseenEffectItem != null && unseenEffectItem.getSeenEffects().size() >= wiredActions.size()) {
                unseenEffectItem.getSeenEffects().clear();
            }

            final Map<String, AtomicBoolean> completedConditions = new HashMap<>();


            for (WiredConditionItem conditionItem : wiredConditions) {
                conditionItem.flash();

                if (conditionItem instanceof WiredConditionTriggererOnFurni || conditionItem instanceof WiredNegativeConditionTriggererOnFurni) {
                    if (!completedConditions.containsKey(conditionItem.getClass() + "")) {
                        completedConditions.put(conditionItem.getClass() + "", new AtomicBoolean(false));
                    }

                    if (conditionItem.evaluate(entity, data)) {
                        completedConditions.get(conditionItem.getClass() + "").set(true);
                    }

                } else {
                    completedConditions.put(conditionItem.getClass() + "" + conditionItem.getVirtualId(), new AtomicBoolean(false));

                    if (conditionItem.evaluate(entity, data)) {
                        completedConditions.get(conditionItem.getClass() + "" + conditionItem.getVirtualId()).set(true);
                    }
                }

            }

            for (AtomicBoolean conditionState : completedConditions.values()) {
                if (!conditionState.get()) {
                    canExecute = false;
                }
            }

            // tell the trigger that the item can execute, but hasn't executed just yet!
            // (just incase you wanna cancel the event that triggered this or do something else... who knows?!?!)
            // this.preActionTrigger(entity, data);

            // if we can perform the action, let's perform it!
            if (canExecute && wiredActions.size() >= 1) {
                // if the execution was a success, this will be set to true and returned so that the
                // event that called this wired trigger can do what it needs to do
                boolean wasSuccess = false;

                if (useRandomEffect) {
                    int itemIndex = RandomUtil.getRandomInt(0, wiredActions.size() - 1);

                    WiredActionItem actionItem = wiredActions.get(itemIndex);

                    if (actionItem != null) {

                        if (WiredTriggerItem.executeEffect(actionItem, entity, data)) {
                            wasSuccess = true;
                        }
                    }

                } else if (unseenEffectItem != null) {

                    Comparator<WiredActionItem> comparator = (x, y) -> Double.compare(y.getPosition().getZ(), x.getPosition().getZ()); // ordre décroissant
                    wiredActions.sort(comparator.reversed());// on inverse

                    for (WiredActionItem actionItem : wiredActions) {

                        if (!unseenEffectItem.getSeenEffects().contains(actionItem.getId())) {
                            unseenEffectItem.getSeenEffects().add(actionItem.getId());

                            if (WiredTriggerItem.executeEffect(actionItem, entity, data))
                                wasSuccess = true;
                            break;
                        }
                    }

                } else {
                    int limit = 4;
                    int executeActionEffectCount = 0;
                    int executeActionMsg = 0;

                    for (WiredActionItem actionItem : wiredActions) {

                        if (actionItem.requiresPlayer() && entity == null)
                            continue;

                        if (actionItem instanceof WiredActionShowMessage /*|| actionItem instanceof WiredActionShowMessageRoom*/) {
                            if (executeActionMsg >= 8) {
                                continue;
                            }

                            executeActionMsg++;
                        }

                        if (actionItem instanceof WiredCustomEnable) {
                            if (executeActionEffectCount >= limit) {
                                continue;
                            }

                            executeActionEffectCount++;
                        }

                        if (WiredTriggerItem.executeEffect(actionItem, entity, data)) {
                            wasSuccess = true;
                        }
                    }
                }

                return wasSuccess;
            }

        } catch (Exception e) {
        }
        return false;
    }

    /**
     * Returns the triggers for this room contract.
     *
     * @param room Room participating in the operation.
     * @param clazz Clazz supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static <T extends RoomItemFloor> List<T> getTriggers(Room room, Class<T> clazz) {
        final List<T> triggers = Lists.newArrayList();
        final List<Position> position = Lists.newArrayList();

        for (RoomItemFloor floorItem : room.getItems().getByClass(clazz)) {

            Position newPosition = new Position(floorItem.getPosition().getX(), floorItem.getPosition().getY());

            if (!position.contains(newPosition) || floorItem instanceof WiredTriggerWalksOffFurni || floorItem instanceof WiredTriggerWalksOnFurni) {
                position.add(newPosition);
                triggers.add((T) floorItem);
            }
        }

        position.clear();
        return triggers;
    }

    private static boolean executeEffect(WiredActionItem actionItem, RoomEntity entity, Object data) {
        actionItem.flash();
        return actionItem.evaluate(entity, data);
    }

    /**
     * Returns the dialog for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public MessageComposer getDialog() {
        return new WiredTriggerMessageComposer(this);
    }

    /**
     * Returns the incompatible actions for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public List<WiredActionItem> getIncompatibleActions() {
        // create an empty list to add the incompatible actions
        List<WiredActionItem> incompatibleActions = Lists.newArrayList();

        // check whether or not this current trigger supplies a player
        if (!this.suppliesPlayer()) {
            // if it doesn't, loop through all items on current tile
            for (RoomItemFloor floorItem : this.getItemsOnStack()) {
                if (floorItem instanceof WiredActionItem) {
                    // check whether the item needs a player to perform its action
                    if (((WiredActionItem) floorItem).requiresPlayer()) {
                        // if it does, add it to the incompatible actions list!
                        incompatibleActions.add(((WiredActionItem) floorItem));
                    }
                }
            }
        }

        return incompatibleActions;
    }

    /**
     * Executes supplies player for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public abstract boolean suppliesPlayer();
}
