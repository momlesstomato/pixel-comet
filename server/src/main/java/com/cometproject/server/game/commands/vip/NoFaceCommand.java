package com.cometproject.server.game.commands.vip;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.messages.outgoing.room.avatar.UpdateInfoMessageComposer;
import com.cometproject.server.network.sessions.Session;
import org.apache.commons.lang3.StringUtils;


/**
 * Describes no face command behavior for the Comet subsystem.
 */
public class NoFaceCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        String figure = client.getPlayer().getData().getFigure();

        if (client.getPlayer().getData().getTemporaryFigure() != null) {
            client.getPlayer().getData().setFigure(client.getPlayer().getData().getTemporaryFigure());
            client.getPlayer().getData().setTemporaryFigure(null);
            client.getPlayer().getData().save();
        } else {
            if (figure.contains("hd-")) {
                String[] head = ("hd-" + figure.split("hd-")[1].split("\\.")[0]).split("-");

                if (head.length < 2)
                    return;

                client.getPlayer().getData().setTemporaryFigure(figure);
                client.getPlayer().getData().setFigure(figure.replace(StringUtils.join(head, "-"), "hd-" + 99999 + "-" + (head.length != 3 ? head.length == 0 ? "" : head[head.length - 1] : head[2])));
            }
        }

        client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new UpdateInfoMessageComposer(client.getPlayer().getEntity()));
        client.send(new UpdateInfoMessageComposer(-1, client.getPlayer().getEntity()));
        isExecuted(client);
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "noface_command";
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
        return Locale.get("command.noface.description");
    }
}
