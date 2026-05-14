package com.cometproject.server.game.rooms.objects.items.types.floor.wired.data;

import com.cometproject.server.game.rooms.objects.items.types.floor.wired.WiredItemSnapshot;

import java.util.List;
import java.util.Map;


/**
 * Carries wired action item data data for the room subsystem.
 */
public class WiredActionItemData extends WiredItemData {
    private int delay;

    /**
     * Creates a wired action item data instance for the room subsystem.
     *
     * @param selectionType Selection type supplied by the caller.
     * @param selectedIds Selected ids supplied by the caller.
     * @param text Text supplied by the caller.
     * @param params Params supplied by the caller.
     * @param snapshots Snapshots supplied by the caller.
     * @param delay Delay supplied by the caller.
     */
    public WiredActionItemData(int selectionType, List<Long> selectedIds, String text, Map<Integer, Integer> params, Map<Long, WiredItemSnapshot> snapshots, int delay) {
        super(selectionType, selectedIds, text, params, snapshots);
        this.delay = delay;
    }

    /**
     * Creates a wired action item data instance for the room subsystem.
     *
     * @param delay Delay supplied by the caller.
     */
    public WiredActionItemData(int delay) {
        this.delay = delay;
    }

    /**
     * Creates a wired action item data instance for the room subsystem.
     */
    public WiredActionItemData() {
        super();
        this.delay = 0;
    }

    /**
     * Returns the delay for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Updates the delay for this room contract.
     *
     * @param delay Delay supplied by the caller.
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }
}