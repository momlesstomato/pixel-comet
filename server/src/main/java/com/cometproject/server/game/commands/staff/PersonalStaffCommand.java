package com.cometproject.server.game.commands.staff;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.permissions.PermissionsDao;
import com.cometproject.server.storage.queries.player.PlayerDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(PermissionsDao.getEffects().entrySet());
        list.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

        for (Map.Entry<Integer, Integer> entry : list) {

            if (client.getPlayer().getPermissions().getRank().getId() < entry.getValue())
                continue;

            client.getPlayer().getSettings().setPersonalStaff(!client.getPlayer().getSettings().hasPersonalStaff());

            if (client.getPlayer().getEntity() == null)
                return;

            if (client.getPlayer().getSettings().hasPersonalStaff()) {
                client.getPlayer().getEntity().applyEffect(new PlayerEffect(entry.getKey()));
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
