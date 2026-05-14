package com.cometproject.server.game.permissions;

/**
 * Describes permission behavior for the permission subsystem.
 */
public class Permission {

    public final String key;
    public final PermissionSetting setting;

    /**
     * Creates a permission instance for the permission subsystem.
     *
     * @param key Key supplied by the caller.
     * @param setting Setting supplied by the caller.
     */
    public Permission(String key, PermissionSetting setting) {
        this.key = key;
        this.setting = setting;
    }
}
