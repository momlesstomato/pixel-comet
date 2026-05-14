package com.cometproject.server.game.rooms.objects.entities;

import com.cometproject.api.game.rooms.entities.RoomEntityStatus;
import com.cometproject.api.game.rooms.models.RoomTileState;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.server.game.rooms.objects.RoomFloorObject;
import com.cometproject.server.game.rooms.objects.RoomObject;
import com.cometproject.server.game.rooms.objects.entities.effects.PlayerEffect;
import com.cometproject.server.game.rooms.objects.entities.pathfinding.Square;
import com.cometproject.server.game.rooms.objects.entities.pathfinding.types.EntityPathfinder;
import com.cometproject.server.game.rooms.objects.entities.types.BotEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PetEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.entities.types.ai.BotAI;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers.custom.WiredTriggerCustomIdle;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers.custom.WiredTriggerCustomIdleV2;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers.custom.WiredTriggerUsersCollide;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.mapping.RoomEntityMovementNode;
import com.cometproject.server.game.rooms.types.mapping.RoomTile;
import com.cometproject.server.network.messages.outgoing.room.avatar.*;
import com.cometproject.server.network.messages.outgoing.room.items.SendShadowMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.items.SlideObjectBundleMessageComposer;
import com.cometproject.server.utilities.collections.ConcurrentHashSet;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Describes room entity behavior for the room subsystem.
 */
public abstract class RoomEntity extends RoomFloorObject implements AvatarEntity {
    public int updatePhase = 0;
    public boolean needsForcedUpdate = false;
    private RoomEntityType entityType;
    private Position walkingGoal;
    private Position positionToSet;
    private int bodyRotation;
    private int headRotation;
    private List<Square> processingPath;
    private List<Square> walkingPath;
    private Square futureSquare;
    private int previousSteps = 0;
    private int diceCount = 0;
    private int idleTime;
    private int signTime;
    private int danceId;
    private int statusType;
    private PlayerEffect teamEffect;
    private PlayerEffect lastEffect;
    private PlayerEffect effect;
    private int handItem;
    private int handItemTimer;
    private boolean needsUpdate;
    private boolean isMoonwalking;
    private boolean overriden;
    private boolean isVisible;
    private boolean cancelNextUpdate;
    private boolean doorbellAnswered;
    private boolean walkCancelled = false;
    private boolean canWalk = true;
    private boolean isIdle = false;
    private boolean isForcedIdle = false;
    private boolean isTransformed = false;
    private boolean isBodyRotating = false;
    private boolean isHeadRotating = false;
    private int afkTime = 0;

    public boolean floorEditCustom = true;

    public ArrayList<Object> addTagUser;

    private final Set<RoomTile> tiles = Sets.newConcurrentHashSet();

    private boolean isRoomMuted = false;

    private long joinTime;

    private RoomEntity mountedEntity;
    private Set<RoomEntity> followingEntities = new ConcurrentHashSet<>();

    private long privateChatItemId = 0;

    private Map<RoomEntityStatus, String> statuses = new ConcurrentHashMap<>();
    private Position pendingWalk;

    private boolean fastWalkEnabled = false;
    private boolean isWarped;
    private boolean isOneGate;
    private RoomTile warpedTile;
    private boolean sendUpdateMessage = true;
    private boolean hasMount = false;

    private boolean warping;

    /**
     * Creates a room entity instance for the room subsystem.
     *
     * @param identifier Identifier supplied by the caller.
     * @param startPosition Start position supplied by the caller.
     * @param startBodyRotation Start body rotation supplied by the caller.
     * @param startHeadRotation Start head rotation supplied by the caller.
     * @param roomInstance Room instance supplied by the caller.
     */
    public RoomEntity(int identifier, Position startPosition, int startBodyRotation, int startHeadRotation, Room roomInstance) {
        super(identifier, startPosition, roomInstance);

        if (this instanceof PlayerEntity) {
            this.entityType = RoomEntityType.PLAYER;
        } else if (this instanceof BotEntity) {
            this.entityType = RoomEntityType.BOT;
        } else if (this instanceof PetEntity) {
            this.entityType = RoomEntityType.PET;
        }

        this.bodyRotation = startBodyRotation;
        this.headRotation = startHeadRotation;

        this.idleTime = 0;
        this.signTime = 0;
        this.handItem = 0;
        this.handItemTimer = 0;

        this.danceId = 0;

        this.needsUpdate = false;
        this.isMoonwalking = false;
        this.overriden = false;
        this.isVisible = true;
        this.cancelNextUpdate = false;

        this.addTagUser = new ArrayList<Object>();

        this.doorbellAnswered = false;

        if (this.getRoom().hasRoomMute()) {
            this.isRoomMuted = true;
        }

        this.joinTime = System.currentTimeMillis();
    }

    /**
     * Returns the entity type for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomEntityType getEntityType() {
        return this.entityType;
    }

    /**
     * Returns the walking goal for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Position getWalkingGoal() {
        if (this.walkingGoal == null) {
            return this.getPosition();
        } else {
            return this.walkingGoal;
        }
    }

    /**
     * Updates the walking goal for this room contract.
     *
     * @param x X supplied by the caller.
     * @param y Y supplied by the caller.
     */
    @Override
    public void setWalkingGoal(int x, int y) {
        this.walkingGoal = new Position(x, y, 0.0);
    }

    /**
     * Executes move to for this room contract.
     *
     * @param position Position supplied by the caller.
     */
    public void moveTo(Position position) {
        this.moveTo(position.getX(), position.getY());
    }

    /**
     * Executes slide player for this room contract.
     *
     * @param client Client supplied by the caller.
     * @param user User supplied by the caller.
     */
    public void slidePlayer(Position client, Position user) {
        this.getRoom().getEntities().broadcastMessage(new SlideObjectBundleMessageComposer(user, client, 0, this.getId(), 007));
    }

    /**
     * Executes move to for this room contract.
     *
     * @param x X supplied by the caller.
     * @param y Y supplied by the caller.
     */
    @Override
    public void moveTo(int x, int y) {
        RoomTile tile = this.getRoom().getMapping().getTile(x, y);

        if (this.hasAttribute("teleport")) {
            Position position = new Position(x, y, this.getRoom().getMapping().getTile(x, y).getWalkHeight());
            // this.warp(position);
            this.slidePlayer(position, this.getPosition());
            this.updateAndSetPosition(position);
            this.markNeedsUpdate();
            this.getRoom().getProcess().updateEntityStuff(this);
            final RoomTile oldTile = this.getRoom().getMapping().getTile(this.getPosition().getX(), this.getPosition().getY());

            if (oldTile != null) {
                oldTile.getEntities().remove(this);
            }

            if (tile != null) {
                tile.getEntities().add(this);
            }
            return;
        }

        if (tile == null) {
            return;
        }

        if (!this.hasAttribute("interacttpencours")) {
            if (tile.getState() == RoomTileState.INVALID || tile.getMovementNode() == RoomEntityMovementNode.CLOSED) {
                if (tile.getRedirect() == null) {
                    return;
                }
            }
        }

        // reassign the position values if they're set to redirect
        if (tile.getRedirect() != null) {
            x = tile.getRedirect().getX();
            y = tile.getRedirect().getY();
        }

        this.previousSteps = 0;

        if (this.getPositionToSet() != null) {
            RoomTile oldTile = this.getRoom().getMapping().getTile(this.getPosition());
            RoomTile newTile = this.getRoom().getMapping().getTile(this.getPositionToSet());

            if (oldTile != null) {
                oldTile.getEntities().remove(this);
            }

            if (newTile != null) {
                newTile.getEntities().add(this);
            }

            this.setPosition(this.getPositionToSet());
        }

        if(this instanceof PlayerEntity) {
            switch (((PlayerEntity)this).getPlayer().getShadowStatus()) {
                case 1:
                    ((PlayerEntity)this).getPlayer().setShadow(2);
                    ((PlayerEntity)this).getPlayer().getSession().send(new SendShadowMessageComposer(((PlayerEntity)this).getPlayerId(), tile.getPosition()));
                    break;
                case 2:
                    ((PlayerEntity)this).getPlayer().getSession().send(new SlideObjectBundleMessageComposer(this.getPosition(), this.getPosition().squareInFront(this.getBodyRotation()), ((PlayerEntity)this).getPlayerId()));
            }
        }

        // Set the goal we are wanting to achieve
        this.setWalkingGoal(x, y);

        // Create a walking path
        List<Square> path = EntityPathfinder.getInstance().makePath(this, new Position(x, y), (this.getRoom().hasAttribute("disableDiagonal")) ? (byte) 0 : (byte) 1, false);

        // Check returned path to see if it calculated one
        if (path == null || path.size() == 0) {
            path = EntityPathfinder.getInstance().makePath(this, new Position(x, y), (this.getRoom().hasAttribute("disableDiagonal")) ? (byte) 0 : (byte) 1, true);

            if (path == null || path.size() == 0) {
                // Reset the goal and return as no path was found
                this.setWalkingGoal(this.getPosition().getX(), this.getPosition().getY());
                return;
            }
        }

        //
//        if(this.isFastWalkEnabled()) {
//            List<Square> newPath = new ArrayList<>();
//
//            boolean add = false;
//            for(Square square : path) {
//                if(add) {
//                    newPath.add(square);
//                    add = false;
//                } else {
//                    add = true;
//                }
//            }
//
//            path.clear();
//            path = newPath;
//        }

        // UnIdle the user and set the path (if the path has nodes it will mean the user is walking)
        this.unIdle();
        this.resetAfkTimer();
        this.setWalkingPath(path);

        Position collisionCheck;

        switch (this.bodyRotation){
            case 4:
                collisionCheck = new Position(this.getPosition().getX(), this.getPosition().getY() + 1);
                break;
            case 0:
                collisionCheck = new Position(this.getPosition().getX(), this.getPosition().getY() - 1);
                break;
            case 6:
                collisionCheck = new Position(this.getPosition().getX() - 1, this.getPosition().getY());
                break;
            default:
                collisionCheck = new Position(this.getPosition().getX() + 1, this.getPosition().getY());
                break;
        }

        RoomTile collisionTile = this.getRoom().getMapping().getTile(collisionCheck);
        if(collisionTile != null) {
            for (RoomEntity c : collisionTile.getEntities()) {
                if (c instanceof PlayerEntity && c.getPosition().touching(this.getPosition()) && c != this) {
                    WiredTriggerUsersCollide.executeTriggers(c);
                }
            }
        }
    }

    /**
     * Executes sit for this room contract.
     *
     * @param height Height supplied by the caller.
     * @param rotation Rotation supplied by the caller.
     */
    public void sit(double height, int rotation) {
        this.removeStatus(RoomEntityStatus.LAY);

        this.addStatus(RoomEntityStatus.SIT, String.valueOf(height).replace(",", "."));
        this.setHeadRotation(getSitRotation(rotation));
        this.setBodyRotation(getSitRotation(rotation));
        this.markNeedsUpdate(true);
    }

    private int getSitRotation(int rotation) {
        switch (rotation) {
            case 1:
            case 3: {
                rotation++;
                break;
            }
            case 5: {
                rotation++;
            }
        }
        return rotation;
    }

    /**
     * Executes look to for this room contract.
     *
     * @param x X supplied by the caller.
     * @param y Y supplied by the caller.
     * @param body Body supplied by the caller.
     */
    public void lookTo(int x, int y, boolean body) {
        if (x == this.getPosition().getX() && y == this.getPosition().getY())
            return;

        final int currentRotation = this.bodyRotation;
        final int rotation = Position.calculateRotation(this.getPosition().getX(), this.getPosition().getY(), x, y, false);

        int rotationDifference = currentRotation - rotation;

        // IDLE SYSTEM
        //this.unIdle();

        if (!this.hasStatus(RoomEntityStatus.SIT) && !this.hasStatus(RoomEntityStatus.LAY)) {
            if (rotationDifference == 1 || rotationDifference == -1 || rotationDifference == -7) {
                this.setHeadRotation(rotation);
            } else if (body) {
                this.setHeadRotation(rotation);
                this.setBodyRotation(rotation);
            }

            this.markNeedsUpdate();
        }
    }

    /**
     * Executes look to for this room contract.
     *
     * @param x X supplied by the caller.
     * @param y Y supplied by the caller.
     */
    public void lookTo(int x, int y) {
        lookTo(x, y, true);
    }

    /**
     * Returns the position to set for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Position getPositionToSet() {
        return this.positionToSet;
    }

    /**
     * Updates and set position for this room contract.
     *
     * @param pos Pos supplied by the caller.
     */
    @Override
    public void updateAndSetPosition(Position pos) {
        this.positionToSet = pos;
    }

    /**
     * Executes mark position is set for this room contract.
     */
    @Override
    public void markPositionIsSet() {
        this.positionToSet = null;
    }

    /**
     * Indicates whether this room contract has position to set.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasPositionToSet() {
        return (this.positionToSet != null);
    }

    /**
     * Returns the body rotation for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getBodyRotation() {
        return this.bodyRotation;
    }

    /**
     * Updates the body rotation for this room contract.
     *
     * @param rotation Rotation supplied by the caller.
     */
    @Override
    public void setBodyRotation(int rotation) {
        this.bodyRotation = rotation;
    }

    /**
     * Returns the head rotation for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getHeadRotation() {
        return this.headRotation;
    }

    /**
     * Updates the head rotation for this room contract.
     *
     * @param rotation Rotation supplied by the caller.
     */
    @Override
    public void setHeadRotation(int rotation) {
        this.headRotation = rotation;
    }

    /**
     * Returns the processing path for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public List<Square> getProcessingPath() {
        return this.processingPath;
    }

    /**
     * Updates the processing path for this room contract.
     *
     * @param path Path supplied by the caller.
     */
    @Override
    public void setProcessingPath(List<Square> path) {
        this.processingPath = path;
    }

    /**
     * Returns the walking path for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public List<Square> getWalkingPath() {
        return this.walkingPath;
    }

    /**
     * Updates the walking path for this room contract.
     *
     * @param path Path supplied by the caller.
     */
    @Override
    public void setWalkingPath(List<Square> path) {
        if (this.walkingPath != null) {
            this.walkingPath.clear();
        }

        this.walkingPath = path;
    }

    /**
     * Indicates whether walking applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isWalking() {
        return (this.processingPath != null) && (this.processingPath.size() > 0);
    }

    /**
     * Returns the future square for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Square getFutureSquare() {
        return this.futureSquare;
    }

    /**
     * Updates the future square for this room contract.
     *
     * @param square Square supplied by the caller.
     */
    @Override
    public void setFutureSquare(Square square) {
        this.futureSquare = square;
    }

    /**
     * Returns the statuses for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Map<RoomEntityStatus, String> getStatuses() {
        return this.statuses;
    }

    /**
     * Adds status to this room contract.
     *
     * @param key Key supplied by the caller.
     * @param value Value supplied by the caller.
     */
    @Override
    public void addStatus(RoomEntityStatus key, String value) {
        if (this.statuses.containsKey(key)) {
            this.statuses.replace(key, value);
        } else {
            this.statuses.put(key, value);
        }
    }

    /**
     * Removes status from this room contract.
     *
     * @param status Status supplied by the caller.
     */
    @Override
    public void removeStatus(RoomEntityStatus status) {
        if (!this.statuses.containsKey(status)) {
            return;
        }

        this.statuses.remove(status);
    }

    /**
     * Indicates whether this room contract has status.
     *
     * @param key Key supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean hasStatus(RoomEntityStatus key) {
        return this.statuses.containsKey(key);
    }

    /**
     * Executes mark needs update for this room contract.
     */
    @Override
    public void markNeedsUpdate() {
        this.needsUpdate = true;
    }

    /**
     * Updates the send update message for this room contract.
     *
     * @param sendUpdateMessage Send update message supplied by the caller.
     */
    public void setSendUpdateMessage(boolean sendUpdateMessage) {
        this.sendUpdateMessage = sendUpdateMessage;
    }

    /**
     * Executes mark needs update for this room contract.
     *
     * @param sendMessage Send message supplied by the caller.
     */
    public void markNeedsUpdate(boolean sendMessage) {
        this.needsUpdate = true;
        this.sendUpdateMessage = sendMessage;
    }

    /**
     * Executes mark update complete for this room contract.
     */
    public void markUpdateComplete() {
        this.needsUpdate = false;
    }

    /**
     * Executes needs update for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean needsUpdate() {
        return this.needsUpdate;
    }

    /**
     * Indicates whether moonwalking applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isMoonwalking() {
        return this.isMoonwalking;
    }

    /**
     * Indicates whether forced idle applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isForcedIdle() { return this.isForcedIdle; }

    /**
     * Updates the is moonwalking for this room contract.
     *
     * @param isMoonwalking Is moonwalking supplied by the caller.
     */
    public void setIsMoonwalking(boolean isMoonwalking) {
        this.isMoonwalking = isMoonwalking;
    }

    /**
     * Returns the idle time for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getIdleTime() {
        return this.idleTime;
    }

    /**
     * Indicates whether idle applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isIdle() {
        return this.idleTime >= 600;
    }

    /**
     * Indicates whether idle and increment applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isIdleAndIncrement() {
        this.idleTime++;

        if(!this.isWalking()) this.afkTime++;

        if(this.isForcedIdle){
            this.getRoom().getEntities().broadcastMessage(new IdleStatusMessageComposer((PlayerEntity) this, true));
            return true;
        }

        if (this.idleTime >= 60000) {
            if (!this.isIdle) {
                this.isIdle = true;
                this.getRoom().getEntities().broadcastMessage(new IdleStatusMessageComposer((PlayerEntity) this, true));
            }
            return true;
        }

        if(WiredTriggerCustomIdle.executeTriggers((PlayerEntity)this) || WiredTriggerCustomIdleV2.executeTriggers((PlayerEntity)this, this.afkTime)) {
            this.resetAfkTimer();
            this.afkTime = 0;
        }

        return false;
    }

    /**
     * Executes reset idle time for this room contract.
     */
    @Override
    public void resetIdleTime() {
        this.idleTime = 0;
    }

    /**
     * Updates the idle for this room contract.
     */
    @Override
    public void setIdle() {
        this.idleTime = 600;
    }

    /**
     * Updates the idle status for this room contract.
     *
     * @param value Value supplied by the caller.
     */
    @Override
    public void setIdleStatus(boolean value) {
        this.isForcedIdle = value;
    }

    /**
     * Executes hand item needs remove for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean handItemNeedsRemove() {
        if (this.handItemTimer == -999)
            return false;

        this.handItemTimer--;

        return this.handItemTimer <= 0;
    }

    /**
     * Executes un idle for this room contract.
     */
    public void unIdle() {
        final boolean sendUpdate = this.isIdle;
        this.isIdle = false;
        this.resetIdleTime();

        if (this instanceof BotEntity) {
            return;
        }

        if (sendUpdate)
        this.getRoom().getEntities().broadcastMessage(new IdleStatusMessageComposer((PlayerEntity) this, false));

        if (this.isForcedIdle) {
            this.getRoom().getEntities().broadcastMessage(new ApplyEffectMessageComposer(this.getId(), 0));
            RoomEntity playerE = this.getRoom().getEntities().getEntity(this.getId());
            PlayerEntity p = ((PlayerEntity) playerE);
            p.getPlayer().getData().setMotto(p.getPlayer().getData().getLegacyMotto());
            this.getRoom().getEntities().broadcastMessage(new UpdateInfoMessageComposer(p.getPlayer().getEntity()));
            this.setIdleStatus(false);
            this.getRoom().getEntities().broadcastMessage(new IdleStatusMessageComposer((PlayerEntity) this, false));
        }

    }

    /**
     * Executes reset afk timer for this room contract.
     */
    public void resetAfkTimer() {
        this.afkTime = 0;
    }

    /**
     * Returns the sign time for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getSignTime() {
        return this.signTime;
    }

    /**
     * Executes mark displaying sign for this room contract.
     */
    @Override
    public void markDisplayingSign() {
        this.signTime = 6;
    }

    /**
     * Indicates whether displaying sign applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isDisplayingSign() {
        this.signTime--;

        if (this.signTime <= 0) {
            if (this.signTime < 0) {
                this.signTime = 0;
            }

            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns the dance id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getDanceId() {
        return this.danceId;
    }

    /**
     * Updates the dance id for this room contract.
     *
     * @param danceId Dance id supplied by the caller.
     */
    @Override
    public void setDanceId(int danceId) {
        this.danceId = danceId;
    }

    /**
     * Returns the current effect for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public PlayerEffect getCurrentEffect() {
        return this.effect;
    }

    /**
     * Returns the hand item for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getHandItem() {
        return this.handItem;
    }

    /**
     * Executes carry item for this room contract.
     *
     * @param id Id supplied by the caller.
     */
    @Override
    public void carryItem(int id) {
        this.carryItem(id, 240);
    }

    /**
     * Executes carry item for this room contract.
     *
     * @param id Id supplied by the caller.
     * @param timer Timer supplied by the caller.
     */
    public void carryItem(int id, int timer) {
        this.handItem = id;
        this.handItemTimer = timer;

        this.getRoom().getEntities().broadcastMessage(new HandItemMessageComposer(this.getId(), handItem));
    }

    /**
     * Executes carry item for this room contract.
     *
     * @param id Id supplied by the caller.
     * @param timer Timer supplied by the caller.
     */
    @Override
    public void carryItem(int id, boolean timer) {
        if (timer) {
            this.carryItem(id);
            return;
        }

        this.handItem = id;
        this.handItemTimer = -999;

        this.getRoom().getEntities().broadcastMessage(new HandItemMessageComposer(this.getId(), handItem));
    }

    /**
     * Returns the hand item timer for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getHandItemTimer() {
        return this.handItemTimer;
    }

    /**
     * Updates the hand item timer for this room contract.
     *
     * @param time Time supplied by the caller.
     */
    @Override
    public void setHandItemTimer(int time) {
        this.handItemTimer = time;
    }

    /**
     * Executes apply effect for this room contract.
     *
     * @param effect Effect supplied by the caller.
     */
    @Override
    public void applyEffect(PlayerEffect effect) {
        if(this instanceof PetEntity)
            return;

        if (effect == null) {
            if (this.teamEffect != null && this.effect != null) {
                this.applyEffect(teamEffect);
                return;
            }


            this.getRoom().getEntities().broadcastMessage(new ApplyEffectMessageComposer(this.getId(), 0));

        } else {
            this.getRoom().getEntities().broadcastMessage(new ApplyEffectMessageComposer(this.getId(), effect.getEffectId()));
        }

        if (effect != null && effect.expires()) {
            this.lastEffect = this.effect;
        }

        this.effect = effect;
    }

    /**
     * Executes apply team effect for this room contract.
     *
     * @param effect Effect supplied by the caller.
     */
    public void applyTeamEffect(PlayerEffect effect) {
        this.teamEffect = effect;

        this.applyEffect(effect);
    }

    /**
     * Indicates whether overriden applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isOverriden() {
        return this.overriden;
    }

    /**
     * Updates the overriden for this room contract.
     *
     * @param overriden Overriden supplied by the caller.
     */
    public void setOverriden(boolean overriden) {
        this.overriden = overriden;
    }

    /**
     * Executes join room for this room contract.
     *
     * @param room Room participating in the operation.
     * @param password Password supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public abstract boolean joinRoom(Room room, String password);

    /**
     * Executes finalize join room for this room contract.
     */
    protected abstract void finalizeJoinRoom();

    /**
     * Executes leave room for this room contract.
     *
     * @param isOffline Is offline supplied by the caller.
     * @param isKick Is kick supplied by the caller.
     * @param toHotelView To hotel view supplied by the caller.
     */
    public abstract void leaveRoom(boolean isOffline, boolean isKick, boolean toHotelView);

    /**
     * Handles the chat callback for this room contract.
     *
     * @param message Message supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public abstract boolean onChat(String message);

    /**
     * Handles the room dispose callback for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public abstract boolean onRoomDispose();

    /**
     * Indicates whether visible applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Updates visibility for this room contract.
     *
     * @param isVisible Is visible supplied by the caller.
     */
    public void updateVisibility(boolean isVisible) {
        if (isVisible && !this.isVisible) {
            this.getRoom().getEntities().broadcastMessage(new AvatarsMessageComposer(this));
        } else {
            this.getRoom().getEntities().broadcastMessage(new LeaveRoomMessageComposer(this.getId()));
        }

        this.isVisible = isVisible;
    }

    /**
     * Executes refresh for this room contract.
     */
    public void refresh() {
        this.updateVisibility(false);
        this.updateVisibility(true);
        this.markNeedsUpdate();
    }

    /**
     * Indicates whether this room contract can cel walk.
     */
    public void cancelWalk() {
        this.setWalkCancelled(true);
        this.markNeedsUpdate();
    }

    /**
     * Indicates whether head rotating applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isHeadRotating() {
        return isHeadRotating;
    }

    /**
     * Updates the head rotating for this room contract.
     *
     * @param value Value supplied by the caller.
     */
    public void setHeadRotating(boolean value) {
        this.isHeadRotating = value;
    }

    /**
     * Indicates whether body rotating applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isBodyRotating() {
        return isBodyRotating;
    }

    /**
     * Updates the body rotating for this room contract.
     *
     * @param value Value supplied by the caller.
     */
    public void setBodyRotating(boolean value) {
        this.isBodyRotating = value;
    }

    /**
     * Executes teleport to item for this room contract.
     *
     * @param itemFloor Item floor supplied by the caller.
     */
    public void teleportToItem(RoomItemFloor itemFloor) {
        this.teleportToObject(itemFloor);
    }

    /**
     * Executes teleport to entity for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    public void teleportToEntity(RoomEntity entity) {
        this.teleportToObject(entity);
    }

    /**
     * Executes teleport to object for this room contract.
     *
     * @param roomObject Room object supplied by the caller.
     */
    public void teleportToObject(RoomObject roomObject) {
        this.applyEffect(new PlayerEffect(4, 5));

        this.warpedTile = this.getRoom().getMapping().getTile(this.getPosition());

        this.cancelWalk();
        this.warp(roomObject.getPosition());
    }

    /**
     * Executes warp for this room contract.
     *
     * @param position Position supplied by the caller.
     * @param cancelNextUpdate Cancel next update supplied by the caller.
     */
    public void warp(Position position, boolean cancelNextUpdate) {
        this.setAttribute("tpencours", true);

        if (cancelNextUpdate) {
            this.cancelNextUpdate();
        } else {
            this.updatePhase = 1;
        }

        this.needsForcedUpdate = true;

        this.updateAndSetPosition(position);
        this.markNeedsUpdate();

        final RoomTile tile = this.getRoom().getMapping().getTile(position);
        if (tile != null) {
            this.addToTile(tile);
        }
    }

    /**
     * Executes warp for this room contract.
     *
     * @param position Position supplied by the caller.
     */
    @Override
    public void warp(Position position) {
        //if (this.needsForcedUpdate) return;

        //this.warping = true;
        this.warp(position, true);
    }

    /**
     * Executes warp immediately for this room contract.
     *
     * @param position Position supplied by the caller.
     */
    @Override
    public void warpImmediately(Position position) {
        this.setPosition(position);
        this.getRoom().getEntities().broadcastMessage(new AvatarUpdateMessageComposer(this));
    }

    /**
     * Executes needs update cancel for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean needsUpdateCancel() {
        if (this.cancelNextUpdate) {
            this.cancelNextUpdate = false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Indicates whether this room contract can cel next update.
     */
    public void cancelNextUpdate() {
        this.cancelNextUpdate = true;
    }

    /**
     * Indicates whether doorbell answered applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isDoorbellAnswered() {
        return this.doorbellAnswered;
    }

    /**
     * Updates the doorbell answered for this room contract.
     *
     * @param b B supplied by the caller.
     */
    public void setDoorbellAnswered(boolean b) {
        this.doorbellAnswered = b;
    }

    /**
     * Returns the last effect for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public PlayerEffect getLastEffect() {
        return lastEffect;
    }

    /**
     * Updates the last effect for this room contract.
     *
     * @param lastEffect Last effect supplied by the caller.
     */
    public void setLastEffect(PlayerEffect lastEffect) {
        this.lastEffect = lastEffect;
    }

    /**
     * Indicates whether walk cancelled applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isWalkCancelled() {
        return walkCancelled;
    }

    /**
     * Updates the walk cancelled for this room contract.
     *
     * @param walkCancelled Walk cancelled supplied by the caller.
     */
    public void setWalkCancelled(boolean walkCancelled) {
        this.walkCancelled = walkCancelled;
    }

    /**
     * Returns the mounted entity for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomEntity getMountedEntity() {
        if (this.mountedEntity == null) return null;

        if (this.getRoom().getEntities().getEntity(this.mountedEntity.getId()) == null) {
            return null;
        }

        return mountedEntity;
    }

    /**
     * Updates the mounted entity for this room contract.
     *
     * @param mountedEntity Mounted entity supplied by the caller.
     */
    public void setMountedEntity(RoomEntity mountedEntity) {
        this.mountedEntity = mountedEntity;
    }

    /**
     * Indicates whether this room contract has mount.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasMount() {
        return hasMount;
    }

    /**
     * Updates the has mount for this room contract.
     *
     * @param hasMount Has mount supplied by the caller.
     */
    public void setHasMount(boolean hasMount) {
        this.hasMount = hasMount;
    }

    /**
     * Executes kick for this room contract.
     */
    @Override
    public void kick() {
        this.leaveRoom(false, true, true);
    }

    /**
     * Indicates whether this room contract can walk.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canWalk() {
        return canWalk;
    }

    /**
     * Updates the can walk for this room contract.
     *
     * @param canWalk Can walk supplied by the caller.
     */
    public void setCanWalk(boolean canWalk) {
        this.canWalk = canWalk;
    }

    /**
     * Executes equals for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean equals(Object entity) {
        if (entity instanceof RoomEntity) {
            return ((RoomEntity) entity).getId() == this.getId();
        }

        return false;
    }

    /**
     * Indicates whether room muted applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isRoomMuted() {
        return isRoomMuted;
    }

    /**
     * Updates the room muted for this room contract.
     *
     * @param isRoomMuted Is room muted supplied by the caller.
     */
    public void setRoomMuted(boolean isRoomMuted) {
        this.isRoomMuted = isRoomMuted;
    }

    /**
     * Returns the join time for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public long getJoinTime() {
        return joinTime;
    }

    /**
     * Returns the private chat item id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public long getPrivateChatItemId() {
        return privateChatItemId;
    }

    /**
     * Indicates whether one gate applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isOneGate() {
        return isOneGate;
    }

    /**
     * Updates the is one gate for this room contract.
     *
     * @param v V supplied by the caller.
     */
    public void setIsOneGate(boolean v){
        this.isOneGate = v;
    }

    /**
     * Updates the private chat item id for this room contract.
     *
     * @param privateChatItemId Private chat item id supplied by the caller.
     */
    public void setPrivateChatItemId(long privateChatItemId) {
        this.privateChatItemId = privateChatItemId;
    }

    /**
     * Returns the ai for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public BotAI getAI() {
        return null;
    }

    /**
     * Returns the following entities for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<RoomEntity> getFollowingEntities() {
        return followingEntities;
    }

    /**
     * Returns the pending walk for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Position getPendingWalk() {
        return pendingWalk;
    }

    /**
     * Updates the pending walk for this room contract.
     *
     * @param pendingWalk Pending walk supplied by the caller.
     */
    public void setPendingWalk(Position pendingWalk) {
        this.pendingWalk = pendingWalk;
    }

    /**
     * Executes toggle fast walk for this room contract.
     */
    public void toggleFastWalk() {
        this.fastWalkEnabled = !this.fastWalkEnabled;
    }

    /**
     * Indicates whether fast walk enabled applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isFastWalkEnabled() {
        return this.fastWalkEnabled;
    }

    /**
     * Updates the fast walk enabled for this room contract.
     *
     * @param fastWalkEnabled Fast walk enabled supplied by the caller.
     */
    public void setFastWalkEnabled(boolean fastWalkEnabled) {
        this.fastWalkEnabled = fastWalkEnabled;
    }

    /**
     * Indicates whether warped applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isWarped() {
        return isWarped;
    }

    /**
     * Updates the warped for this room contract.
     *
     * @param warped Warped supplied by the caller.
     */
    public void setWarped(boolean warped) {
        isWarped = warped;
    }

    /**
     * Returns the previous steps for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPreviousSteps() {
        return previousSteps;
    }

    /**
     * Executes increment previous steps for this room contract.
     */
    public void incrementPreviousSteps() {
        this.previousSteps++;
    }

    /**
     * Executes send update message for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean sendUpdateMessage() {
        return sendUpdateMessage;
    }

    /**
     * Indicates whether warping applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isWarping() {
        return warping;
    }

    /**
     * Updates the warping for this room contract.
     *
     * @param warping Warping supplied by the caller.
     */
    public void setWarping(boolean warping) {
        this.warping = warping;
    }

    /**
     * Returns the warped tile for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomTile getWarpedTile() {
        return warpedTile;
    }

    /**
     * Updates the warped tile for this room contract.
     *
     * @param warpedTile Warped tile supplied by the caller.
     */
    public void setWarpedTile(RoomTile warpedTile) {
        this.warpedTile = warpedTile;
    }

    /**
     * Removes from tile from this room contract.
     *
     * @param tile Tile supplied by the caller.
     */
    public void removeFromTile(RoomTile tile) {
        tile.getEntities().remove(this);
        this.tiles.remove(tile);
    }

    /**
     * Adds to tile to this room contract.
     *
     * @param tile Tile supplied by the caller.
     */
    public void addToTile(RoomTile tile) {
        if(this.tiles.size() != 0) {
            for(RoomTile oldTile : this.tiles) {
                oldTile.getEntities().remove(this);
            }

            this.tiles.clear();
        }

        tile.getEntities().add(this);
        this.tiles.add(tile);
    }

    /**
     * Returns the tiles for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public Set<RoomTile> getTiles() {
        return tiles;
    }

    /**
     * Updates the status type for this room contract.
     *
     * @param statusType Status type supplied by the caller.
     */
    public void setStatusType(int statusType) {
        this.statusType = statusType;
    }

    /**
     * Returns the status type for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getStatusType() {
        return statusType;
    }

    /**
     * Returns the dice count for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDiceCount() {
        return diceCount;
    }

    /**
     * Adds dice count to this room contract.
     *
     * @param diceCount Dice count supplied by the caller.
     */
    public void addDiceCount(int diceCount) {
        this.diceCount += diceCount;
    }

    /**
     * Executes reset dice count for this room contract.
     */
    public void resetDiceCount() {
        this.diceCount = 0;
    }

    /**
     * Indicates whether transformed applies to this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isTransformed() { return isTransformed;}

    /**
     * Updates the transformed for this room contract.
     *
     * @param transformed Transformed supplied by the caller.
     */
    public void setTransformed(boolean transformed) {
        isTransformed = transformed;
    }

    /**
     * Returns the tag user for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public ArrayList<Object> getTagUser() {return this.addTagUser;}
}
