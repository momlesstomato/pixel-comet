package com.cometproject.server.game.rooms.types.components;

import com.cometproject.api.game.bots.IBotData;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.server.game.bots.BotData;
import com.cometproject.server.game.rooms.objects.entities.types.BotEntity;
import com.cometproject.server.game.rooms.objects.entities.types.data.PlayerBotData;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.storage.queries.bots.RoomBotDao;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;


/**
 * Owns room bot behavior inside the room processing subsystem.
 */
public class RoomBotComponent {
    private Room room;

    private Map<String, Integer> botNameToId;

    /**
     * Creates a room bot component instance for the room processing subsystem.
     *
     * @param room Room participating in the operation.
     */
    public RoomBotComponent(Room room) {
        this.room = room;

        this.botNameToId = Maps.newHashMap();

        this.load();
    }

    /**
     * Releases resources owned by this room processing component.
     */
    public void dispose() {
        this.botNameToId.clear();
    }

    /**
     * Executes load for this room processing contract.
     */
    public void load() {
        try {
            List<IBotData> botData = this.room.getCachedData() != null ? this.room.getCachedData().getBots() : RoomBotDao.getBotsByRoomId(this.room.getId());

            for (IBotData data : botData) {
                if (this.botNameToId.containsKey(data.getUsername())) {
                    data.setUsername(this.getAvailableName(data.getUsername()));
                }

                BotEntity botEntity = new BotEntity(data, room.getEntities().getFreeId(), ((PlayerBotData) data).getPosition(), 2, 2, room);
                this.botNameToId.put(botEntity.getUsername(), botEntity.getBotId());

                botEntity.getPosition().setZ(this.getRoom().getMapping().getStepHeight(botEntity.getPosition()));

                this.getRoom().getEntities().addEntity(botEntity);

                for (RoomItemFloor roomItemFloor : this.getRoom().getItems().getItemsOnSquare(((PlayerBotData) data).getPosition().getX(), ((PlayerBotData) data).getPosition().getY())) {
                    roomItemFloor.onEntityStepOn(botEntity);
                }
            }
        } catch (Exception e) {
            room.LOGGER.error("Error while deploying bots", e);
        }
    }

    /**
     * Returns the available name for this room processing contract.
     *
     * @param name Name supplied by the caller.
     * @return Value exposed by the contract.
     */
    public String getAvailableName(String name) {
        int usedCount = 0;

        for (String usedName : this.botNameToId.keySet()) {
            if (name.startsWith(usedName)) {
                usedCount++;
            }
        }

        if (usedCount == 0) return name;

        return name + usedCount;
    }

    /**
     * Adds bot to this room processing contract.
     *
     * @param bot Bot supplied by the caller.
     * @param x X supplied by the caller.
     * @param y Y supplied by the caller.
     * @param height Height supplied by the caller.
     * @return Result produced by the mutation.
     */
    public BotEntity addBot(IBotData bot, int x, int y, double height) {
        int virtualId = room.getEntities().getFreeId();
        String name;

        if (this.botNameToId.containsKey(bot.getUsername())) {
            name = this.getAvailableName(bot.getUsername());
        } else {
            name = bot.getUsername();
        }

        this.botNameToId.put(bot.getUsername(), bot.getId());

        BotData botData = new PlayerBotData(bot.getId(), name, bot.getMotto(), bot.getFigure(), bot.getGender(), bot.getOwnerName(), bot.getOwnerId(), "[]", true, 7, bot.getBotType(), bot.getMode(), null);
        BotEntity botEntity = new BotEntity(botData, virtualId, new Position(x, y, height), 1, 1, room);

        if (botEntity.getPosition().getZ() < this.getRoom().getModel().getSquareHeight()[x][y]) {
            botEntity.getPosition().setZ(this.getRoom().getModel().getSquareHeight()[x][y]);
        }

        this.getRoom().getEntities().addEntity(botEntity);
        return botEntity;
    }

    /**
     * Returns the bot by name for this room processing contract.
     *
     * @param name Name supplied by the caller.
     * @return Value exposed by the contract.
     */
    public BotEntity getBotByName(String name) {
        if (this.botNameToId.containsKey(name)) {
            return this.getRoom().getEntities().getEntityByBotId(this.botNameToId.get(name));
        }

        return null;
    }

    /**
     * Returns the room for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public Room getRoom() {
        return this.room;
    }

    /**
     * Executes change bot name for this room processing contract.
     *
     * @param currentName Current name supplied by the caller.
     * @param newName New name supplied by the caller.
     */
    public void changeBotName(String currentName, String newName) {
        if (!this.botNameToId.containsKey(currentName)) return;

        int botId = this.botNameToId.get(currentName);

        this.botNameToId.remove(currentName);
        this.botNameToId.put(newName, botId);
    }

    /**
     * Executes change bot motto for this room processing contract.
     *
     * @param bot Bot supplied by the caller.
     * @param motto Motto supplied by the caller.
     */
    public void changeBotMotto(BotEntity bot, String motto){
        bot.getData().setMotto(motto);
        bot.getData().save();
    }

    /**
     * Removes bot from this room processing contract.
     *
     * @param name Name supplied by the caller.
     */
    public void removeBot(String name) {
        this.botNameToId.remove(name);
    }
}
