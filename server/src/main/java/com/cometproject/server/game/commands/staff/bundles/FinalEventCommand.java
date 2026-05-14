package com.cometproject.server.game.commands.staff.bundles;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.game.quests.QuestType;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.engine.RoomActionMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.mistery.MisteryBoxDataMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.utilities.RandomUtil;
import com.cometproject.storage.api.data.currency.CurrencyUseCases;
import com.cometproject.storage.api.services.ICurrencyService;

/**
 * Describes final event command behavior for the Comet subsystem.
 */
public class FinalEventCommand extends ChatCommand {

    private String logDesc = "";

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        String commandName = params[0];

        switch (commandName) {
            case "ween":
                final Room room = client.getPlayer().getEntity().getRoom();

                for (PlayerEntity p : room.getEntities().getPlayerEntities()) {
                    int k = RandomUtil.getRandomInt(1,2);
                    String key = k == 1 ? "lilac" : "green", box = k == 1 ? "green" : "lilac";
                    p.getPlayer().getMistery().setMisteryKey(key);
                    p.getPlayer().getMistery().setMisteryBox(box);

                    serializeInfo(p, "ween");
                }

                room.getEntities().broadcastMessage(new RoomActionMessageComposer(1));
                room.getEntities().broadcastMessage(new NotificationMessageComposer("pumpkin", "Custom ha sido derrotado y liberado del espíritu maligno y agradece a todos los usuarios con un obsequio.", ""));

                this.logDesc = "El staff %s ha realizado un evento final en la sala '%b'."
                        .replace("%s", client.getPlayer().getData().getUsername())
                        .replace("%b", client.getPlayer().getEntity().getRoom().getData().getName());

                break;


            case "toggleween":
                if (CometSettings.toggleWeenMode) {
                    CometSettings.setWeenEvent(false);
                    sendNotif(Locale.getOrDefault("command.togglefriends.disabled", "You have disabled ween mode."), client);
                } else {
                    CometSettings.setWeenEvent(true);
                    sendNotif(Locale.getOrDefault("command.togglefriends.disabled", "You have enabled ween mode."), client);
                    break;
                }

            case "destroy":

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
        return "finalevent_command";
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
        return Locale.get("command.finalevent.description");
    }

    /**
     * Indicates whether async applies to this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isAsync() {
        return true;
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

    private void serializeInfo(PlayerEntity p, String type){
        switch (type) {
            case "ween":
            final ICurrencyService currencyService = com.cometproject.server.boot.CometBootstrap.resolve(ICurrencyService.class);
            p.getPlayer().getSession().send(new MisteryBoxDataMessageComposer(p.getPlayer().getMistery()));
            p.getPlayer().getData().increaseCurrency(currencyService.currencyCodeForUseCase(CurrencyUseCases.STAFF_EVENT_SPECIAL), 5);
            p.getPlayer().getData().increaseCurrency(currencyService.currencyCodeForUseCase(CurrencyUseCases.STAFF_EVENT_PRIMARY), 500);
            p.getPlayer().getData().increaseCurrency(currencyService.currencyCodeForUseCase(CurrencyUseCases.STAFF_EVENT_SECONDARY), 2);
            p.getPlayer().getInventory().addBadge("LAB07", true);
            p.getPlayer().sendBalance();
            p.getPlayer().getQuests().progressQuest(QuestType.WEEN_FINAL);
            break;
            default:
            break;
        }
    }
}
