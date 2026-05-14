package com.cometproject.server.game.rooms.objects.entities.types;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.game.GameContext;
import com.cometproject.api.game.achievements.types.AchievementType;
import com.cometproject.api.game.bots.BotMode;
import com.cometproject.api.game.bots.BotType;
import com.cometproject.api.game.groups.types.IGroupData;
import com.cometproject.api.game.players.data.components.inventory.PlayerItem;
import com.cometproject.api.game.quests.QuestType;
import com.cometproject.api.game.rooms.entities.PlayerRoomEntity;
import com.cometproject.api.game.rooms.entities.RoomEntityStatus;
import com.cometproject.api.game.rooms.settings.RoomAccessType;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.achievements.BattlePassGlobals;
import com.cometproject.server.game.achievements.types.BattlePassMission;
import com.cometproject.server.game.achievements.types.BattlePassMissionEnums;
import com.cometproject.server.game.commands.CommandManager;
import com.cometproject.server.game.commands.vip.TransformCommand;
import com.cometproject.server.game.moderation.BanManager;
import com.cometproject.server.game.moderation.types.Ban;
import com.cometproject.server.game.moderation.types.BanType;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.game.players.data.PlayerData;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.game.rooms.RoomManager;
import com.cometproject.server.game.rooms.RoomQueue;
import com.cometproject.server.game.rooms.objects.entities.PlayerEntityAccess;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.ai.bots.WaiterAI;
import com.cometproject.server.game.rooms.objects.entities.types.enums.RoomControllerLevel;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers.WiredTriggerCollision;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers.WiredTriggerPlayerSaysKeyword;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers.custom.WiredTriggerLeavesRoom;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.RoomPromotion;
import com.cometproject.server.game.rooms.types.components.games.GameTeam;
import com.cometproject.server.game.rooms.types.components.games.RoomGame;
import com.cometproject.server.game.rooms.types.components.games.survival.SurvivalGame;
import com.cometproject.server.game.rooms.types.components.games.survival.types.SurvivalPlayer;
import com.cometproject.server.game.rooms.types.components.types.Trade;
import com.cometproject.server.game.rooms.types.mapping.RoomTile;
import com.cometproject.server.game.utilities.DistanceCalculator;
import com.cometproject.server.logging.LogManager;
import com.cometproject.server.logging.entries.RoomVisitLogEntry;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.incoming.room.engine.InitializeRoomMessageEvent;
import com.cometproject.server.network.messages.outgoing.room.access.DoorbellRequestComposer;
import com.cometproject.server.network.messages.outgoing.room.access.RoomReadyMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.alerts.CantConnectMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.alerts.DoorbellNoAnswerComposer;
import com.cometproject.server.network.messages.outgoing.room.alerts.RoomErrorMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.avatar.*;
import com.cometproject.server.network.messages.outgoing.room.engine.*;
import com.cometproject.server.network.messages.outgoing.room.events.RoomPromotionMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.permissions.FloodFilterMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.permissions.YouAreControllerMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.permissions.YouAreOwnerMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.permissions.YouAreSpectatorMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.queue.RoomQueueStatusMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.settings.RoomRatingMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.inventory.PetInventoryMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageComposer;
import com.cometproject.server.storage.queries.pets.RoomPetDao;
import com.cometproject.server.utilities.attributes.Attributable;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Describes player entity behavior for the room subsystem.
 */
public class PlayerEntity extends RoomEntity implements PlayerEntityAccess, Attributable, PlayerRoomEntity {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerEntity.class.getName());
    private Player player;
    private PlayerData playerData;

    private int playerId;

    private Map<String, Object> attributes = new HashMap<>();
    private RoomVisitLogEntry visitLogEntry;

    private boolean isFinalized = false;
    private boolean isKicked = false;

    private GameTeam gameTeam = GameTeam.NONE;
    private int kickWalkStage = 0;

    private boolean isQueueing = false;

    private int banzaiPlayerAchievement = 0;

    private boolean hasPlacedPet = false;

    // BUILDER STUFF
    private boolean builderFillFloor = false;
    private int lastMessageCounter = 0;
    private int betAmount = 0;
    private int betRow = 0;
    private int lifes = 100;

    private boolean survivalMode = false;
    private String bankSent = "";
    private String bankType = "";
    private String lastMessage = "";
    private Set<PlayerItem> user1Items, user2Items;

    public boolean setzok = false;

    /**
     * Creates a player entity instance for the room subsystem.
     *
     * @param player Player participating in the operation.
     * @param identifier Identifier supplied by the caller.
     * @param startPosition Start position supplied by the caller.
     * @param startBodyRotation Start body rotation supplied by the caller.
     * @param startHeadRotation Start head rotation supplied by the caller.
     * @param roomInstance Room instance supplied by the caller.
     */
    public PlayerEntity(Player player, int identifier, Position startPosition, int startBodyRotation, int startHeadRotation, Room roomInstance) {
        super(identifier, startPosition, startBodyRotation, startHeadRotation, roomInstance);

        this.player = player;

        // create reference to the PlayerDa
        this.playerId = player.getId();
        this.playerData = player.getData();

        if (this.player.isInvisible()) {
            this.updateVisibility(false);
        }

        if (this.getPlayer().isTeleporting() && this.getPlayer().getTeleportRoomId() == roomInstance.getId())
            this.setOverriden(true);

        if (LogManager.ENABLED)
            this.visitLogEntry = LogManager.getInstance().getStore().getRoomVisitContainer().put(player.getId(), roomInstance.getId(), Comet.getTime());
    }

    /**
     * Executes join room for this room contract.
     *
     * @param room Room participating in the operation.
     * @param password Password supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean joinRoom(Room room, String password) {
        if (this.isFinalized()) return this.getRoom().getId() == room.getId();

        boolean isAuthFailed = false;
        boolean isSpectating = this.getPlayer().isSpectating(room.getId());

        if (this.getRoom() == null) {
            this.getPlayer().getSession().send(new HotelViewMessageComposer());
            isAuthFailed = true;
        }

        // Room full, no slot available
        if (!isSpectating && !this.getPlayer().hasQueued(room.getId()) && !isAuthFailed && this.getPlayerId() != this.getRoom().getData().getOwnerId() && this.getRoom().getEntities().playerCount() >= this.getRoom().getData().getMaxUsers() &&
                !this.getPlayer().getPermissions().getRank().roomEnterFull()) {

            if (RoomQueue.getInstance().hasQueue(room.getId())) {
                RoomQueue.getInstance().addPlayerToQueue(room.getId(), this.playerId);

                this.isQueueing = true;
                this.getPlayer().getSession().send(new RoomQueueStatusMessageComposer(RoomQueue.getInstance().getQueueCount(room.getId(), this.playerId)));
                return true;
            }

            this.getPlayer().getSession().send(new CantConnectMessageComposer(1));
            this.getPlayer().getSession().send(new HotelViewMessageComposer());
            isAuthFailed = true;
        }

        // Room bans
        if (!isAuthFailed && this.getRoom().getRights().hasBan(this.getPlayerId()) && this.getPlayer().getPermissions().getRank().roomKickable()) {
            this.getPlayer().getSession().send(new CantConnectMessageComposer(4));
            isAuthFailed = true;
        }

        boolean isOwner = (this.getRoom().getData().getOwnerId() == this.getPlayerId());
        boolean isTeleporting = this.getPlayer().isTeleporting() && (this.getPlayer().getTeleportRoomId() == this.getRoom().getId());
        boolean isDoorbell = false;

        if (!isAuthFailed && !this.getPlayer().isBypassingRoomAuth() && (!isOwner && !this.getPlayer().getPermissions().getRank().roomEnterLocked() && !this.isDoorbellAnswered()) && !isTeleporting) {
            if (this.getRoom().getData().getAccess() == RoomAccessType.PASSWORD) {
                boolean matched;

                if (CometSettings.roomEncryptPasswords) {
                    matched = BCrypt.checkpw(password, this.getRoom().getData().getPassword());
                } else {
                    matched = this.getRoom().getData().getPassword().equals(password);
                }

                if (!matched) {
                    this.getPlayer().getSession().send(new RoomErrorMessageComposer(-100002));
                    this.getPlayer().getSession().send(new HotelViewMessageComposer());
                    isAuthFailed = true;
                }
            } else if (this.getRoom().getData().getAccess() == RoomAccessType.DOORBELL) {
                if (!this.getRoom().getRights().hasRights(this.playerId)) {
                    if (this.getRoom().getEntities().playerCount() < 1) {
                        this.getPlayer().getSession().send(new DoorbellNoAnswerComposer());
                        this.getPlayer().getSession().send(new HotelViewMessageComposer());

                        isAuthFailed = true;
                    } else {
                        this.getRoom().getEntities().broadcastMessage(new DoorbellRequestComposer(this.getUsername()), true);
                        this.getPlayer().getSession().send(new DoorbellRequestComposer(""));
                        isAuthFailed = true;
                        isDoorbell = true;
                    }
                }
            }
        }

        this.getPlayer().bypassRoomAuth(false);
        this.getPlayer().setTeleportId(0);
        this.getPlayer().setTeleportRoomId(0);

        this.getPlayer().setRoomQueueId(0);

        if (isAuthFailed) {
            return isDoorbell;
        }

        this.getPlayer().getSession().send(new OpenConnectionMessageComposer());

        this.getRoom().getEntities().addEntity(this);
        this.finalizeJoinRoom();

        return true;
    }

    /**
     * Executes finalize join room for this room contract.
     */
    @Override
    protected void finalizeJoinRoom() {
        Session session = this.player.getSession();

        session.send(new RoomReadyMessageComposer(this.getRoom().getId(), this.getRoom().getModel().getId()));

        for (Map.Entry<String, String> decoration : this.getRoom().getData().getDecorations().entrySet()) {
            if (decoration.getKey().equals("wallpaper") || decoration.getKey().equals("floor")) {
                if (decoration.getValue().equals("0.0")) {
                    continue;
                }
            }

            session.send(new RoomPropertyMessageComposer(decoration.getKey(), decoration.getValue()));
        }

        int accessLevel = this.getControllerLevel().getLevel();

        if (this.getRoom().getData().getOwnerId() == this.getPlayerId() || this.getPlayer().getPermissions().getRank().roomFullControl()) {
            session.send(new YouAreOwnerMessageComposer());
        }

        this.addStatus(RoomEntityStatus.CONTROLLER, Integer.toString(accessLevel));
        session.send(new YouAreControllerMessageComposer(accessLevel));

        boolean isSpectating = this.getPlayer().isSpectating(this.getRoom().getId());

        if (!isSpectating) {
            if (this.getRoom().getData().getRequiredBadge() != null) {
                if (!this.getPlayer().getInventory().hasBadge(this.getRoom().getData().getRequiredBadge())) {
                    isSpectating = true;
                } else if (this.getPlayer().getInventory().getBadges().get(this.getRoom().getData().getRequiredBadge()) == 0) {
                    isSpectating = true;
                }
            }
        }

        if (isSpectating) {
            session.send(new YouAreSpectatorMessageComposer());
            this.updateVisibility(false);
        }

        session.send(new RoomRatingMessageComposer(this.getRoom().getData().getScore(), this.canRateRoom()));

        InitializeRoomMessageEvent.heightmapMessageEvent.handle(session, null);

        if (RoomManager.getInstance().hasPromotion(this.getRoom().getId())) {
            session.send(new RoomPromotionMessageComposer(this.getRoom().getData(), this.getRoom().getPromotion()));
        } else {
            session.send(new RoomPromotionMessageComposer(null, null));
        }

        if(this.getRoom().getId() == CometSettings.currentEventRoom) {
            session.send(new RoomPromotionMessageComposer(this.getRoom().getData(), new RoomPromotion(this.getRoom().getId(), this.getRoom().getData().getName(), "¡Sigue los pasos indicados, pásalo en grande y consigue tu recompensa! ¡Buena suerte!", Comet.getTime(), Comet.getTime() + 1000)));
        }

        if (this.getPlayer().getEntity().isVisible())
            this.getRoom().getEntities().broadcastMessage(new AvatarsMessageComposer(this.getPlayer().getEntity()));

        BattlePassMission ms = BattlePassGlobals.battlePassMissions.stream().filter(x -> x.type == BattlePassMissionEnums.MissionType.VISITROOM).findAny().orElse(null);
        if(ms != null){
            if(this.getPlayer().getData().battlePass != null){
                if(this.getPlayer().getData().battlePass.level == ms.id) this.getPlayer().getData().battlePass.addExperiencePoint(ms.id);
            }
        }

        this.isFinalized = true;
        this.getPlayer().setSpectatorRoomId(0);
        this.getPlayer().getAchievements().progressAchievement(AchievementType.ACH_48, 1);
    }


    /**
     * Indicates whether this room contract can rate room.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canRateRoom() {
        return !this.getRoom().getRatings().contains(this.getPlayerId());
    }

    /**
     * Handles the reached tile callback for this room contract.
     *
     * @param tile Tile supplied by the caller.
     */
    public void onReachedTile(RoomTile tile) {

        final PlayerEntity closestEntity = this.nearestPlayerEntity(this);

        if (closestEntity != null) {
            final int distanceY = DistanceCalculator.calculateY(tile.getPosition(), closestEntity.getPosition());
            final int distanceX = DistanceCalculator.calculateX(tile.getPosition(), closestEntity.getPosition());

            if (distanceX < 2 && distanceY < 2) {
                WiredTriggerCollision.executeTriggers(this, null);
            }
        }
    }

    /**
     * Executes leave room for this room contract.
     *
     * @param isOffline Is offline supplied by the caller.
     * @param isKick Is kick supplied by the caller.
     * @param toHotelView To hotel view supplied by the caller.
     */
    @Override
    public void leaveRoom(boolean isOffline, boolean isKick, boolean toHotelView) {
        if (this.isQueueing) {
            RoomQueue.getInstance().removePlayerFromQueue(this.getRoom().getId(), this.playerId);
        }

        try {
            if (RoomQueue.getInstance().hasQueue(this.getRoom().getId()) && !this.isQueueing) {
                int nextPlayer = RoomQueue.getInstance().getNextPlayer(this.getRoom().getId());

                RoomQueue.getInstance().removePlayerFromQueue(this.getRoom().getId(), nextPlayer);
                Session nextPlayerSession = NetworkManager.getInstance().getSessions().getByPlayerId(nextPlayer);

                if (nextPlayerSession != null) {
                    nextPlayerSession.getPlayer().setRoomQueueId(this.getRoom().getId());

                    if (nextPlayerSession.getPlayer().getEntity() != null && nextPlayerSession.getPlayer().getEntity().getRoom().getId() == this.getRoom().getId()) {
                        nextPlayerSession.send(new RoomForwardMessageComposer(this.getRoom().getId()));
                    }
                }
            }
        } catch (Exception ignored) {

        }

        for (BotEntity entity : this.getRoom().getEntities().getBotEntities()) {
            if (entity.getAI().onPlayerLeave(this)) break;
        }

        for (Map.Entry<Long, RoomItemFloor> floorItem : this.getRoom().getItems().getFloorItems().entrySet()) {
            if (floorItem.getValue() == null) continue;

            floorItem.getValue().onEntityLeaveRoom(this);
        }

        // Check and cancel any active trades
        Trade trade = this.getRoom().getTrade().get(this);

        if (trade != null) {
            trade.cancel(this.getPlayerId());
        }

        if (this.getMountedEntity() != null) {
            this.getMountedEntity().setOverriden(false);
            this.getMountedEntity().setHasMount(false);
        }

        if(this.isSurvivalMode()) {
            Session player = NetworkManager.getInstance().getSessions().getByPlayerId(this.getPlayer().getId());
            if (player == null || player.getPlayer() == null || player.getPlayer().getEntity() == null)
                return;

            final RoomGame game = this.getRoom().getGame().getInstance();

            if (!(game instanceof SurvivalGame))
                return;

            final SurvivalGame survivalGame = (SurvivalGame) game;
            final SurvivalPlayer survivalPlayer = survivalGame.survivalPlayer(this.getPlayerId());
            if (survivalPlayer != null) {
                ((SurvivalGame) game).playerLeaves(this.getPlayer().getData().getId(), false);
            }
        }

        // Step off
        for (RoomItemFloor item : this.getRoom().getItems().getItemsOnSquare(this.getPosition().getX(), this.getPosition().getY())) {
            if (item == null) continue;
            item.onEntityStepOff(this);
        }

        if (isKick && !isOffline && this.getPlayer() != null && this.getPlayer().getSession() != null) {
            this.getPlayer().getSession().send(new RoomErrorMessageComposer(4008));
        }

        // Send leave room message to all instance entities
        this.getRoom().getEntities().broadcastMessage(new LeaveRoomMessageComposer(this.getId()));

        // Sending this user to the hotel view?
        if (!isOffline && toHotelView && this.getPlayer() != null && this.getPlayer().getSession() != null) {
            this.getPlayer().getSession().send(new HotelViewMessageComposer());

            if (this.getPlayer().getData() != null) {
                this.getPlayer().getMessenger().sendStatus(true, false);
            }
        }

        if (this.hasPlacedPet && this.getRoom().getData().getOwnerId() != this.playerId) {
            for (PetEntity petEntity : this.getRoom().getEntities().getPetEntities()) {
                if (petEntity.getData().getOwnerId() == this.getPlayerId()) {
                    RoomPetDao.updatePet(0, 0, 0, petEntity.getData().getId());
                    petEntity.leaveRoom(false);

                    this.getPlayer().getPets().addPet(petEntity.getData());
                    this.getPlayer().getSession().send(new PetInventoryMessageComposer(this.getPlayer().getPets().getPets()));

                }
            }
        }

        for (RoomEntity follower : this.getFollowingEntities()) {
            if (follower instanceof BotEntity) {
                final BotEntity botEntity = ((BotEntity) follower);

                if (botEntity.getData() != null) {
                    if (botEntity.getData().getMode() == BotMode.RELAXED) {
                        botEntity.getData().setMode(BotMode.DEFAULT);
                    }
                }
            }
        }

        // Remove entity from the room
        this.getRoom().getEntities().removeEntity(this);

        if (this.player != null) {
            this.getPlayer().setEntity(null);
        }

        if (this.visitLogEntry != null) {
            this.visitLogEntry.setExitTime((int) Comet.getTime());

            LogManager.getInstance().getStore().getRoomVisitContainer().updateExit(this.visitLogEntry);
        }

        WiredTriggerLeavesRoom.executeTriggers(this);

        this.getStatuses().clear();
        this.attributes.clear();

        // De-reference things
        this.player = null;
        this.playerData = null;
    }

    /**
     * Executes kick for this room contract.
     */
    @Override
    public void kick() {
        this.isKicked = true;
        this.setCanWalk(false);

        this.moveTo(this.getRoom().getModel().getDoorX(), this.getRoom().getModel().getDoorY());
    }

    /**
     * Handles the chat callback for this room contract.
     *
     * @param message Message supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean onChat(String message) {
        final long time = System.currentTimeMillis();

        final boolean isPlayerOnline = PlayerManager.getInstance().isOnline(this.getPlayerId());

        if (!isPlayerOnline) {
            this.leaveRoom(true, false, false);
            return false;
        }

        if (WiredTriggerPlayerSaysKeyword.executeTriggers(this, message)) {
            return false;
        }

        if (this.getPlayer().getListeningPlayers().size() != 0) {
            for (Integer listeningPlayerId : this.getPlayer().getListeningPlayers()) {
                final Session session = NetworkManager.getInstance().getSessions().getByPlayerId(listeningPlayerId);

                if (session != null) {
                    session.send(new WhisperMessageComposer(session.getPlayer().getId(),
                            Locale.get("command.listen.message").replace("%username%",
                                    this.getUsername()).replace("%message%", message)));
                }
            }
        }

        if (!this.getPlayer().getPermissions().getRank().floodBypass() && !Arrays.asList(getRoom().getData().getTags()).contains("noflood")) {
            if (this.lastMessage.equals(message)) {
                this.lastMessageCounter++;

                if (this.lastMessageCounter >= 3) {
                    this.getPlayer().setRoomFloodTime(this.getPlayer().getPermissions().getRank().floodTime());
                }
            } else {
                this.lastMessage = message;
                this.lastMessageCounter = 0;
            }

            if (time - this.getPlayer().getRoomLastMessageTime() < 750) {
                this.getPlayer().setRoomFloodFlag(this.getPlayer().getRoomFloodFlag() + 1);

                if (this.getPlayer().getRoomFloodFlag() >= 3) {
                    this.getPlayer().setRoomFloodTime(this.getPlayer().getPermissions().getRank().floodTime());
                    this.getPlayer().setRoomFloodFlag(0);

                    this.getPlayer().getSession().send(new FloodFilterMessageComposer(player.getRoomFloodTime()));
                }
            } else {
                this.getPlayer().setRoomFloodFlag(0);
            }

            if (this.getPlayer().getRoomFloodTime() >= 1) {
                return false;
            }

            player.setRoomLastMessageTime(time);
            player.setLastMessage(message);
        }

        if (message.isEmpty() || message.length() > 100)
            return false;

        if (!this.getPlayer().getData().getNameColour().equals("000000") || !this.getPlayer().getData().getTag().isEmpty()) {
            this.sendNameChange();
        }

        try {
            if (this.getPlayer() != null && this.getPlayer().getSession() != null) {
                if (CommandManager.getInstance().isCommand(message)) {
                    // FIX For <font> stuff when executing commands.
                    this.getRoom().getEntities().broadcastMessage(new UserNameChangeMessageComposer(this.getRoom().getId(), this.getId(), this.getUsername()));
                    if (CommandManager.getInstance().parse(message, this.getPlayer().getSession()))
                        return false;
                } else if (CommandManager.getInstance().getNotifications().isNotificationExecutor(message, this.getPlayer().getData().getRank())) {
                    CommandManager.getInstance().getNotifications().execute(this.player, message.substring(1));
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error while executing command", e);
            return false;
        }

        if (this.isRoomMuted() && !this.getPlayer().getPermissions().getRank().roomMuteBypass() && this.getRoom().getData().getOwnerId() != this.getPlayerId()) {
            return false;
        }

        if ((this.getRoom().getRights().hasMute(this.getPlayerId()) && !this.getPlayer().getPermissions().getRank().roomMuteBypass())) {
            this.getPlayer().getSession().send(new MutedMessageComposer(this.getRoom().getRights().getMuteTime(this.getPlayerId())));
            return false;
        }

        if(BanManager.getInstance().hasBan(this.getPlayer().getId() + "", BanType.MUTE) && !this.getPlayer().getPermissions().getRank().roomMuteBypass()){
            Ban ban = BanManager.getInstance().get(this.getPlayer().getId() + "");
            if(ban.getType() == BanType.MUTE) {
                long tL = ban.getExpire() - Comet.getTime();

                int timeLeft = (int) tL;
                this.getPlayer().getSession().send(new MutedMessageComposer(timeLeft));
                return false;
            }
        }

        if (this.getRoom().getEntities().playerCount() > 1) {
            this.getPlayer().getQuests().progressQuest(QuestType.SOCIAL_CHAT);
        }

        // AFK SYSTEM
        //this.unIdle();

        return true;
    }

    private void sendNameChange() {
        final StringBuilder username = new StringBuilder();
        if(!(this.getPlayer().getData().getTag() == null || this.getPlayer().getData().getTag().equals("") || this.getPlayer().getData().getTag() == "")){
            final String format = "<font color='#000'>[%s]</font><font color='#%s'> %s</font>";
            final String colour = this.getPlayer().getData().getNameColour();
            final String tag = this.getPlayer().getData().getTag();

            username.append(String.format(format, tag, colour, this.getUsername()));
        }
        else {
            final String format = "<font color='#%s'> %s</font>";
            final String colour = this.getPlayer().getData().getNameColour();

            username.append(String.format(format, colour, this.getUsername()));
        }

        final MessageComposer composer = new UserNameChangeMessageComposer(this.getRoom().getId(), this.getId(), username.toString());

        for (PlayerEntity playerEntity : this.getRoom().getEntities().getPlayerEntities()) {
            if(playerEntity == null || playerEntity.getPlayer() == null || playerEntity.getPlayer().getSettings() == null)
                continue;

            if (!playerEntity.getPlayer().getSettings().isUseOldChat()) {
                playerEntity.getPlayer().getSession().send(composer);
            }
        }
    }

    /**
     * Executes post chat for this room contract.
     *
     * @param message Message supplied by the caller.
     */
    public void postChat(String message) {
        String triggerMessage = message.toLowerCase();

        boolean isDrinkRequest = false;

        for (WaiterAI.Drink drink : WaiterAI.drinks) {
            if (triggerMessage.contains(drink.getTrigger())) {
                isDrinkRequest = true;
            }
        }

        if (isDrinkRequest) {
            final BotEntity nearestBot = this.nearestBotEntity(BotType.WAITER);

            if (nearestBot != null) {
                nearestBot.getAI().onTalk(this, message);
                return;
            }
        }

        for (Map.Entry<Integer, RoomEntity> entity : this.getRoom().getEntities().getAllEntities().entrySet()) {
            if (entity.getValue().getAI() != null)
                entity.getValue().getAI().onTalk(this, message);
        }

        for (RoomEntity roomEntity : this.getRoom().getEntities().getAllEntities().values()) {
            if (roomEntity.getId() != this.getId() && !roomEntity.isIdle())
                roomEntity.lookTo(this.getPosition().getX(), this.getPosition().getY(), false);
        }

        if (!this.getPlayer().getData().getNameColour().equals("000000") || !this.getPlayer().getData().getTag().isEmpty()) {
            this.getRoom().getEntities().broadcastMessage(new UserNameChangeMessageComposer(this.getRoom().getId(), this.getId(), this.getUsername()));
        }
    }

    /**
     * Handles the room dispose callback for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean onRoomDispose() {
        // Clear all  statuses
        this.getStatuses().clear();

        // Send leave room message to all instance entities
        this.getRoom().getEntities().broadcastMessage(new LeaveRoomMessageComposer(this.getId()));

        // Sending this user to the hotel view?
        this.getPlayer().getSession().send(new HotelViewMessageComposer());
        this.getPlayer().getSession().getPlayer().getMessenger().sendStatus(true, false);

        // Check and cancel any active trades
        Trade trade = this.getRoom().getTrade().get(this);

        if (trade != null) {
            trade.cancel(this.getPlayerId());
        }

        if (this.getPlayer() != null) {
            this.getPlayer().setEntity(null);
            this.player = null;
        }

        return false;
    }

    /**
     * Updates the idle for this room contract.
     */
    @Override
    public void setIdle() {
        super.setIdle();

        this.getRoom().getEntities().broadcastMessage(new IdleStatusMessageComposer(this, true));
    }

    /**
     * Returns the player id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPlayerId() {
        return this.playerId;
    }

    /**
     * Updates the player id for this room contract.
     *
     * @param playerId Player identifier used by the operation.
     */
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Returns the username for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getUsername() {
        return this.playerData == null ? "UnknownPlayer" + this.playerId : this.playerData.getUsername();
    }

    /**
     * Returns the motto for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getMotto() {
        return this.playerData == null ? "" : this.playerData.getMotto();
    }

    /**
     * Returns the figure for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getFigure() {
        return this.playerData == null ? "" : this.playerData.getFigure();
    }

    /**
     * Returns the gender for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getGender() {
        return this.playerData == null ? "M" : this.playerData.getGender();
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        if (this.hasAttribute("transformation")) {
            String[] transformationData = ((String) this.getAttribute("transformation")).split("#");

            TransformCommand.composeTransformation(msg, transformationData, this);
            return;
        }

        msg.writeInt(this.getPlayerId());
        msg.writeString(this.getUsername().replace("<", "").replace(">", "")); // Client sometimes parses the username as HTML...
        msg.writeString(this.getMotto());
        msg.writeString(this.getFigure());
        msg.writeInt(this.getId());

        msg.writeInt(this.getPosition().getX());
        msg.writeInt(this.getPosition().getY());
        msg.writeDouble(this.getPosition().getZ());

        msg.writeInt(this.getBodyRotation()); // 2 = user 4 = bot
        msg.writeInt(1); // 1 = user 2 = pet 3 = bot

        msg.writeString(this.getGender().toLowerCase());

        if (this.playerData == null || this.playerData.getFavouriteGroup() == 0) {
            msg.writeInt(-1);
            msg.writeInt(-1);
            msg.writeInt(0);
        } else {
            IGroupData group = GameContext.getCurrent().getGroupService().getData(this.playerData.getFavouriteGroup());

            if (group == null) {
                msg.writeInt(-1);
                msg.writeInt(-1);
                msg.writeInt(0);

                this.playerData.setFavouriteGroup(0);
                this.playerData.save();
            } else {
                msg.writeInt(group.getId());
                msg.writeInt(2);
                msg.writeString(group.getTitle());
                msg.writeString("");
            }
        }

        msg.writeInt(this.playerData == null ? 0 : this.playerData.getAchievementPoints()); //achv points
        msg.writeBoolean(false);
    }

    /**
     * Returns the player for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Releases resources owned by this room component.
     */
    @Deprecated
    public void dispose() {
        this.leaveRoom(true, false, false);
        this.attributes.clear();
        this.user1Items.clear();
        this.user2Items.clear();
    }

    /**
     * Updates the attribute for this room contract.
     *
     * @param attributeKey Attribute key supplied by the caller.
     * @param attributeValue Attribute value supplied by the caller.
     */
    @Override
    public void setAttribute(String attributeKey, Object attributeValue) {
        if (this.attributes.containsKey(attributeKey)) {
            this.attributes.replace(attributeKey, attributeValue);
        } else {
            this.attributes.put(attributeKey, attributeValue);
        }
    }

    /**
     * Returns the attribute for this room contract.
     *
     * @param attributeKey Attribute key supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public Object getAttribute(String attributeKey) {
        return this.attributes.get(attributeKey);
    }

    /**
     * Indicates whether this room contract has attribute.
     *
     * @param attributeKey Attribute key supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean hasAttribute(String attributeKey) {
        return this.attributes.containsKey(attributeKey);
    }

    /**
     * Removes attribute from this room contract.
     *
     * @param attributeKey Attribute key supplied by the caller.
     */
    @Override
    public void removeAttribute(String attributeKey) {
        this.attributes.remove(attributeKey);
    }

    /**
     * Indicates whether finalized applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isFinalized() {
        return isFinalized;
    }

    /**
     * Updates the finalized for this room contract.
     *
     * @param finalized Finalized supplied by the caller.
     */
    public void setFinalized(boolean finalized) {
        isFinalized = finalized;
    }

    /**
     * Returns the game team for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public GameTeam getGameTeam() {
        return gameTeam;
    }

    /**
     * Updates the game team for this room contract.
     *
     * @param gameTeam Game team supplied by the caller.
     */
    public void setGameTeam(GameTeam gameTeam) {
        if (gameTeam == null) {
            this.gameTeam = GameTeam.NONE;
        } else {
            this.gameTeam = gameTeam;
        }
    }

    /**
     * Indicates whether kicked applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isKicked() {
        return isKicked;
    }

    /**
     * Returns the kick walk stage for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getKickWalkStage() {
        return kickWalkStage;
    }

    /**
     * Executes increase kick walk stage for this room contract.
     */
    public void increaseKickWalkStage() {
        this.kickWalkStage++;
    }

    /**
     * Returns the banzai player achievement for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getBanzaiPlayerAchievement() {
        return banzaiPlayerAchievement;
    }

    /**
     * Updates the banzai player achievement for this room contract.
     *
     * @param banzaiPlayerAchievement Banzai player achievement supplied by the caller.
     */
    public void setBanzaiPlayerAchievement(int banzaiPlayerAchievement) {
        this.banzaiPlayerAchievement = banzaiPlayerAchievement;
    }

    /**
     * Executes increment banzai player achievement for this room contract.
     */
    public void incrementBanzaiPlayerAchievement() {
        this.banzaiPlayerAchievement++;
    }

    /**
     * Updates the placed pet for this room contract.
     *
     * @param hasPlacedPet Has placed pet supplied by the caller.
     */
    public void setPlacedPet(boolean hasPlacedPet) {
        this.hasPlacedPet = hasPlacedPet;
    }

    /**
     * Indicates whether this room contract has rights.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasRights() {
        return this.getRoom().getRights().hasRights(this.playerId);
    }

    /**
     * Returns the controller level for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomControllerLevel getControllerLevel() {
        if (this.getPlayer().getPermissions().getRank().roomFullControl()) {
            return RoomControllerLevel.MODERATOR;
        }else if (this.getRoom().getData().getOwnerId() == this.getPlayerId()) {
            return RoomControllerLevel.ROOM_OWNER;
        } else if(this.getRoom().getGroup() != null && this.getRoom().getGroup().getMembers().hasAdminPerm(this.getPlayerId())) {
            return RoomControllerLevel.GUILD_ADMIN;
        } else if(this.getRoom().getGroup() != null && this.getRoom().getGroup().getMembers().hasMembership(this.getPlayerId())) {
            return RoomControllerLevel.GUILD_MEMBER;
        } else if (this.getRoom().getRights().hasRights(this.getPlayerId())) {
            return RoomControllerLevel.GUEST;
        }
        return RoomControllerLevel.NONE;
    }

    /**
     * Indicates whether builder fill floor applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isBuilderFillFloor() {
        return builderFillFloor;
    }

    /**
     * Updates the builder fill floor for this room contract.
     *
     * @param builderFillFloor Builder fill floor supplied by the caller.
     */
    public void setBuilderFillFloor(boolean builderFillFloor) {
        this.builderFillFloor = builderFillFloor;
    }

    /**
     * Updates the bet amount for this room contract.
     *
     * @param a A supplied by the caller.
     */
    public void setBetAmount (int a ){ this.betAmount = a; }

    /**
     * Returns the bet amount for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getBetAmount() { return betAmount; }

    /**
     * Updates the bet row for this room contract.
     *
     * @param betRow Bet row supplied by the caller.
     */
    public void setBetRow(int betRow) { this.betRow = betRow; }

    /**
     * Executes increment bet row for this room contract.
     */
    public void incrementBetRow() { this.betRow++; }

    /**
     * Returns the bet row for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getBetRow() { return betRow; }

    /**
     * Returns the user1 items for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<PlayerItem> getUser1Items() {
        return user1Items;
    }

    /**
     * Returns the user2 items for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<PlayerItem> getUser2Items() {
        return user2Items;
    }

    /**
     * Updates the user1 items for this room contract.
     *
     * @param user1Items User1 items supplied by the caller.
     */
    public void setUser1Items(Set<PlayerItem> user1Items) {
        this.user1Items = user1Items;
    }

    /**
     * Updates the user2 items for this room contract.
     *
     * @param user2Items User2 items supplied by the caller.
     */
    public void setUser2Items(Set<PlayerItem> user2Items) {
        this.user2Items = user2Items;
    }

    /**
     * Returns the bank sent for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getBankSent() { return bankSent; }

    /**
     * Returns the bank type for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getBankType() { return bankType; }

    /**
     * Updates the bank sent for this room contract.
     *
     * @param bankSent Bank sent supplied by the caller.
     */
    public void setBankSent(String bankSent) { this.bankSent = bankSent; }

    /**
     * Updates the bank type for this room contract.
     *
     * @param bankType Bank type supplied by the caller.
     */
    public void setBankType(String bankType) { this.bankType = bankType; }

    /**
     * Returns the lifes for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLifes() {
        return lifes;
    }

    /**
     * Executes decrement lifes for this room contract.
     */
    public void decrementLifes() {
        this.lifes--;
    }

    /**
     * Indicates whether survival mode applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isSurvivalMode() {
        return survivalMode;
    }

    /**
     * Updates the survival mode for this room contract.
     *
     * @param survivalMode Survival mode supplied by the caller.
     */
    public void setSurvivalMode(boolean survivalMode) {
        this.survivalMode = survivalMode;
    }
}
