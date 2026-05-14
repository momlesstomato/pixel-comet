package com.cometproject.server.game.commands.development;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes position command behavior for the Comet subsystem.
 */
public class PositionCommand extends ChatCommand {
    private boolean debug;

    /**
     * Creates a position command instance for the Comet subsystem.
     */
    public PositionCommand() {
        this.debug = false;
    }

    /**
     * Creates a position command instance for the Comet subsystem.
     *
     * @param debug Debug supplied by the caller.
     */
    public PositionCommand(boolean debug) {
        this.debug = debug;
    }

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        sendNotif(("X: " + client.getPlayer().getEntity().getPosition().getX() + "\r\n") +
                        "Y: " + client.getPlayer().getEntity().getPosition().getY() + "\r\n" +
                        "Z: " + client.getPlayer().getEntity().getPosition().getZ() + "\r\n" +
                        "Rotation: " + client.getPlayer().getEntity().getBodyRotation() + "\r\n" +
                        "Entities: " + client.getPlayer().getEntity().getTile().getEntities().size() + "\r\n",
                client);
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return this.debug ? "dev" : "position_command";
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
        return Locale.get("command.position.description");
    }
}
