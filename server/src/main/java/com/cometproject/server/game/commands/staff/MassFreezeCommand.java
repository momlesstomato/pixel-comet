package com.cometproject.server.game.commands.staff;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.cometproject.server.game.rooms.objects.entities.types.PetEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes mass freeze command behavior for the Comet subsystem.
 */
public class MassFreezeCommand extends ChatCommand {

    private String logDesc = "";

    /**
     * Executes execute for this Comet contract.
     *
     * @param client Client supplied by the caller.
     * @param params Params supplied by the caller.
     */
    @Override
    public void execute(Session client, String[] params) {
        for (RoomEntity roomEntity : client.getPlayer().getEntity().getRoom().getEntities().getAllEntities().values()) {
            if (roomEntity != client.getPlayer().getEntity() && !(roomEntity instanceof PetEntity)) {
                roomEntity.cancelWalk();
                roomEntity.setCanWalk(!roomEntity.canWalk());

                if (!roomEntity.canWalk()) {
                    if (roomEntity instanceof PlayerEntity) {
                        sendNotif(Locale.getOrDefault("command.massfreeze.frozen", "You've been frozen!"),
                                ((PlayerEntity) roomEntity).getPlayer().getSession());
                    }

                    roomEntity.applyEffect(new PlayerEffect(12));
                } else {
                    if (roomEntity instanceof PlayerEntity) {
                        sendNotif(Locale.getOrDefault("command.massfreeze.unfrozen", "You've been frozen!"),
                                ((PlayerEntity) roomEntity).getPlayer().getSession());
                    }

                    roomEntity.applyEffect(roomEntity.getLastEffect());
                }
            }
        }

        this.logDesc = "El staff %s ha hecho <b>massfreeze</b>"
                .replace("%s", client.getPlayer().getData().getUsername());
    }

    /**
     * Returns the permission for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPermission() {
        return "massfreeze_command";
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
        return Locale.get("command.massfreeze.description");
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
