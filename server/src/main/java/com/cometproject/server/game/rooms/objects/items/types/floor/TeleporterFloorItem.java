package com.cometproject.server.game.rooms.objects.items.types.floor;
import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.game.rooms.RoomManager;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFactory;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.AdvancedFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.state.FloorItemEvent;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.mapping.RoomTile;
import com.cometproject.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.engine.RoomForwardMessageComposer;

/**
 * Describes teleporter floor item behavior for the room subsystem.
 */
public class TeleporterFloorItem extends AdvancedFloorItem<TeleporterFloorItem.TeleporterItemEvent> {
    class TeleporterItemEvent extends FloorItemEvent {

        public int state;
        public PlayerEntity outgoingEntity;
        public PlayerEntity incomingEntity;

        /**
         * Executes teleporter item event for this room contract.
         *
         * @param delay Delay supplied by the caller.
         */
        protected TeleporterItemEvent(int delay) {
            super(delay);
        }
    }

    private boolean inUse = false;

    private long pairId = -1;
    boolean isDoor = false;

    /**
     * Creates a teleporter floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public TeleporterFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);

        this.getItemData().setData("0");

        if (this.getDefinition().getInteraction().equals("teleport_door")) {
            this.isDoor = true;
        }
    }

    /**
     * Handles the event complete callback for this room contract.
     *
     * @param event Event supplied by the caller.
     */
    @Override
    public void onEventComplete(TeleporterItemEvent event) {
        try {
            switch (event.state) {
                case 0: {
                    event.outgoingEntity.moveTo(this.getPosition().getX(), this.getPosition().getY());

                    if (!(this instanceof TeleportPadFloorItem)) {
                        this.toggleDoor(true);
                    }

                    event.state = 1;

                    event.setTotalTicks(RoomItemFactory.getProcessTime(1));
                    this.queueEvent(event);
                    break;
                }

                case 1: {
                    RoomItemFloor pairItem = this.getPartner(this.getPairId());

                    if (pairItem == null) {
                        int roomId = ItemManager.getInstance().roomIdByItemId(pairId);

                        if (RoomManager.getInstance().get(roomId) == null) {
                            event.state = 8;

                            event.setTotalTicks(RoomItemFactory.getProcessTime(0.5));
                            this.queueEvent(event);
                            return;
                        }
                    }

                    if (!this.isDoor && !(this instanceof TeleportPadFloorItem))
                        this.toggleDoor(false);

                    event.state = 2;
                    event.setTotalTicks(RoomItemFactory.getProcessTime(this instanceof TeleportPadFloorItem ? 0.1 : 0.5));
                    this.queueEvent(event);
                    break;
                }

                case 2: {
                    if (!this.isDoor) {
                        this.toggleAnimation(true);

                        event.state = 3;
                        event.setTotalTicks(RoomItemFactory.getProcessTime(1));
                        this.queueEvent(event);
                    } else {
                        this.toggleAnimation(true);
                        event.state = 3;
                        event.setTotalTicks(RoomItemFactory.getProcessTime(0.1));
                        this.queueEvent(event);
                    }
                    break;
                }

                case 3: {
                    long pairId = this.getPairId();

                    if (pairId == 0) {
                        event.state = 8;
                        event.setTotalTicks(RoomItemFactory.getProcessTime(0.5));
                        this.queueEvent(event);
                        return;
                    }

                    RoomItemFloor pairItem = this.getPartner(pairId);

                    if (pairItem == null) {
                        int roomId = ItemManager.getInstance().roomIdByItemId(pairId);

                        if (RoomManager.getInstance().get(roomId) != null) {
                            if (event.outgoingEntity != null) {
                                PlayerEntity pEntity = event.outgoingEntity;

                                if (pEntity.getPlayer() != null && pEntity.getPlayer().getSession() != null) {
                                    pEntity.getPlayer().setTeleportId(pairId);
                                    pEntity.getPlayer().setTeleportRoomId(roomId);
                                    pEntity.getPlayer().getSession().send(new RoomForwardMessageComposer(roomId));
                                }

                                event.state = 7;
                                event.setTotalTicks(RoomItemFactory.getProcessTime(0.5));
                                this.queueEvent(event);
                            }
                        } else {
                            event.state = 8;
                            event.setTotalTicks(RoomItemFactory.getProcessTime(0.5));
                            this.queueEvent(event);
                            return;
                        }
                    }

                    if (!this.isDoor) {
                        event.state = 9;
                        event.setTotalTicks(RoomItemFactory.getProcessTime(1));
                        this.queueEvent(event);
                    }

                    TeleporterFloorItem teleItem = (TeleporterFloorItem) pairItem;

                    if (teleItem != null)
                        teleItem.handleIncomingEntity(event.outgoingEntity, this);
                    break;
                }

                case 5: {
                    this.toggleAnimation(false);
                    event.state = 6;

                    if (event.incomingEntity.getPlayer() != null) {
                        event.incomingEntity.setBodyRotation(this.getItemData().getRotation());
                        event.incomingEntity.setHeadRotation(this.getItemData().getRotation());
                        event.incomingEntity.refresh();
                    }

                    event.setTotalTicks(RoomItemFactory.getProcessTime(0.5));
                    this.queueEvent(event);
                    break;
                }

                case 6: {
                    event.incomingEntity.setBodyRotation(this.getRotation());
                    event.incomingEntity.refresh();

                    if (!(this instanceof TeleportPadFloorItem))
                        this.toggleDoor(true);

                    if (event.incomingEntity != null) {
                        if (event.incomingEntity.getCurrentEffect() != null) {
                            event.incomingEntity.applyEffect(event.incomingEntity.getCurrentEffect());
                        }
                    }

                    event.state = 7;
                    event.setTotalTicks(RoomItemFactory.getProcessTime(1));
                    this.queueEvent(event);
                    break;
                }

                case 7: {
                    Position pos = this.getPosition().squareInFront(this.getRotation());
                    if(event.incomingEntity != null)
                    event.incomingEntity.moveTo(pos);


                    if (event.incomingEntity != null) {
                        event.incomingEntity.setOverriden(false);
                        event.incomingEntity.removeAttribute("tptpencours");
                        event.incomingEntity = null;
                    }

                    if (event.outgoingEntity != null) {
                        event.outgoingEntity.setOverriden(false);
                        event.outgoingEntity.removeAttribute("tptpencours");
                        event.outgoingEntity = null;
                    }
                    //this.inUse = false;
                    event.state = 10;
                    event.setTotalTicks(RoomItemFactory.getProcessTime(1));
                    this.queueEvent(event);
                    break;
                }

                case 10: {
                    this.toggleDoor(false);
                    break;
                }

                case 8: {
                    if (!(this instanceof TeleportPadFloorItem))
                        this.toggleDoor(true);

                    if (event.outgoingEntity != null) {
                        event.outgoingEntity.moveTo(this.getPosition().squareInFront(this.getItemData().getRotation()).getX(), this.getPosition().squareInFront(this.getItemData().getRotation()).getY());
                    }

                    event.state = 7;
                    event.setTotalTicks(RoomItemFactory.getProcessTime(1));
                    this.queueEvent(event);
                    break;
                }

                case 9: {
                    if (event.incomingEntity != null) {
                        event.incomingEntity.removeAttribute("interacttpencours");
                    }
                    if (event.outgoingEntity != null) {
                        event.outgoingEntity.removeAttribute("interacttpencours");
                    }
                    this.endTeleporting();
                }
            }
        } catch (Exception e) {
            Comet.getServer().getLogger().error("Failed to handle teleport event", e);
        }
    }

    /**
     * Handles the interact callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param requestData Request data supplied by the caller.
     * @param isWiredTrigger Is wired trigger supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean onInteract(RoomEntity entity, int requestData, boolean isWiredTrigger) {
        if (isWiredTrigger) return false;

        Position posInFront = this.getPosition().squareInFront(this.getRotation());

        if (entity.isOverriden()) return false;

        if (entity.getPosition().getX() != posInFront.getX() || entity.getPosition().getY() != posInFront.getY() &&
                !(entity.getPosition().getX() == this.getPosition().getX() && entity.getPosition().getY() == this.getPosition().getY())) {
            entity.moveTo(posInFront.getX(), posInFront.getY());

            RoomTile tile = this.getRoom().getMapping().getTile(posInFront.getX(), posInFront.getY());

            if (tile != null) {
                tile.scheduleEvent(entity.getId(), (e) -> onInteract(e, requestData, false));
            }
            return false;
        }

        //this.inUse = true;

        final TeleporterItemEvent event = new TeleporterItemEvent(RoomItemFactory.getProcessTime(1));
        event.outgoingEntity = (PlayerEntity) entity;
        event.outgoingEntity.setOverriden(true);
        event.outgoingEntity.setAttribute("interacttpencours", true);
        event.outgoingEntity.setAttribute("tptpencours", true);
        event.outgoingEntity.moveTo(posInFront);
        event.state = 0;

        try {
            this.queueEvent(event);
        } catch (Exception e) {
            Comet.getServer().getLogger().error("Failed to queue teleporter item event", e);
        }

        return true;
    }

    /**
     * Handles the entity step on callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOn(RoomEntity entity) {
        if (this.inUse || !(entity instanceof PlayerEntity)) {
            return;
        }

        //this.inUse = true;

        if (entity.isOverriden()) return;

        final TeleporterItemEvent event = new TeleporterItemEvent(RoomItemFactory.getProcessTime(0.01));
        event.outgoingEntity = (PlayerEntity) entity;
        event.outgoingEntity.setOverriden(true);
        event.outgoingEntity.setAttribute("tptpencours", true);
        event.state = 1;

        try {
            this.queueEvent(event);
        } catch (Exception e) {
            Comet.getServer().getLogger().error("Failed to queue teleporter item event", e);
        }
    }

    /**
     * Handles the tick complete callback for this room contract.
     */
    @Override
    public void onTickComplete() {

    }

    /**
     * Handles the placed callback for this room contract.
     */
    @Override
    public void onPlaced() {
        this.getItemData().setData("0");
    }

    private long getPairId() {
        if (this.pairId == -1) {
            this.pairId = ItemManager.getInstance().getTeleportPartner(this.getId());
        }

        return this.pairId;
    }

    /**
     * Executes end teleporting for this room contract.
     */
    public void endTeleporting() {
        this.toggleAnimation(false);
        this.toggleDoor(false);
        //this.inUse = false;
    }

    /**
     * Handles incoming entity for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param otherItem Other item supplied by the caller.
     */
    public void handleIncomingEntity(PlayerEntity entity, TeleporterFloorItem otherItem) {
        if (otherItem != null)
            otherItem.endTeleporting();

        entity.warp(this.getPosition().copy());
        //entity.updateAndSetPosition(this.getPosition().copy());

        this.toggleAnimation(true);

        final TeleporterItemEvent event = new TeleporterItemEvent(0);

        event.incomingEntity = entity;

        if (!this.isDoor) {
            event.state = 5;
            event.setTotalTicks(RoomItemFactory.getProcessTime(1));
        } else {
            event.state = 6;
            event.setTotalTicks(RoomItemFactory.getProcessTime(0.1));
        }

        try {
            this.queueEvent(event);
        } catch (Exception e) {
            Comet.getServer().getLogger().error("Error while queueing teleport event", e);
        }
    }

    /**
     * Executes toggle door for this room contract.
     *
     * @param state State supplied by the caller.
     */
    protected void toggleDoor(boolean state) {
        if (state)
            this.getItemData().setData("1");
        else
            this.getItemData().setData("0");

        this.sendUpdate();
    }

    /**
     * Executes toggle animation for this room contract.
     *
     * @param state State supplied by the caller.
     */
    protected void toggleAnimation(boolean state) {
        if (state) {
            this.getItemData().setData("2");
        } else {
            this.getItemData().setData("0");
        }

        this.sendUpdate();
    }

    /**
     * Executes notify state for this room contract.
     *
     * @param state State supplied by the caller.
     */
    public void notifyState(String state){
        this.getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(-1, "Current state is " + state + "."));
    }

    /**
     * Returns the partner for this room contract.
     *
     * @param pairId Pair id supplied by the caller.
     * @return Value exposed by the contract.
     */
    protected RoomItemFloor getPartner(long pairId) {
        return this.getRoom().getItems().getFloorItem(pairId);
    }
}
