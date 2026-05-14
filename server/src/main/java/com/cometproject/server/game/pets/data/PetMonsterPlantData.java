package com.cometproject.server.game.pets.data;


import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.pets.PetManager;
import com.cometproject.server.game.pets.races.plants.PetMonsterPlant;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Carries pet monster plant data data for the pet subsystem.
 */
public class PetMonsterPlantData extends PetData {

    private final int bodyRarity;
    private final int colorRarity;
    private final int seedRarity;

    private final PetMonsterPlant body;

    private final int nose;
    private final int noseColor;
    private final int eyes;
    private final int eyesColor;
    private final int mouth;
    private final int mouthColor;
    private int hue;


    private int lastFood;
    private int lastDiamonds;
    private boolean isDead;
    private boolean hasBreed;
    private boolean isActive;


    private int plantType;
    private int growthStage;

    /**
     * Creates a pet monster plant data instance for the pet subsystem.
     *
     * @param set Set supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public PetMonsterPlantData(ResultSet set) throws SQLException {
        super(set);
        JsonObject object = new Gson().fromJson(this.getExtradata(), JsonObject.class);
        this.seedRarity = object.get("se").getAsInt();
        this.plantType = object.get("t").getAsInt();
        this.hue = object.get("c").getAsInt();
        this.nose = object.get("n").getAsInt();
        this.noseColor = object.get("nc").getAsInt();
        this.eyes = object.get("e").getAsInt();
        this.eyesColor = object.get("ec").getAsInt();
        this.mouth = object.get("m").getAsInt();
        this.mouthColor = object.get("mc").getAsInt();
        this.bodyRarity = object.get("mb").getAsInt();
        this.colorRarity = object.get("mcr").getAsInt();
        this.lastFood = object.get("ml").getAsInt();
        this.isDead = object.get("id").getAsBoolean();
        this.hasBreed = object.get("hb").getAsBoolean();
        this.lastDiamonds = object.get("lg").getAsInt();
        this.isActive = object.get("pb").getAsBoolean();
        this.body = PetManager.getInstance().getMonsterPlantBodies().get(this.bodyRarity);
        this.growthStage = this.getRealGrowthStage();
        this.setExtradata(this.toJson());

    }

    /**
     * Creates a pet monster plant data instance for the pet subsystem.
     *
     * @param seedRarity Seed rarity supplied by the caller.
     * @param bodyRarity Body rarity supplied by the caller.
     * @param colorRarity Color rarity supplied by the caller.
     * @param userId User id supplied by the caller.
     * @param type Type supplied by the caller.
     * @param hue Hue supplied by the caller.
     * @param nose Nose supplied by the caller.
     * @param noseColor Nose color supplied by the caller.
     * @param mouth Mouth supplied by the caller.
     * @param mouthColor Mouth color supplied by the caller.
     * @param eyes Eyes supplied by the caller.
     * @param eyesColor Eyes color supplied by the caller.
     */
    public PetMonsterPlantData(int seedRarity, int bodyRarity, int colorRarity, int userId, int type, int hue, int nose, int noseColor, int mouth, int mouthColor, int eyes, int eyesColor) {
        super(16, 0, "", "", userId);
        this.seedRarity = seedRarity;
        this.plantType = type;
        this.hue = hue;
        this.nose = nose;
        this.noseColor = noseColor;
        this.bodyRarity = bodyRarity;
        this.hasBreed = false;
        this.colorRarity = colorRarity;
        this.mouth = mouth;
        this.mouthColor = mouthColor;
        this.eyes = eyes;
        this.eyesColor = eyesColor;
        this.growthStage = 1;
        this.isActive = false;
        this.lastFood = (int) Comet.getTime();
        this.isDead = false;
        this.body = PetManager.getInstance().getMonsterPlantBodies().get(this.bodyRarity);
        this.lastDiamonds = (int) Comet.getTime();
        this.setExtradata(this.toJson());
    }

    /**
     * Returns the name for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getName() {
        String name = " " + PetManager.getInstance().getMonsterPlantColors().get(this.colorRarity).getName();
        name += " " + this.body.getName();

        return name;
    }

    /**
     * Returns the rarity for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRarity() {
        int max = this.bodyRarity + this.colorRarity;
        return (isDead() ? 0 : (!isFullyGrown() ? Math.min(this.growthStage, max) : max));
    }

    /**
     * Returns the look for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getLook() {
        return "16 0 FFFFFF " +
                "10 " +
                "0 -1 2 " +
                "1 " + this.plantType + " " + this.hue + " " +
                "2 " + this.mouth + " " + this.mouthColor + " " +
                "3 " + this.nose + " " + this.noseColor + " " +
                "4 " + this.eyes + " " + this.eyesColor + " " + "2";
    }

    /**
     * Returns the eyes color for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getEyesColor() {
        return eyesColor;
    }

    /**
     * Returns the eyes for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getEyes() {
        return eyes;
    }

    /**
     * Returns the nose for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getNose() {
        return nose;
    }

    /**
     * Returns the nose color for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getNoseColor() {
        return noseColor;
    }

    /**
     * Returns the plant color for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPlantColor() {
        return this.hue;
    }

    /**
     * Returns the mouth color for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getMouthColor() {
        return mouthColor;
    }

    /**
     * Returns the mouth for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getMouth() {
        return mouth;
    }

    /**
     * Returns the real growth stage for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRealGrowthStage() {
        int stage = (7 - (this.remainingGrowTime() / (this.getGrowTime() / 7))) - 1;
        if (stage <= 0) {
            stage = 1;
        }
        if (this.remainingGrowTime() <= 0) {
            stage = 7;
        }

        return stage;
    }

    /**
     * Returns the last diamonds for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLastDiamonds() {
        return lastDiamonds;
    }

    /**
     * Updates the last diamonds for this pet contract.
     *
     * @param lastDiamonds Last diamonds supplied by the caller.
     */
    public void setLastDiamonds(int lastDiamonds) {
        this.lastDiamonds = lastDiamonds;
    }

    /**
     * Returns the growth stage for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getGrowthStage() {
        return this.growthStage;
    }

    /**
     * Returns the plant name for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getPlantName() {
        return this.getName();
    }

    /**
     * Executes remaining grow time for this pet contract.
     *
     * @return Result produced by the operation.
     */
    public int remainingGrowTime() {
        if (this.growthStage == 7 || this.isDead()) {
            return 0;
        }
        return (int) ((this.getBirthday() + this.getGrowTime()) - Comet.getTime());
    }

    /**
     * Indicates whether fully grown applies to this pet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isFullyGrown() {
        return this.growthStage == 7 && !this.isDead();
    }

    /**
     * Returns the grow time for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getGrowTime() {
        return this.body.getGrowthTime();
    }

    /**
     * Executes to JSON for this pet contract.
     *
     * @return Result produced by the operation.
     */
    public String toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("t", this.plantType);
        object.addProperty("c", this.hue);
        object.addProperty("n", this.nose);
        object.addProperty("nc", this.noseColor);
        object.addProperty("e", this.eyes);
        object.addProperty("ec", this.eyesColor);
        object.addProperty("m", this.mouth);
        object.addProperty("mc", this.mouthColor);
        object.addProperty("mg", this.growthStage);
        object.addProperty("mb", this.bodyRarity);
        object.addProperty("mcr", this.colorRarity);
        object.addProperty("ml", this.lastFood);
        object.addProperty("id", this.isDead);
        object.addProperty("lg", this.lastDiamonds);
        object.addProperty("se", this.seedRarity);
        object.addProperty("hb", this.hasBreed);
        object.addProperty("pb", this.isActive);
        return object.toString();
    }

    /**
     * Executes diamonds stocked for this pet contract.
     *
     * @return Result produced by the operation.
     */
    public int diamondsStocked() {
        if (this.getRarity() >= 20) {
        }
        return 0;
    }

    /**
     * Indicates whether dead applies to this pet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isDead() {
        return (this.getLastFood() <= 0);
    }

    /**
     * Executes as dead for this pet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean asDead() {
        return this.isDead;
    }


    /**
     * Updates the dead for this pet contract.
     *
     * @param dead Dead supplied by the caller.
     */
    public void setDead(boolean dead) {
        isDead = dead;
    }

    /**
     * Returns the seed rarity for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getSeedRarity() {
        return this.seedRarity;
    }

    /**
     * Indicates whether this pet contract can breed.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canBreed() {
        return !hasBreed() && isFullyGrown() && !isDead();
    }

    /**
     * Returns the last food for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLastFood() {
        int time = (lastFood + this.body.getLifeTime()) - ((int) Comet.getTime());
        if (time < 0) {
            time = 0;
        }
        return time;
    }

    /**
     * Returns the body for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public PetMonsterPlant getBody() {
        return this.body;
    }

    /**
     * Executes save for this pet contract.
     */
    public void save() {
        this.setExtradata(this.toJson());
        super.savePlantsData();
    }

    /**
     * Updates the growth stage for this pet contract.
     *
     * @param growthStage Growth stage supplied by the caller.
     */
    public void setGrowthStage(int growthStage) {
        this.growthStage = growthStage;
    }

    /**
     * Indicates whether this pet contract has breed.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasBreed() {
        return this.hasBreed;
    }

    /**
     * Updates the has breed for this pet contract.
     *
     * @param hasBreed Has breed supplied by the caller.
     */
    public void setHasBreed(boolean hasBreed) {
        this.hasBreed = hasBreed;
    }

    /**
     * Indicates whether active applies to this pet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isActive() {
        return isActive && !this.isDead();
    }

    /**
     * Updates the is active for this pet contract.
     *
     * @param isActive Is active supplied by the caller.
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}