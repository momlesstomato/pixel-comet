package com.cometproject.server.game.groups.items;

import com.cometproject.api.game.groups.IGroupItemService;
import com.cometproject.api.game.groups.items.IGroupBadgeItem;
import com.cometproject.server.storage.queries.groups.GroupItemDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Manages group item runtime state for the group subsystem.
 */
public class GroupItemManager implements IGroupItemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupItemManager.class.getName());
    private List<IGroupBadgeItem> bases;
    private List<IGroupBadgeItem> symbols;
    private List<IGroupBadgeItem> baseColours;
    private Map<Integer, IGroupBadgeItem> symbolColours;
    private Map<Integer, IGroupBadgeItem> backgroundColours;

    /**
     * Creates a group item manager instance for the group subsystem.
     */
    public GroupItemManager() {
        this.load();
    }

    /**
     * Executes load for this group contract.
     */
    public void load() {
        if (bases == null) {
            // If bases is null, gotta assume all the others are...
            bases = new ArrayList<>();
            symbols = new ArrayList<>();
            baseColours = new ArrayList<>();
            symbolColours = new HashMap<>();
            backgroundColours = new HashMap<>();
        } else {
            bases.clear();
            symbols.clear();
            baseColours.clear();
            symbolColours.clear();
            backgroundColours.clear();
        }

        int itemCount = GroupItemDao.loadGroupItems(bases, symbols, baseColours, symbolColours, backgroundColours);

        LOGGER.info("Loaded " + itemCount + " group items");
    }

    /**
     * Returns the bases for this group contract.
     *
     * @return Value exposed by the contract.
     */
    public List<IGroupBadgeItem> getBases() {
        return this.bases;
    }

    /**
     * Returns the symbols for this group contract.
     *
     * @return Value exposed by the contract.
     */
    public List<IGroupBadgeItem> getSymbols() {
        return this.symbols;
    }

    /**
     * Returns the base colours for this group contract.
     *
     * @return Value exposed by the contract.
     */
    public List<IGroupBadgeItem> getBaseColours() {
        return this.baseColours;
    }

    /**
     * Returns the symbol colours for this group contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Integer, IGroupBadgeItem> getSymbolColours() {
        return this.symbolColours;
    }

    /**
     * Returns the background colours for this group contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Integer, IGroupBadgeItem> getBackgroundColours() {
        return this.backgroundColours;
    }
}
