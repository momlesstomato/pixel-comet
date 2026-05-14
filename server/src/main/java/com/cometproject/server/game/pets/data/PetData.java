package com.cometproject.server.game.pets.data;

import com.cometproject.api.game.pets.IPetData;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.rooms.objects.entities.types.ai.pets.PetAI;
import com.cometproject.server.storage.queries.pets.PetDao;
import com.google.gson.JsonObject;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Carries pet data data for the pet subsystem.
 */
public class PetData implements IPetData {
    private int id;
    private String name;

    private int scratches;

    private int level;

    private int happiness;
    private int experience;
    private int energy;
    private int hunger;
    private int ownerId;
    private int roomId;

    private String ownerName;
    private String colour;
    private String extraData;

    private int raceId;
    private int typeId;
    private int hairDye = 0;

    private int hair = -1;
    private boolean anyRider = false;

    private boolean saddled = false;
    private int birthday;

    private Position roomPosition;

    /**
     * Creates a pet data instance for the pet subsystem.
     *
     * @param data Data supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public PetData(ResultSet data) throws SQLException {
        this.id = data.getInt("id");
        this.name = data.getString("pet_name");
        this.scratches = data.getInt("scratches");
        this.level = data.getInt("level");
        this.happiness = data.getInt("happiness");
        this.experience = data.getInt("experience");
        this.energy = data.getInt("energy");
        this.hunger = data.getInt("hunger");
        this.ownerId = data.getInt("owner_id");
        this.roomId = data.getInt("room_id");
        this.ownerName = data.getString("owner_name");
        this.colour = data.getString("colour");
        this.raceId = data.getInt("race_id");
        this.typeId = data.getInt("type");
        this.saddled = data.getBoolean("saddled");
        this.hair = data.getInt("hair_style");
        this.hairDye = data.getInt("hair_colour");
        this.anyRider = data.getBoolean("any_rider");
        this.birthday = data.getInt("birthday");
        this.extraData = data.getString("extra_data");

        this.roomPosition = new Position(data.getInt("x"), data.getInt("y"), data.getDouble("z"));
    }

    /**
     * Creates a pet data instance for the pet subsystem.
     *
     * @param id Id supplied by the caller.
     * @param name Name supplied by the caller.
     * @param scratches Scratches supplied by the caller.
     * @param level Level supplied by the caller.
     * @param happiness Happiness supplied by the caller.
     * @param experience Experience supplied by the caller.
     * @param energy Energy supplied by the caller.
     * @param hunger Hunger supplied by the caller.
     * @param ownerId Owner id supplied by the caller.
     * @param ownerName Owner name supplied by the caller.
     * @param colour Colour supplied by the caller.
     * @param raceId Race id supplied by the caller.
     * @param typeId Type id supplied by the caller.
     */
    public PetData(int id, String name, int scratches, int level, int happiness, int experience, int energy, int hunger,
                   int ownerId, String ownerName, String colour, int raceId, int typeId) {
        this.id = id;
        this.name = name;
        this.scratches = scratches;
        this.level = level;
        this.happiness = happiness;
        this.experience = experience;
        this.energy = energy;
        this.hunger = hunger;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.colour = colour;
        this.raceId = raceId;
        this.typeId = typeId;
        this.birthday = (int) Comet.getTime();
        this.extraData = "";
    }

    /**
     * Creates a pet data instance for the pet subsystem.
     *
     * @param id Id supplied by the caller.
     * @param name Name supplied by the caller.
     * @param ownerId Owner id supplied by the caller.
     * @param ownerName Owner name supplied by the caller.
     * @param raceId Race id supplied by the caller.
     * @param typeId Type id supplied by the caller.
     */
    public PetData(int id, String name, int ownerId, String ownerName, int raceId, int typeId) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.raceId = raceId;
        this.typeId = typeId;
        this.colour = "FFFFFF";
        this.level = StaticPetProperties.DEFAULT_LEVEL;
        this.happiness = StaticPetProperties.DEFAULT_HAPPINESS;
        this.experience = StaticPetProperties.DEFAULT_EXPERIENCE;
        this.energy = StaticPetProperties.DEFAULT_ENERGY;
        this.hunger = StaticPetProperties.DEFAULT_HUNGER;
        this.extraData = "";
    }

    /**
     * Creates a pet data instance for the pet subsystem.
     *
     * @param type Type supplied by the caller.
     * @param race Race supplied by the caller.
     * @param color Color supplied by the caller.
     * @param name Name supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public PetData(int type, int race, String color, String name, int userId) {
        this.id = 0;
        this.ownerId = userId;
        this.name = name;
        this.typeId = type;
        this.raceId = race;
        this.colour = color;
        this.experience = 0;
        this.happiness = 100;
        this.energy = 100;
        this.scratches = 0;
        this.level = 0;
        this.hunger = 0;
        this.birthday = (int) Comet.getTime();
        this.level = 1;
        this.extraData = "";
    }

    /**
     * Executes to JSON object for this pet contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", this.id);
        jsonObject.addProperty("name", this.name);
        jsonObject.addProperty("scratches", this.scratches);
        jsonObject.addProperty("level", this.level);
        jsonObject.addProperty("happiness", this.happiness);
        jsonObject.addProperty("experience", this.experience);
        jsonObject.addProperty("energy", this.energy);
        jsonObject.addProperty("ownerId", this.ownerId);
        jsonObject.addProperty("ownerName", this.ownerName);
        jsonObject.addProperty("colour", this.colour);
        jsonObject.addProperty("raceId", this.raceId);
        jsonObject.addProperty("typeId", this.typeId);
        jsonObject.addProperty("hairDye", this.hairDye);
        jsonObject.addProperty("hair", this.hair);
        jsonObject.addProperty("anyRider", this.anyRider);
        jsonObject.addProperty("saddled", this.saddled);
        jsonObject.addProperty("birthday", this.birthday);
        jsonObject.addProperty("extra_data", this.extraData);

        final JsonObject roomPosition = new JsonObject();

        if (this.roomPosition != null) {
            roomPosition.addProperty("x", this.roomPosition.getX());
            roomPosition.addProperty("y", this.roomPosition.getY());
            roomPosition.addProperty("z", this.roomPosition.getZ());
        }

        jsonObject.add("roomPosition", roomPosition);

        return jsonObject;
    }


    /**
     * Updates the id for this pet contract.
     *
     * @param id Id supplied by the caller.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Persists stats for this pet contract.
     */
    @Override
    public void saveStats() {
        PetDao.saveStats(this.scratches, this.level, this.happiness, this.experience, this.energy, this.hunger, this.extraData, this.id);
    }

    /**
     * Persists horse data for this pet contract.
     */
    @Override
    public void saveHorseData() {
        PetDao.saveHorseData(this.getId(), this.isSaddled(), this.hair, this.hairDye, this.anyRider, this.raceId);
    }

    /**
     * Persists plants data for this pet contract.
     */
    @Override
    public void savePlantsData() {
        PetDao.savePetsBatch(this);
    }

    /**
     * Executes increase experience for this pet contract.
     *
     * @param amount Amount supplied by the caller.
     */
    @Override
    public void increaseExperience(int amount) {
        this.experience += amount;
    }

    /**
     * Executes increase happiness for this pet contract.
     *
     * @param amount Amount supplied by the caller.
     */
    @Override
    public void increaseHappiness(int amount) {
        this.happiness += amount;

        if (this.happiness > 100) {
            this.happiness = 100;
        } else if (this.happiness < 0) {
            this.happiness = 0;
        }
    }

    /**
     * Executes increment level for this pet contract.
     */
    @Override
    public void incrementLevel() {
        this.level += 1;
    }

    /**
     * Executes increment scratches for this pet contract.
     */
    @Override
    public void incrementScratches() {
        this.scratches += 1;
    }

    /**
     * Returns the id for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Returns the name for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the level for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getLevel() {
        return level;
    }

    /**
     * Updates the level for this pet contract.
     *
     * @param level Level supplied by the caller.
     */
    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Returns the happiness for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getHappiness() {
        return happiness;
    }

    /**
     * Returns the experience for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getExperience() {
        return experience;
    }

    /**
     * Returns the experience goal for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getExperienceGoal() {
        return this.level > (PetAI.levelBoundaries.size() - 1) ? PetAI.levelBoundaries.get(19) : PetAI.levelBoundaries.get(this.level);
    }

    /**
     * Returns the energy for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getEnergy() {
        return energy;
    }

    /**
     * Executes decrease energy for this pet contract.
     *
     * @param amount Amount supplied by the caller.
     */
    @Override
    public void decreaseEnergy(int amount) {
        this.energy -= amount;

        if (this.energy < 0) {
            this.energy = 0;
        }
    }

    /**
     * Returns the room id for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getRoomId() {
        return roomId;
    }

    /**
     * Updates the room id for this pet contract.
     *
     * @param roomId Room identifier used by the operation.
     */
    @Override
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Executes increase energy for this pet contract.
     *
     * @param amount Amount supplied by the caller.
     */
    @Override
    public void increaseEnergy(int amount) {
        this.energy += amount;
    }

    /**
     * Returns the extradata for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getExtradata() {
        return this.extraData;
    }

    /**
     * Updates the extradata for this pet contract.
     *
     * @param extraData Extra data supplied by the caller.
     */
    @Override
    public void setExtradata(String extraData) {
        this.extraData = extraData;
    }

    /**
     * Returns the owner id for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * Returns the colour for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getColour() {
        return colour;
    }

    /**
     * Returns the race id for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getRaceId() {
        return raceId;
    }

    /**
     * Updates the race id for this pet contract.
     *
     * @param raceId Race id supplied by the caller.
     */
    @Override
    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    /**
     * Returns the look for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getLook() {
        return this.getTypeId() + " " + this.getRaceId() + " " + this.getColour();
    }

    /**
     * Returns the hair dye for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getHairDye() {
        return this.hairDye;
    }

    /**
     * Updates the hair dye for this pet contract.
     *
     * @param hairDye Hair dye supplied by the caller.
     */
    @Override
    public void setHairDye(int hairDye) {
        this.hairDye = hairDye;
    }

    /**
     * Returns the hair for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getHair() {
        return this.hair;
    }

    /**
     * Updates the hair for this pet contract.
     *
     * @param hair Hair supplied by the caller.
     */
    @Override
    public void setHair(int hair) {
        this.hair = hair;
    }

    /**
     * Returns the type id for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getTypeId() {
        return typeId;
    }

    /**
     * Returns the room position for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Position getRoomPosition() {
        return this.roomPosition;
    }

    /**
     * Updates the room position for this pet contract.
     *
     * @param position Position supplied by the caller.
     */
    @Override
    public void setRoomPosition(Position position) {
        this.roomPosition = position;
    }

    /**
     * Indicates whether saddled applies to this pet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isSaddled() {
        return saddled;
    }

    /**
     * Updates the saddled for this pet contract.
     *
     * @param saddled Saddled supplied by the caller.
     */
    @Override
    public void setSaddled(boolean saddled) {
        this.saddled = saddled;
    }

    /**
     * Indicates whether any rider applies to this pet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isAnyRider() {
        return anyRider;
    }

    /**
     * Updates the any rider for this pet contract.
     *
     * @param anyRider Any rider supplied by the caller.
     */
    @Override
    public void setAnyRider(boolean anyRider) {
        this.anyRider = anyRider;
    }

    /**
     * Returns the scratches for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getScratches() {
        return scratches;
    }

    /**
     * Returns the birthday for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getBirthday() {
        return birthday;
    }

    /**
     * Updates the birthday for this pet contract.
     *
     * @param birthday Birthday supplied by the caller.
     */
    @Override
    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    /**
     * Returns the owner name for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * Updates the owner name for this pet contract.
     *
     * @param ownerName Owner name supplied by the caller.
     */
    @Override
    public void setOwnerName(final String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * Executes increase hunger for this pet contract.
     *
     * @param amount Amount supplied by the caller.
     */
    @Override
    public void increaseHunger(int amount) {
        this.hunger += amount;

        if (this.hunger >= 100) {
            this.hunger = 100;
        } else if (this.hunger < 0) {
            this.hunger = 0;
        }
    }

    /**
     * Returns the hunger for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getHunger() {
        return hunger;
    }
}
