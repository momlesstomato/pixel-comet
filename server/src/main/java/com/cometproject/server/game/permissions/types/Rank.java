package com.cometproject.server.game.permissions.types;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.cometproject.api.game.players.data.components.permissions.PlayerRank;
import com.cometproject.server.game.permissions.Permission;
import com.cometproject.server.game.permissions.PermissionSetting;

/**
 * Describes rank behavior for the permission subsystem.
 */
public class Rank implements PlayerRank {
    private final int id;
    private String name;

    private boolean floodBypass;
    private int floodTime;

    private boolean disconnectable;
    private boolean modTool;
    private boolean alfaTool;
    private boolean bannable;

    private boolean roomKickable;
    private boolean roomFullControl;
    private boolean roomMuteBypass;
    private boolean roomFilterBypass;
    private boolean roomIgnorable;
    private boolean roomEnterFull;
    private boolean roomEnterLocked;
    private boolean roomStaffPick;
    private boolean roomSeeWhispers;

    private boolean messengerStaffChat;
    private boolean messengerModsChat;
    private boolean messengerLogChat;
    private boolean messengerAlfaChat;
    private int messengerMaxFriends;

    private boolean aboutDetailed;
    private boolean aboutStats;
    private boolean loginNotif;

    private final Map<String, Permission> permissions;
    private final Map<String, String> variables;

    /**
     * Creates a rank instance for the permission subsystem.
     *
     * @param set Set supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public Rank(ResultSet set) throws SQLException {
        this.permissions = new HashMap<>();
        this.variables = new HashMap<>();
        this.id = set.getInt("id");

        this.load(set);
    }

    /**
     * Executes load for this permission contract.
     *
     * @param set Set supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public void load(ResultSet set) throws SQLException {
        ResultSetMetaData meta = set.getMetaData();
        this.name = set.getString("name");

        this.floodBypass = set.getString("flood_bypass").equals("1");
        this.floodTime = set.getInt("flood_time");
        this.disconnectable = set.getString("disconnectable").equals("1");
        this.modTool = set.getString("mod_tool").equals("1");
        this.alfaTool = set.getString("alfa_tool").equals("1");
        this.bannable = set.getString("bannable").equals("");
        this.roomKickable = set.getString("room_kickable").equals("1");
        this.roomFullControl = set.getString("room_full_control").equals("1");
        this.roomMuteBypass = set.getString("room_mute_bypass").equals("1");
        this.roomFilterBypass = set.getString("room_filter_bypass").equals("1");
        this.roomIgnorable = set.getString("room_ignorable").equals("1");
        this.roomEnterFull = set.getString("room_enter_full").equals("1");
        this.roomEnterLocked = set.getString("room_enter_locked").equals("1");
        this.roomStaffPick = set.getString("room_staff_pick").equals("1");
        this.roomSeeWhispers = set.getString("room_see_whispers").equals("1");
        this.messengerStaffChat = set.getString("messenger_staff_chat").equals("1");
        this.messengerModsChat = set.getString("messenger_mod_chat").equals("1");
        this.messengerLogChat = set.getString("messenger_log_chat").equals("1");
        this.messengerAlfaChat = set.getString("messenger_alfa_chat").equals("1");
        this.messengerMaxFriends = set.getInt("messenger_max_friends");
        this.aboutDetailed = set.getString("about_detailed").equals("1");
        this.aboutStats = set.getString("about_stats").equals("1");
        this.loginNotif = set.getString("login_notif").equals("1");

        for (int i = 1; i < meta.getColumnCount() + 1; i++) {
            String columnName = meta.getColumnName(i);
            if (columnName.startsWith("cmd_") || columnName.startsWith("acc_") || columnName.contains("_command")) {
                this.permissions.put(meta.getColumnName(i), new Permission(columnName, PermissionSetting.fromString(set.getString(i))));
            } else {
                this.variables.put(meta.getColumnName(i), set.getString(i));
            }
        }
    }

    /**
     * Indicates whether this permission contract has permission.
     *
     * @param key Key supplied by the caller.
     * @param isRoomOwner Is room owner supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasPermission(String key, boolean isRoomOwner) {
        if (this.permissions.containsKey(key)) {
            Permission permission = this.permissions.get(key);

            return permission.setting == PermissionSetting.ALLOWED || permission.setting == PermissionSetting.ROOM_OWNER && isRoomOwner;

        }

        return false;
    }

    /**
     * Returns the permissions for this permission contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<String, Permission> getPermissions() {
        return permissions;
    }

    /**
     * Returns the id for this permission contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Returns the name for this permission contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Executes flood bypass for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean floodBypass() {
        return this.floodBypass;
    }

    /**
     * Executes flood time for this permission contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public int floodTime() {
        return this.floodTime;
    }

    /**
     * Executes disconnectable for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean disconnectable() {
        return this.disconnectable;
    }

    /**
     * Executes bannable for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean bannable() {
        return this.bannable;
    }

    /**
     * Executes mod tool for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean modTool() {
        return this.modTool;
    }

    /**
     * Executes alfa tool for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean alfaTool() {
        return this.alfaTool;
    }

    /**
     * Executes room kickable for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean roomKickable() {
        return this.roomKickable;
    }

    /**
     * Executes room full control for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean roomFullControl() {
        return this.roomFullControl;
    }

    /**
     * Executes room mute bypass for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean roomMuteBypass() {
        return this.roomMuteBypass;
    }

    /**
     * Executes room filter bypass for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean roomFilterBypass() {
        return this.roomFilterBypass;
    }

    /**
     * Executes room ignorable for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean roomIgnorable() {
        return this.roomIgnorable;
    }

    /**
     * Executes room enter full for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean roomEnterFull() {
        return roomEnterFull;
    }

    /**
     * Executes messenger staff chat for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean messengerStaffChat() {
        return this.messengerStaffChat;
    }

    /**
     * Executes messenger mod chat for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean messengerModChat() {
        return this.messengerModsChat;
    }

    /**
     * Executes messenger log chat for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean messengerLogChat() {
        return this.messengerLogChat;
    }

    /**
     * Executes messenger alfa chat for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean messengerAlfaChat() {
        return this.messengerAlfaChat;
    }

    /**
     * Executes room enter locked for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean roomEnterLocked() {
        return roomEnterLocked;
    }

    /**
     * Executes room staff pick for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean roomStaffPick() {
        return this.roomStaffPick;
    }

    /**
     * Executes messenger max friends for this permission contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public int messengerMaxFriends() {
        return this.messengerMaxFriends;
    }

    /**
     * Executes about detailed for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean aboutDetailed() {
        return this.aboutDetailed;
    }

    /**
     * Executes about stats for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean aboutStats() {
        return aboutStats;
    }

    /**
     * Executes room see whispers for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean roomSeeWhispers() {
        return this.roomSeeWhispers;
    }

    /**
     * Executes send login notif for this permission contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean sendLoginNotif() {
        return loginNotif;
    }
}
