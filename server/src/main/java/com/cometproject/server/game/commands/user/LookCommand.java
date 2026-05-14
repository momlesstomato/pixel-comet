package com.cometproject.server.game.commands.user;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.game.players.data.types.IWardrobeItem;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.messages.outgoing.user.details.AvatarAspectUpdateMessageComposer;
import com.cometproject.server.network.sessions.Session;

import java.util.List;

/**
 * Describes look command behavior for the Comet subsystem.
 */
public class LookCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1){
            return;
        }

        int slot = Integer.parseInt(params[0]);
        int timeSinceLastUpdate = ((int) Comet.getTime()) - client.getPlayer().getLastFigureUpdate();
        List<IWardrobeItem> wardrobe = client.getPlayer().getSettings().getWardrobe();

        for (IWardrobeItem item : wardrobe) {
            if (item.getSlot() == slot) {
                if (timeSinceLastUpdate >= CometSettings.playerChangeFigureCooldown) {
                    client.getPlayer().getData().setGender(item.getGender());
                    client.getPlayer().getData().setFigure(item.getFigure());
                    client.getPlayer().getData().save();
                    client.getPlayer().poof();
                    client.getPlayer().setLastFigureUpdate((int) Comet.getTime());

                    client.send(new AvatarAspectUpdateMessageComposer(item.getFigure(), item.getGender()));
                }
            }
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "look_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return null;
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.get("command.look.description");
    }
}
