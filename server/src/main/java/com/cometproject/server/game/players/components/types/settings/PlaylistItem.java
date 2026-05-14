package com.cometproject.server.game.players.components.types.settings;

import com.cometproject.api.game.players.data.types.IPlaylistItem;

/**
 * Describes playlist item behavior for the player subsystem.
 */
public class PlaylistItem implements IPlaylistItem {
    private String videoId;
    private String title;
    private String description;
    private int duration;

    /**
     * Creates a playlist item instance for the player subsystem.
     *
     * @param videoId Video id supplied by the caller.
     * @param title Title supplied by the caller.
     * @param description Description supplied by the caller.
     * @param duration Duration supplied by the caller.
     */
    public PlaylistItem(String videoId, String title, String description, int duration) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.duration = duration;
    }

    /**
     * Returns the video id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     * Updates the video id for this player contract.
     *
     * @param videoId Video id supplied by the caller.
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     * Returns the title for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Updates the title for this player contract.
     *
     * @param title Title supplied by the caller.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description for this player contract.
     *
     * @param description Description supplied by the caller.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the duration for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Updates the duration for this player contract.
     *
     * @param duration Duration supplied by the caller.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
}
