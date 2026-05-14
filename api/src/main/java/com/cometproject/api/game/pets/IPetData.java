package com.cometproject.api.game.pets;

import com.cometproject.api.game.utilities.Position;
import com.google.gson.JsonObject;

/**
 * Defines the i pet data contract for the pet subsystem.
 */
public interface IPetData extends IPetStats {

    /**
     * Executes the to JSON object operation for this pet contract.
     *
     * @return Result produced by the operation.
     */
    JsonObject toJsonObject();

    /**
     * Persists stats data for this pet contract.
     */
    void saveStats();

    /**
     * Persists horse data data for this pet contract.
     */
    void saveHorseData();

    /**
     * Executes the increase experience operation for this pet contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void increaseExperience(int amount);

    /**
     * Executes the increase happiness operation for this pet contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void increaseHappiness(int amount);

    /**
     * Executes the increment level operation for this pet contract.
     */
    void incrementLevel();

    /**
     * Executes the increment scratches operation for this pet contract.
     */
    void incrementScratches();

    /**
     * Returns the id associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Returns the name associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getName();

    /**
     * Returns the level associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLevel();

    /**
     * Returns the happiness associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getHappiness();

    /**
     * Returns the experience associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getExperience();

    /**
     * Returns the experience goal associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getExperienceGoal();

    /**
     * Returns the energy associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getEnergy();

    /**
     * Returns the hunger associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getHunger();

    /**
     * Executes the decrease energy operation for this pet contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void decreaseEnergy(int amount);

    /**
     * Executes the increase energy operation for this pet contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void increaseEnergy(int amount);

    /**
     * Returns the owner id associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getOwnerId();

    /**
     * Returns the colour associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getColour();

    /**
     * Returns the race id associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getRaceId();

    /**
     * Returns the look associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getLook();

    /**
     * Returns the hair dye associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getHairDye();

    /**
     * Returns the hair associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getHair();

    /**
     * Returns the type id associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getTypeId();

    /**
     * Returns the room position associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Position getRoomPosition();

    /**
     * Updates the room position value for this pet contract.
     *
     * @param position Position value supplied by the caller.
     */
    void setRoomPosition(Position position);

    /**
     * Updates the hair dye value for this pet contract.
     *
     * @param hairDye Hair dye value supplied by the caller.
     */
    void setHairDye(int hairDye);

    /**
     * Updates the hair value for this pet contract.
     *
     * @param hair Hair value supplied by the caller.
     */
    void setHair(int hair);

    /**
     * Indicates whether saddled is enabled for this pet contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isSaddled();

    /**
     * Updates the saddled value for this pet contract.
     *
     * @param saddled Saddled value supplied by the caller.
     */
    void setSaddled(boolean saddled);

    /**
     * Indicates whether any rider is enabled for this pet contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isAnyRider();

    /**
     * Updates the any rider value for this pet contract.
     *
     * @param anyRider Any rider value supplied by the caller.
     */
    void setAnyRider(boolean anyRider);

    /**
     * Returns the scratches associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getScratches();

    /**
     * Updates the race id value for this pet contract.
     *
     * @param raceId Race id value supplied by the caller.
     */
    void setRaceId(int raceId);

    /**
     * Returns the birthday associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getBirthday();

    /**
     * Updates the birthday value for this pet contract.
     *
     * @param birthday Birthday value supplied by the caller.
     */
    void setBirthday(int birthday);

    /**
     * Returns the owner name associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getOwnerName();

    /**
     * Returns the extradata associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getExtradata();

    /**
     * Returns the room id associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getRoomId();

    /**
     * Updates the room id value for this pet contract.
     *
     * @param roomId Room id value supplied by the caller.
     */
    void setRoomId(int roomId);

    /**
     * Persists plants data data for this pet contract.
     */
    void savePlantsData();

    /**
     * Updates the extradata value for this pet contract.
     *
     * @param extradata Extradata value supplied by the caller.
     */
    void setExtradata(String extradata);

    /**
     * Updates the owner name value for this pet contract.
     *
     * @param ownerName Owner name value supplied by the caller.
     */
    void setOwnerName(String ownerName);

    /**
     * Updates the level value for this pet contract.
     *
     * @param level Level value supplied by the caller.
     */
    void setLevel(int level);

    /**
     * Executes the increase hunger operation for this pet contract.
     *
     * @param amount Amount value supplied by the caller.
     */
    void increaseHunger(int amount);
}
