package com.cometproject.server.game.commands.staff;

import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.permissions.PermissionNodeCatalog;
import com.cometproject.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.storage.api.services.IPermissionService;


/**
 * Describes mass effect command behavior for the Comet subsystem.
 */
public class MassEffectCommand extends ChatCommand {

    private String logDesc = "";

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            sendNotif(Locale.getOrDefault("command.masseffect.none", "To give everyone in the room an effect type :masseffect %number%"), client);
            return;
        }

        try {
            int effectId = Integer.parseInt(params[0]);
            final IPermissionService permissionService = CometBootstrap.resolve(IPermissionService.class);
            final String effectNode = PermissionNodeCatalog.effect(effectId);

            if (permissionService.isPermissionNodeDefined(effectNode)
                    && !permissionService.hasPermission(client.getPlayer().getId(), effectNode)) {
                effectId = 10;
            }

            for (PlayerEntity playerEntity : client.getPlayer().getEntity().getRoom().getEntities().getPlayerEntities()) {
                playerEntity.applyEffect(new PlayerEffect(effectId, 0));
            }

        } catch (Exception e) {
            sendNotif(Locale.get("command.masseffect.invalidid"), client);
        }

        this.logDesc = "El staff %s ha hecho <b>massEffect</b> en la sala '%b'"
                .replace("%s", client.getPlayer().getData().getUsername())
                .replace("%b", client.getPlayer().getEntity().getRoom().getData().getName());
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "masseffect_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.number", "%number%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.masseffect.description");
    }

    /**
     * Returns the loggable description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getLoggableDescription(){
        return this.logDesc;
    }

    /**
     * Executes loggable for this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean Loggable(){
        return true;
    }
}
