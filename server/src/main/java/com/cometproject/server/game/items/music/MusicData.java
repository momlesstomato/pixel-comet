package com.cometproject.server.game.items.music;

import com.cometproject.api.game.furniture.types.IMusicData;

/**
 * Carries music data data for the item subsystem.
 */
public class MusicData implements IMusicData {
    private int songId;
    private String name;

    private String title;
    private String artist;
    private String data;

    private int lengthSeconds;

    /**
     * Creates a music data instance for the item subsystem.
     *
     * @param songId Song id supplied by the caller.
     * @param name Name supplied by the caller.
     * @param title Title supplied by the caller.
     * @param artist Artist supplied by the caller.
     * @param data Data supplied by the caller.
     * @param lengthSeconds Length seconds supplied by the caller.
     */
    public MusicData(int songId, String name, String title, String artist, String data, int lengthSeconds) {
        this.songId = songId;
        this.name = name;
        this.title = title;
        this.artist = artist;
        this.data = data;
        this.lengthSeconds = lengthSeconds;
    }

    /**
     * Returns the song id for this item contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getSongId() {
        return songId;
    }

    /**
     * Returns the name for this item contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the title for this item contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Returns the artist for this item contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getArtist() {
        return artist;
    }

    /**
     * Returns the data for this item contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getData() {
        return data;
    }

    /**
     * Returns the length seconds for this item contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getLengthSeconds() {
        return this.lengthSeconds;
    }

    /**
     * Returns the length milliseconds for this item contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getLengthMilliseconds() {
        return lengthSeconds * 1000;
    }
}
