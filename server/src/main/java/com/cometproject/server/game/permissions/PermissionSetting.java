package com.cometproject.server.game.permissions;

/**
 * Enumerates permission setting values used by the permission subsystem.
 */
public enum PermissionSetting {

    DISALLOWED,


    ALLOWED,


    ROOM_OWNER;

    /**
     * Executes from string for this permission contract.
     *
     * @param value Value supplied by the caller.
     * @return Result produced by the operation.
     */
    public static PermissionSetting fromString(String value) {
        switch (value) {
            case "1":
                return ALLOWED;
            case "2":
                return ROOM_OWNER;

        }

        return DISALLOWED;
    }
}
