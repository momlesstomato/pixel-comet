package com.cometproject.api.game.furniture.types;

/**
 * Defines the i music data contract for the furniture subsystem.
 */
public interface IMusicData {
    /**
     * Returns the song id associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getSongId();

    /**
     * Returns the name associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getName();

    /**
     * Returns the title associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getTitle();

    /**
     * Returns the artist associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getArtist();

    /**
     * Returns the data associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getData();

    /**
     * Returns the length seconds associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLengthSeconds();

    /**
     * Returns the length milliseconds associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLengthMilliseconds();
}
