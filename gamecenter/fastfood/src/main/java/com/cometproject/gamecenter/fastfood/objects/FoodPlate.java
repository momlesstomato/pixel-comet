package com.cometproject.gamecenter.fastfood.objects;

import com.cometproject.gamecenter.fastfood.FastFoodGame;
import com.cometproject.gamecenter.fastfood.net.FastFoodGameSession;
import com.cometproject.gamecenter.fastfood.net.composers.ApplyShieldMessageComposer;
import com.cometproject.gamecenter.fastfood.net.composers.FoodUpdateMessageComposer;

/**
 * Describes food plate behavior for the Fast Food game subsystem.
 */
public class FoodPlate {
    private final int objectId;
    private final int playerId;
    private float location;
    private float speed;
    private float parachuteSpeed;
    private int state;
    private long timeDropped = System.currentTimeMillis();
    private long timeOpened;
    private long dropTime = System.currentTimeMillis() + 100000000;

    private boolean finalized = false;
    private boolean openedParachute = false;
    private boolean hasShield = false;

    /*
     *
     * STATES:
     * 1 - Normal Drop
     * 2 - Parachute
     * 3 - Add Star
     * 4 - Broken
     * 5 - Hidden
     */

    /**
     * Creates a falling food plate for the Fast Food arena.
     *
     * @param objectId Plate object id sent to the client.
     * @param playerId Player id that owns the plate.
     */
    public FoodPlate(int objectId, int playerId) {
        this.objectId = objectId;
        this.playerId = playerId;
        this.location = 1.0f;
        this.speed = 0.25f;
        this.parachuteSpeed = 0.1f;
        this.state = 1;
    }

    /**
     * Executes tick for this Fast Food game contract.
     *
     * @param gameSession Game session supplied by the caller.
     * @param game Game supplied by the caller.
     */
    public void tick(FastFoodGameSession gameSession, FastFoodGame game) {
        // (System.currentTimeMillis() - this.timeDropped) >= (this.state == 2 ? 4500 : 2500)
        //System.out.print("FoodPlate - ID: " + this.getObjectId() + " - State: " + this.getState() + " - Location: " + this.location + " - Finalized: " + finalized + "\n");
        if(!this.openedParachute && (System.currentTimeMillis() - this.timeDropped) >= 1800 && !this.finalized){
            if(this.hasShield){
                this.state = 3;
                game.broadcast(new FoodUpdateMessageComposer(this.playerId, this.getObjectId(), this.state, 0, 0));
                this.finalized = true;
                return;
            }

            this.state = 4;
            game.broadcast(new FoodUpdateMessageComposer(this.playerId, this.getObjectId(), this.state, 0, 0));
            this.finalized = true;
            return;
        }

        if(System.currentTimeMillis() >= this.dropTime && !this.finalized) {
            if(this.state == 2) { // we have a parachute
                this.state = 3;
                game.broadcast(new FoodUpdateMessageComposer(this.playerId, this.getObjectId(), this.state, 0, 0));
            }

            if(this.state == 3) {
                this.state = 6;
                this.finalized = true;
                game.broadcast(new FoodUpdateMessageComposer(this.playerId, this.getObjectId(), this.state, -1, 1));

                /*final int objectId = game.getCounter().getAndIncrement();
                final FoodPlate foodPlate = new FoodPlate(objectId, gameSession.getPlayerId());

                gameSession.setCurrentPlate(foodPlate);
                game.broadcast(new DropFoodMessageComposer(objectId, foodPlate, true));
                return;*/
            } else{
                this.state = 4;
                game.broadcast(new FoodUpdateMessageComposer(this.playerId, this.getObjectId(), this.state, 0, 0));
            }

            //this.hidePlate(game);
            this.finalized = true;
        }
    }

    /**
     * Executes open parachute for this Fast Food game contract.
     *
     * @param game Game supplied by the caller.
     */
    public void openParachute(FastFoodGame game) {
        this.state = 2;
        this.openedParachute = true;

        this.timeOpened = System.currentTimeMillis();
        long difference = this.timeOpened - this.timeDropped;

        // DISTANCIA SIN PARACAÍDAS
        this.location = this.location - ((float)(difference / 1000) * (this.speed)); // calculate location
        this.speed = this.parachuteSpeed;

        this.dropTime = (((long)((this.location / this.speed) / 3.1) * 1000) + this.timeOpened);

        //System.out.print("\nLOCATION ON DROP:" + this.location + " - DropTime : " + this.dropTime + " DIFF DROP: " + difference + " \n");

        game.broadcast(new FoodUpdateMessageComposer(this.getPlayerId(), this.getObjectId(), this.getState(), -1, 0));
    }

    /**
     * Executes apply shield for this Fast Food game contract.
     *
     * @param game Game supplied by the caller.
     */
    public void applyShield(FastFoodGame game) {
        this.hasShield = true;
        game.broadcast(new ApplyShieldMessageComposer(this.getPlayerId(), this.getObjectId(), true));
    }

    /**
     * Executes hide plate for this Fast Food game contract.
     *
     * @param game Game supplied by the caller.
     */
    public void hidePlate(FastFoodGame game){
        game.broadcast(new FoodUpdateMessageComposer(this.getPlayerId(), this.getObjectId(), 5, 0, 0));
    }

    /**
     * Returns the object id for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public int getObjectId() {
        return this.objectId;
    }

    /**
     * Returns the location for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public float getLocation() {
        return this.location;
    }

    /**
     * Updates the location for this Fast Food game contract.
     *
     * @param location Location supplied by the caller.
     */
    public void setLocation(float location) {
        this.location = location;
    }

    /**
     * Returns the speed for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public float getSpeed() {
        return this.speed;
    }

    /**
     * Updates the speed for this Fast Food game contract.
     *
     * @param speed Speed supplied by the caller.
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Returns the state for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public int getState() {
        return this.state;
    }

    /**
     * Updates the state for this Fast Food game contract.
     *
     * @param state State supplied by the caller.
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * Returns the player id for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPlayerId() {
        return this.playerId;
    }

    /**
     * Returns the time opened for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public long getTimeOpened(){
        return this.timeOpened;
    }
    /**
     * Indicates whether finalized applies to this Fast Food game contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isFinalized() {
        return this.finalized;
    }

    /**
     * Enumerates plate type values used by the Fast Food game subsystem.
     */
    public enum plateType{
        Pizza("1"),
        Soap("2"),
        Glass("3"),
        Cake("4"),
        Cake1("5"),
        Cake2("6");

        private String groupName;

        plateType(String groupName) {
            this.groupName = groupName;
        }

        /**
         * Returns the group name for this Fast Food game contract.
         *
         * @return Value exposed by the contract.
         */
        public String getGroupName() {
            return groupName;
        }

        /**
         * Returns the type by name for this Fast Food game contract.
         *
         * @param name Name supplied by the caller.
         * @return Value exposed by the contract.
         */
        public static plateType getTypeByName(String name) {
            for (plateType type : plateType.values()) {
                if (type.groupName.equals(name)) {
                    return type;
                }
            }

            return null;
        }
    }
}
