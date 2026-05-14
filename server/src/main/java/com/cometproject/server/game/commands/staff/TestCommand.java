package com.cometproject.server.game.commands.staff;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.sessions.Session;

import java.util.Random;

/**
 * Describes test command behavior for the Comet subsystem.
 */
public class TestCommand extends ChatCommand {
    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        /*client.send(new ConfirmableAlertMessageComposer(client.getPlayer().getData().getUsername(), 1, false));
        client.getPlayer().setShadow(1);*/
        //client.send(new SeasonalCalendarMessageComposer(Integer.parseInt(params[0])));
        /*client.send(new UpdateActivityPointsMessageComposer(6000, 6000, 0));
        client.send(new ConfirmableAlertMessageComposer(client.getPlayer().getData().getUsername(), 1, false));*/
        client.getPlayer().getData().increaseAchievementPoints(100);

        if(params[0].equals("body")){
            client.getPlayer().getEntity().setBodyRotating(true);
        } else {
            client.getPlayer().getEntity().setHeadRotating(true);
        }

        /*if(client.getPlayer().getRP().hasAttribute("death")){
            client.getPlayer().sendBubble("", "Tracked attribute");
            client.getPlayer().getRP().removeAttribute("death");
        } else {
            client.getPlayer().getRP().setAttribute("death", true);
        }*/
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "test_command";
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
        return Locale.get("command.test.description");
    }

    private int result(){
        Random result = new Random();
        result.setSeed(Comet.getTime());

        return result.nextInt(37);
    }
}
