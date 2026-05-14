package com.cometproject.server.game.rooms.objects.items.types.floor.wired.data;

import com.cometproject.server.game.rooms.objects.items.types.floor.wired.WiredItemSnapshot;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;


/**
 * Carries wired item data data for the room subsystem.
 */
public class WiredItemData {
    private int selectionType = 0;
    private List<Long> selectedIds;
    private String text;
    private Map<Integer, Integer> params;
    private Map<Long, WiredItemSnapshot> snapshots;

    /**
     * Creates a wired item data instance for the room subsystem.
     *
     * @param selectionType Selection type supplied by the caller.
     * @param selectedIds Selected ids supplied by the caller.
     * @param text Text supplied by the caller.
     * @param params Params supplied by the caller.
     * @param snapshots Snapshots supplied by the caller.
     */
    public WiredItemData(int selectionType, List<Long> selectedIds, String text, Map<Integer, Integer> params, Map<Long, WiredItemSnapshot> snapshots) {
        this.selectionType = selectionType;
        this.selectedIds = selectedIds;
        this.text = StringUtils.abbreviate(text, 100);
        this.params = params;
        this.snapshots = snapshots;
    }

    /**
     * Creates a wired item data instance for the room subsystem.
     */
    public WiredItemData() {
        this.selectionType = 0;
        this.selectedIds = Lists.newArrayList();
        this.text = "";
        this.params = Maps.newHashMap();
        this.snapshots = Maps.newHashMap();
    }

    /**
     * Executes select item for this room contract.
     *
     * @param itemId Item id supplied by the caller.
     */
    public void selectItem(long itemId) {
        this.selectedIds.add(itemId);
    }

    /**
     * Returns the selection type for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getSelectionType() {
        return selectionType;
    }

    /**
     * Updates the selection type for this room contract.
     *
     * @param selectionType Selection type supplied by the caller.
     */
    public void setSelectionType(int selectionType) {
        this.selectionType = selectionType;
    }

    /**
     * Returns the selected ids for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public List<Long> getSelectedIds() {
        return selectedIds;
    }

    /**
     * Returns the text for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getText() {
        return text;
    }

    /**
     * Updates the text for this room contract.
     *
     * @param text Text supplied by the caller.
     */
    public void setText(String text) {
        this.text = StringUtils.abbreviate(text, 100);
    }

    /**
     * Returns the params for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Integer, Integer> getParams() {
        return params;
    }

    /**
     * Updates the params for this room contract.
     *
     * @param params Params supplied by the caller.
     */
    public void setParams(Map<Integer, Integer> params) {
        this.params = params;
    }

    /**
     * Returns the snapshots for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Long, WiredItemSnapshot> getSnapshots() {
        return snapshots;
    }

    /**
     * Updates the snapshots for this room contract.
     *
     * @param snapshots Snapshots supplied by the caller.
     */
    public void setSnapshots(Map<Long, WiredItemSnapshot> snapshots) {
        this.snapshots = snapshots;
    }
}
