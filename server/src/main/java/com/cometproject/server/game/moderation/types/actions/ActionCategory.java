package com.cometproject.server.game.moderation.types.actions;

import com.cometproject.server.storage.queries.moderation.PresetDao;
import com.google.common.collect.Lists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * Describes action category behavior for the moderation subsystem.
 */
public class ActionCategory {
    private int categoryId;
    private String categoryName;

    private List<ActionPreset> presets = Lists.newArrayList();

    /**
     * Creates a action category instance for the moderation subsystem.
     *
     * @param resultSet Result set supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public ActionCategory(ResultSet resultSet) throws SQLException {
        this.categoryId = resultSet.getInt("id");
        this.categoryName = resultSet.getString("name");

        PresetDao.getActionPresetsForCategory(this.categoryId, presets);
    }

    /**
     * Releases resources owned by this moderation component.
     */
    public void dispose() {
        this.presets.clear();
    }

    /**
     * Returns the category id for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Returns the category name for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Returns the presets for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public List<ActionPreset> getPresets() {
        return presets;
    }
}
