package com.cometproject.server.game.rooms.types.components;

import com.cometproject.api.game.utilities.Position;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.RoomEntityType;
import com.cometproject.server.game.rooms.objects.entities.types.BotEntity;
import com.cometproject.server.game.rooms.objects.entities.types.MonsterPlantEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PetEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.entities.types.ai.pets.PetMonsterPlantAI;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.TeleporterFloorItem;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.types.RoomMessageType;
import com.cometproject.server.game.rooms.types.mapping.RoomTile;
import com.cometproject.server.network.messages.outgoing.room.engine.UpdateStackMapMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.settings.RoomRatingMessageComposer;
import com.cometproject.server.network.websockets.WebSocketSessionManager;
import com.cometproject.server.protocol.messages.MessageComposer;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Owns entity behavior inside the room processing subsystem.
 */
public class EntityComponent {
    private final Map<Integer, RoomEntity> entities = new ConcurrentHashMap<>();
    private final Map<Integer, Integer> playerIdToEntity = new ConcurrentHashMap<>();
    private final Map<Integer, Integer> botIdToEntity = new ConcurrentHashMap<>();
    private final Map<Integer, Integer> petIdToEntity = new ConcurrentHashMap<>();
    private final Map<String, Integer> nameToPlayerEntity = new ConcurrentHashMap<>();

    private Room room;
    private AtomicInteger entityIdGenerator = new AtomicInteger();

    /**
     * Creates a entity component instance for the room processing subsystem.
     *
     * @param room Room participating in the operation.
     */
    public EntityComponent(Room room) {
        this.room = room;
    }

    /**
     * Returns the entities at for this room processing contract.
     *
     * @param position Position supplied by the caller.
     * @return Value exposed by the contract.
     */
    public List<RoomEntity> getEntitiesAt(Position position) {
        RoomTile tile = this.getRoom().getMapping().getTile(position.getX(), position.getY());

        if (tile != null && tile.getEntities().size() >= 1) {
            return new ArrayList<>(tile.getEntities());
        }

        return new ArrayList<>();
    }

    /**
     * Executes position has entity for this room processing contract.
     *
     * @param position Position supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean positionHasEntity(Position position) {
        RoomTile tile = this.getRoom().getMapping().getTile(position.getX(), position.getY());

        if (tile != null) {
            return tile.getEntities().size() != 0;
        }

        return false;
    }

    /**
     * Executes position has entity for this room processing contract.
     *
     * @param position Position supplied by the caller.
     * @param ignoredEntities Ignored entities supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean positionHasEntity(Position position, final Set<RoomEntity> ignoredEntities) {
        RoomTile tile = this.getRoom().getMapping().getTile(position.getX(), position.getY());

        if (tile != null) {
            for (RoomEntity entity : tile.getEntities()) {
                if (!ignoredEntities.contains(entity)) return true;
            }
        }

        return false;
    }

    /**
     * Creates entity for this room processing contract.
     *
     * @param player Player participating in the operation.
     * @return Value exposed by the contract.
     */
    public PlayerEntity createEntity(Player player) {
        Position startPosition = new Position(this.getRoom().getModel().getRoomModelData().getDoorX(), this.getRoom().getModel().getRoomModelData().getDoorY(), this.getRoom().getModel().getDoorZ());

        if (player.isTeleporting()) {
            RoomItemFloor item = this.room.getItems().getFloorItem(player.getTeleportId());

            if (item != null) {
                startPosition = new Position(item.getPosition().getX(), item.getPosition().getY(), item.getPosition().getZ());
            }
        }

        int doorRotation = this.getRoom().getModel().getRoomModelData().getDoorRotation();

        PlayerEntity entity = new PlayerEntity(player, this.getFreeId(), startPosition, doorRotation, doorRotation, this.getRoom());

        if (player.isTeleporting()) {
            RoomItemFloor flItem = this.room.getItems().getFloorItem(player.getTeleportId());

            if (flItem != null && (flItem instanceof TeleporterFloorItem)) {
                ((TeleporterFloorItem) flItem).handleIncomingEntity(entity, null);
            }
        }

        return entity;
    }

    /**
     * Adds entity to this room processing contract.
     *
     * @param entity Entity supplied by the caller.
     */
    public void addEntity(RoomEntity entity) {
        if (entity.getEntityType() == RoomEntityType.PLAYER) {
            PlayerEntity playerEntity = (PlayerEntity) entity;

            this.nameToPlayerEntity.put(entity.getUsername(), ((PlayerEntity) entity).getPlayerId());
            this.playerIdToEntity.put(playerEntity.getPlayerId(), playerEntity.getId());
        } else if (entity.getEntityType() == RoomEntityType.BOT) {
            BotEntity botEntity = (BotEntity) entity;

            this.botIdToEntity.put(botEntity.getBotId(), botEntity.getId());
        } else if (entity.getEntityType() == RoomEntityType.PET) {
            PetEntity petEntity = (PetEntity) entity;

            this.petIdToEntity.put(petEntity.getData().getId(), petEntity.getId());
        }

        this.entities.put(entity.getId(), entity);
    }

    /**
     * Executes broadcast message mode build for this room processing contract.
     *
     * @param tile Tile supplied by the caller.
     */
    public void broadcastMessageModeBuild(RoomTile tile) {

        for (RoomEntity entity : this.entities.values()) {
            if (entity.getEntityType() == RoomEntityType.PLAYER) {
                PlayerEntity playerEntity = (PlayerEntity) entity;

                if (playerEntity.getPlayer() == null)
                    continue;

                if (playerEntity.hasAttribute("SetzCMD") || playerEntity.hasAttribute("ConstruitCMD") || playerEntity.hasAttribute("modebuild"))
                    continue;

                playerEntity.getPlayer().getSession().send(new UpdateStackMapMessageComposer(tile));
            }
        }
    }

    /**
     * Removes entity from this room processing contract.
     *
     * @param entity Entity supplied by the caller.
     */
    public void removeEntity(RoomEntity entity) {
        final RoomTile tile = this.getRoom().getMapping().getTile(entity.getPosition());

        if (tile != null) {
            entity.removeFromTile(tile);
        }

        // Handle removing entity specifics
        if (entity.getEntityType() == RoomEntityType.PLAYER) {
            PlayerEntity playerEntity = (PlayerEntity) entity;

            this.playerIdToEntity.remove(playerEntity.getPlayerId());
            this.nameToPlayerEntity.remove(playerEntity.getUsername());
        } else if (entity.getEntityType() == RoomEntityType.BOT) {
            BotEntity botEntity = (BotEntity) entity;

            this.botIdToEntity.remove(botEntity.getBotId());
        } else if (entity.getEntityType() == RoomEntityType.PET) {
            PetEntity petEntity = (PetEntity) entity;

            this.petIdToEntity.remove(petEntity.getData().getId());
        }

        this.entities.remove(entity.getId());
    }

    /**
     * Executes broadcast socket for this room processing contract.
     *
     * @param obj Obj supplied by the caller.
     * @param usersWithRightsOnly Users with rights only supplied by the caller.
     */
    public void broadcastSocket(Object obj, boolean usersWithRightsOnly) {
        if (obj == null) return;

        for (RoomEntity entity : this.entities.values()) {
            if (entity.getEntityType() == RoomEntityType.PLAYER) {
                PlayerEntity playerEntity = (PlayerEntity) entity;

                if (playerEntity.getPlayer().getSession().getWsChannel() == null) {
                    System.out.print("Handled exception on Socket.");
                    continue;
                }

                /*if (usersWithRightsOnly && !this.room.getRights().hasRights(playerEntity.getPlayerId()) && !playerEntity.getPlayer().getPermissions().getRank().roomFullControl()) {
                    continue;
                }*/

                WebSocketSessionManager.getInstance().sendMessage(playerEntity.getPlayer().getSession().getWsChannel(), obj);
            }
        }
    }

    /**
     * Executes broadcast message for this room processing contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     * @param usersWithRightsOnly Users with rights only supplied by the caller.
     */
    public void broadcastMessage(MessageComposer msg, boolean usersWithRightsOnly) {
        broadcastMessage(msg, usersWithRightsOnly, RoomMessageType.GENERIC_COMPOSER);
    }

    /**
     * Executes broadcast message for this room processing contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     * @param usersWithRightsOnly Users with rights only supplied by the caller.
     * @param type Type supplied by the caller.
     */
    public void broadcastMessage(MessageComposer msg, boolean usersWithRightsOnly, RoomMessageType type) {
        if (msg == null) return;

        for (RoomEntity entity : this.entities.values()) {
            if (entity.getEntityType() == RoomEntityType.PLAYER) {
                PlayerEntity playerEntity = (PlayerEntity) entity;

                if (playerEntity.getPlayer() == null)
                    continue;

                if (usersWithRightsOnly && !this.room.getRights().hasRights(playerEntity.getPlayerId()) && !playerEntity.getPlayer().getPermissions().getRank().roomFullControl()) {
                    continue;
                }

                if (type == RoomMessageType.BOT_CHAT && playerEntity.getPlayer().botsMuted()) {
                    continue;
                }

                if (type == RoomMessageType.PET_CHAT && playerEntity.getPlayer().petsMuted()) {
                    continue;
                }

                playerEntity.getPlayer().getSession().send(msg);
            }
        }
    }

    /**
     * Executes broadcast chat message for this room processing contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     * @param sender Sender supplied by the caller.
     */
    public void broadcastChatMessage(MessageComposer msg, PlayerEntity sender) {
        for (RoomEntity entity : this.entities.values()) {
            if (entity.getEntityType() == RoomEntityType.PLAYER) {
                PlayerEntity playerEntity = (PlayerEntity) entity;

                if (playerEntity.getPlayer() == null)
                    continue;

                if (playerEntity.getPlayer().ignores(sender.getPlayerId()))
                    continue;

                if (!playerEntity.getPlayer().ignores(sender.getPlayerId()))
                    playerEntity.getPlayer().getSession().send(msg);
            }
        }
    }

    /**
     * Executes broadcast message for this room processing contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void broadcastMessage(MessageComposer msg) {
        broadcastMessage(msg, false);
    }

    /**
     * Returns the entity for this room processing contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public RoomEntity getEntity(int id) {
        return this.entities.get(id);
    }

    /**
     * Returns the entity by player id for this room processing contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public PlayerEntity getEntityByPlayerId(int id) {
        if (!this.playerIdToEntity.containsKey(id)) {
            return null;
        }

        int entityId = this.playerIdToEntity.get(id);
        RoomEntity roomEntity = this.entities.get(entityId);

        if (roomEntity == null || roomEntity.getEntityType() != RoomEntityType.PLAYER) {
            return null;
        }

        return (PlayerEntity) roomEntity;
    }

    /**
     * Returns the player entity by name for this room processing contract.
     *
     * @param username Username supplied by the caller.
     * @return Value exposed by the contract.
     */
    public PlayerEntity getPlayerEntityByName(final String username) {
        final Integer playerId = this.nameToPlayerEntity.get(username);

        if (playerId != null) {
            return this.getEntityByPlayerId(playerId);
        }

        return null;
    }

    /**
     * Returns the entity by name for this room processing contract.
     *
     * @param name Name supplied by the caller.
     * @param type Type supplied by the caller.
     * @return Value exposed by the contract.
     */
    public RoomEntity getEntityByName(String name, RoomEntityType type) {
        for (RoomEntity entity : this.getAllEntities().values()) {
            if (entity.getUsername() == null) continue;

            if (entity.getUsername().equalsIgnoreCase(name) && entity.getEntityType() == type) {
                return entity;
            }
        }

        return null;
    }

    /**
     * Returns the entity by bot id for this room processing contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public BotEntity getEntityByBotId(int id) {
        if (!this.botIdToEntity.containsKey(id)) {
            return null;
        }

        int entityId = this.botIdToEntity.get(id);
        RoomEntity roomEntity = this.entities.get(entityId);

        if (roomEntity == null || roomEntity.getEntityType() != RoomEntityType.BOT) {
            return null;
        }

        return (BotEntity) roomEntity;
    }

    /**
     * Returns the entity by pet id for this room processing contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public PetEntity getEntityByPetId(int id) {
        if (!this.petIdToEntity.containsKey(id)) {
            return null;
        }

        int entityId = this.petIdToEntity.get(id);
        RoomEntity roomEntity = this.entities.get(entityId);

        if (roomEntity == null || roomEntity.getEntityType() != RoomEntityType.PET) {
            return null;
        }

        return (PetEntity) roomEntity;
    }

    /**
     * Returns the entity by plant id for this room processing contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public MonsterPlantEntity getEntityByPlantId(int id) {
        if (!this.petIdToEntity.containsKey(id)) {
            return null;
        }

        int entityId = this.petIdToEntity.get(id);
        RoomEntity roomEntity = this.entities.get(entityId);

        if (roomEntity == null || roomEntity.getEntityType() != RoomEntityType.PET) {
            return null;
        }

        return (MonsterPlantEntity) roomEntity;
    }

    /**
     * Returns the bot entities for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public List<BotEntity> getBotEntities() {
        List<BotEntity> entities = new ArrayList<>();

        for (RoomEntity entity : this.entities.values()) {
            if (entity.getEntityType() == RoomEntityType.BOT) {
                entities.add((BotEntity) entity);
            }
        }

        return entities;
    }

    /**
     * Returns the pet entities for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public List<PetEntity> getPetEntities() {
        List<PetEntity> entities = new ArrayList<>();

        for (RoomEntity entity : this.entities.values()) {
            if (entity.getEntityType() == RoomEntityType.PET) {
                entities.add((PetEntity) entity);
            }
        }

        return entities;
    }

    /**
     * Returns the player entities for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public List<PlayerEntity> getPlayerEntities() {
        List<PlayerEntity> entities = new ArrayList<>();

        if (this.entities.size() < 1) {
            return entities;
        }

        for (RoomEntity entity : this.entities.values()) {
            if (entity.getEntityType() == RoomEntityType.PLAYER) {
                entities.add((PlayerEntity) entity);
            }
        }

        return entities;
    }

    /**
     * Returns the monsterplants for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public List<MonsterPlantEntity> getMonsterplants() {
        List<MonsterPlantEntity> entities = new ArrayList<>();

        if (this.entities.size() < 1) {
            return entities;
        }

        for (RoomEntity entity : this.entities.values()) {
            if (entity.getEntityType() == RoomEntityType.PET) {
                if(entity.getAI() instanceof PetMonsterPlantAI)
                entities.add((MonsterPlantEntity) entity);
            }
        }

        return entities;
    }

    /**
     * Returns the whisper seers for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public List<PlayerEntity> getWhisperSeers() {
        List<PlayerEntity> entities = new ArrayList<>();

        if (this.entities.size() < 1) {
            return entities;
        }

        for (RoomEntity entity : this.entities.values()) {
            if (entity.getEntityType() == RoomEntityType.PLAYER) {
                if (((PlayerEntity) entity).getPlayer().getPermissions().getRank().roomSeeWhispers())
                    entities.add((PlayerEntity) entity);
            }
        }

        return entities;
    }

    /**
     * Returns the casino players for this room processing contract.
     *
     * @param pos Pos supplied by the caller.
     * @return Value exposed by the contract.
     */
    public List<PlayerEntity> getCasinoPlayers(Position pos) {
        List<PlayerEntity> entities = new ArrayList<>();

        if (this.entities.size() < 1) {
            return entities;
        }

        for (RoomEntity entity : this.entities.values()) {
            if (entity.getEntityType() == RoomEntityType.PLAYER) {
                if (((PlayerEntity) entity).getPosition().distanceTo(pos) < 4)
                    entities.add((PlayerEntity) entity);
            }
        }

        return entities;
    }

    /**
     * Executes refresh score for this room processing contract.
     */
    public void refreshScore() {
        for (PlayerEntity entity : getPlayerEntities()) {
            entity.getPlayer().getSession().send(new RoomRatingMessageComposer(room.getData().getScore(), entity.canRateRoom()));
        }
    }

    /**
     * Returns the free id for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    protected int getFreeId() {
        return this.entityIdGenerator.incrementAndGet();
    }

    /**
     * Executes count for this room processing contract.
     *
     * @return Result produced by the operation.
     */
    public int count() {
        int count = 0;

        for (RoomEntity entity : this.entities.values()) {
            if (entity.isVisible()) count++;
        }

        return count;
    }

    /**
     * Executes player count for this room processing contract.
     *
     * @return Result produced by the operation.
     */
    public int playerCount() {
        List<Integer> countedEntities = Lists.newArrayList();

        try {
            for (RoomEntity entity : this.entities.values()) {
                if (entity instanceof PlayerEntity && entity.isVisible()) {
                    if (!countedEntities.contains(((PlayerEntity) entity).getPlayerId())) {
                        countedEntities.add(((PlayerEntity) entity).getPlayerId());
                    }
                }
            }

            return countedEntities.size();
        } catch (Exception e) {
            return 0;
        } finally {
            countedEntities.clear();
        }
    }

    /**
     * Executes real player count for this room processing contract.
     *
     * @return Result produced by the operation.
     */
    public int realPlayerCount() {
        return this.playerIdToEntity.size();
    }

    /**
     * Returns the all entities for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Integer, RoomEntity> getAllEntities() {
        return this.entities;
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
     * Releases resources owned by this room processing component.
     */
    public void dispose() {
        for (Map.Entry<Integer, RoomEntity> entity : this.entities.entrySet()) {
            entity.getValue().onRoomDispose();
        }

        this.entities.clear();
        this.playerIdToEntity.clear();
        this.botIdToEntity.clear();
        this.petIdToEntity.clear();
        this.nameToPlayerEntity.clear();
    }
}
