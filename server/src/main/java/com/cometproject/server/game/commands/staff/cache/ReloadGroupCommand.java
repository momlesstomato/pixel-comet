package com.cometproject.server.game.commands.staff.cache;

import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes reload group command behavior for the Comet subsystem.
 */
public class ReloadGroupCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
//        if (params.length != 1) {
//            return;
//        }
//
//        if (!StringUtils.isNumeric(params[0])) {
//            return;
//        }
//
//        final int groupId = Integer.parseInt(params[0]);
//
//        final IGroupData groupData = GroupManager.getInstance().getData(groupId);
//
//        StorageContext.getCurrentContext().getGroupRepository().getDataById(groupId, newGroupData -> {
//            if (groupData.getRoomId() != newGroupData.getRoomId()) {
//                if (RoomManager.getInstance().isActive(groupData.getRoomId())) {
//                    Room oldRoom = RoomManager.getInstance().get(groupData.getRoomId());
//
//                    if (oldRoom != null) {
//                        oldRoom.setIdleNow();
//                    }
//                }
//
//                if (RoomManager.getInstance().isActive(newGroupData.getRoomId())) {
//                    Room newRoom = RoomManager.getInstance().get(newGroupData.getRoomId());
//
//                    if (newRoom != null) {
//                        newRoom.setIdleNow();
//                    }
//                }
//            }
//
//            groupData.setCanMembersDecorate(newGroupData.canMembersDecorate());
//            groupData.setOwnerId(newGroupData.getOwnerId());
//            groupData.setRoomId(newGroupData.getRoomId());
//            groupData.setBadge(newGroupData.getBadge());
//            groupData.setColourA(newGroupData.getColourA());
//            groupData.setColourB(newGroupData.getColourB());
//            groupData.setDescription(newGroupData.getDescription());
//            groupData.setTitle(newGroupData.getTitle());
//            groupData.setType(newGroupData.getType());
//
//            client.send(new AlertMessageComposer(Locale.getOrDefault("command.reloadgroup.done", "Group data reloaded successfully!")));
//        });
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "reloadgroup_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return "";
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return null;
    }

    /**
     * Indicates whether hidden applies to this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isHidden() {
        return true;
    }
}
