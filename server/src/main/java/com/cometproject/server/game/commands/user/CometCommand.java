package com.cometproject.server.game.commands.user;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.boot.CometServer;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.cometproject.server.network.sessions.Session;


/**
 * Describes comet command behavior for the Comet subsystem.
 */
public class CometCommand extends ChatCommand {

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param message Message supplied by the caller.
     */
    @Override
    public void execute(Session client, String message[]) {
        client.send(new AlertMessageComposer("Powered by Comet Server. <br><br><b>Waves to:</b><br>- Leon<br>- Eden<br>- Matty<br>- Matou19<br>- Metus<br>- Mandoe<br>- Markones<br>- Helpi<br>- Vaguinho<br>- Equipe Staff<br>- Gladius<br>- Johno<br>- Sledmore<br>- Scott<br>- Nillus<br>- Jordan<br>- Burak<br>- Quackster<br>- Jaxter<br>- Kai<br>- lElectrico<br>- Caffeine<br>- More Caffeine<br>- Mary Jane<br><br><b>Fuckings to:</b><br>- Fahd<br>- Magrao<br>- TheGeneral<br>- Luck<br><br>Server Version: <b>" + Comet.getBuild() + "</b><br>Client Version: <b>" + CometServer.CLIENT_VERSION.replace(" (FINAL RELEASE, Thank you Leon for everything.)<br>PRODUCTION-", "").split(" ")[0] + "</b>"));
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "dev";
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
        return "";
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
