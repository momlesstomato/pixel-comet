package com.cometproject.server.game.commands.staff.rewards;
import com.cometproject.api.config.CometSettings;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.cometproject.server.network.messages.outgoing.quests.VIPQuestPromotionMessageComposer;
import com.cometproject.server.network.sessions.Session;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * Describes lottery command behavior for the Comet subsystem.
 */
public class LotteryCommand extends ChatCommand {

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        if (params.length < 1)
            return;

        Room room = client.getPlayer().getEntity().getRoom();

        if(room == null)
            return;

        switch (params[0]){
            case "start":
                room.getEntities().broadcastMessage(new VIPQuestPromotionMessageComposer());
                client.getPlayer().sendBubble("lottery", "Acabas de empezar una lotería en esta sala.");
                break;
            case "run":
                int entrySize = NetworkManager.getInstance().getSessions().getLotteryEntries().size();
                int prize = entrySize * CometSettings.lotteryPool;
                int winnerId = new Random().nextInt(entrySize);
                int count = 0;

                Session winner = null;

                for(Integer userId : NetworkManager.getInstance().getSessions().getLotteryEntries()){
                    if(count == winnerId){
                        winner = NetworkManager.getInstance().getSessions().getByPlayerId(userId);
                    }
                    count++;
                }

                if(winner != null){
                    winner.getPlayer().getData().increaseCredits(prize);
                    winner.getPlayer().sendBalance();
                    winner.getPlayer().getData().save();
                    room.getEntities().broadcastMessage(new NotificationMessageComposer("lottery_award", Locale.getOrDefault("lottery.winner", "%user% ha ganado la ronda.\n\nEl bote total era de %amount% con %size% participantes.").replace("%user%", winner.getPlayer().getData().getUsername()).replace("%amount%", prize + "").replace("%size%", entrySize + "")));
                }

                NetworkManager.getInstance().getSessions().clearLottery();
                break;
            case "clear":
                NetworkManager.getInstance().getSessions().clearLottery();
                client.getPlayer().sendBubble("lottery", "Entradas en la lotería limpiadas.");
                break;
            case "pool":
                if(params[1] == null)
                    return;
                int pool = Integer.parseInt(params[1]);

                if(StringUtils.isNumeric(params[1])) {
                    CometSettings.setLotteryPool(pool);
                    client.getPlayer().sendBubble("lottery", "Precio del bono ajustado a " + pool + ".");
                }
                break;
        }
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "lottery_command";
    }

    /**
     * Returns the parameter for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.type", "%type%");
    }

    /**
     * Returns the description for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return Locale.getOrDefault("command.lottery.description", "Empieza una ronda de lotería.");
    }
}
