package com.cometproject.server.game.commands.staff;

import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.permissions.PermissionNodeCatalog;
import com.cometproject.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.player.PlayerDao;
import com.cometproject.storage.api.services.IPermissionService;

/**
 * Describes personal staff command behavior for the Comet subsystem.
 */
public class PersonalStaffCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (client == null) return;

        final IPermissionService permissionService = CometBootstrap.resolve(IPermissionService.class);

        for (int effectId : PermissionNodeCatalog.personalStaffEffects()) {
            final String effectNode = PermissionNodeCatalog.effect(effectId);

            if (!permissionService.isPermissionNodeDefined(effectNode)
                    || !permissionService.hasPermission(client.getPlayer().getId(), effectNode)) {
                continue;
            }

            client.getPlayer().getSettings().setPersonalStaff(!client.getPlayer().getSettings().hasPersonalStaff());

            if (client.getPlayer().getEntity() == null)
                return;

            if (client.getPlayer().getSettings().hasPersonalStaff()) {
                client.getPlayer().getEntity().applyEffect(new PlayerEffect(effectId));
            } else
                client.getPlayer().getEntity().applyEffect(new PlayerEffect(0));

            break;
        }

        PlayerDao.savePersonalStaff(client.getPlayer().getSettings().hasPersonalStaff(), client.getPlayer().getId());
        sendWhisper(Locale.get("command.personalstaff." + (client.getPlayer().getSettings().hasPersonalStaff() ? "enabled" : "disabled")), client);
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "personalstaff_command";
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
        return Locale.get("command.personalstaff.description");
    }
}
