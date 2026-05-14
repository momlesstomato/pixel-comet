package com.cometproject.server.game.commands.user;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.messages.outgoing.notification.MotdNotificationMessageComposer;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes super wired command behavior for the Comet subsystem.
 */
public class SuperWiredCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        client.send(new MotdNotificationMessageComposer(
                "SuperWired Commands:\n\n" +
                        "SuperWired Efecto: \n" +
                        "Enable \n" +
                        "HandItem\n" +
                        "Freeze\n" +
                        "Sit\n" +
                        "Lay\n" +
                        "Dance\n" +
                        "MoonWalk\n" +
                        "ToRoom\n" +
                        "FastWalk\n" +
                        "RoomMute\n" +
                        "Points\n" +
                        "RemovePoints\n" +
                        "ResetPoints\n" +
                        "VerifyPoints\n\n" +

                        "SuperWired Condición:\n" +
                        "Enable\n" +
                        "NoEnable\n" +
                        "HandItem\n" +
                        "NoHandItem\n" +
                        "Transform\n" +
                        "Mission\n" +
                        "NoMission\n" +
                        "Dance\n" +
                        "TeamRed-Max\n" +
                        "TeamRed-Min\n" +
                        "TeamBlue-Max\n" +
                        "TeamBlue-Min\n" +
                        "TeamYellow-Max\n" +
                        "TeamYellow-Min\n" +
                        "TeamGreen-Max\n" +
                        "TeamGreen-Min\n" +
                        "Points\n" +
                        "NoPoints\n"
        ));
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "superwired_command";
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
        return Locale.get("command.superwired.description");
    }
}
