package com.cometproject.api.game.players.data.types;

/**
 * Defines the i playlist item contract for the player subsystem.
 */
public interface IPlaylistItem {
    /**
     * Returns the video id associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getVideoId();

    /**
     * Updates the video id value for this player contract.
     *
     * @param videoId Video id value supplied by the caller.
     */
    void setVideoId(String videoId);

    /**
     * Returns the title associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getTitle();

    /**
     * Updates the title value for this player contract.
     *
     * @param title Title value supplied by the caller.
     */
    void setTitle(String title);

    /**
     * Returns the description associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getDescription();

    /**
     * Updates the description value for this player contract.
     *
     * @param description Description value supplied by the caller.
     */
    void setDescription(String description);

    /**
     * Returns the duration associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getDuration();

    /**
     * Updates the duration value for this player contract.
     *
     * @param duration Duration value supplied by the caller.
     */
    void setDuration(int duration);
}
