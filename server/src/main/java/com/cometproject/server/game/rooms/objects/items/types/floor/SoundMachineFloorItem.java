package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.furniture.types.IMusicData;
import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.utilities.JsonUtil;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.game.items.music.SongItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFactory;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.music.PlayMusicMessageComposer;
import com.cometproject.server.protocol.messages.MessageComposer;
import com.cometproject.server.utilities.attributes.Stateable;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes sound machine floor item behavior for the room subsystem.
 */
public class SoundMachineFloorItem extends RoomItemFloor implements Stateable {
    public static final int MAX_CAPACITY = 20;

    private boolean isPlaying = false;
    private int currentPlayingIndex = -1;
    private long startTimestamp = 0;

    private List<SongItemData> songs;

    private boolean pendingPlay = false;

    /**
     * Creates a sound machine floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public SoundMachineFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);

        final String data = itemData.getData();

        if (data.startsWith("##jukeboxOn[")) {
            this.songs = JsonUtil.getInstance().fromJson(data.replace("##jukeboxOn", ""), new TypeToken<ArrayList<SongItemData>>() {
            }.getType());

            this.pendingPlay = true;
            this.setTicks(RoomItemFactory.getProcessTime(1.5));
        } else if (data.startsWith("##jukeboxOff[")) {
            this.songs = JsonUtil.getInstance().fromJson(data.replace("##jukeboxOff", ""), new TypeToken<ArrayList<SongItemData>>() {
            }.getType());
        } else if (data.startsWith("[")) {
            this.songs = JsonUtil.getInstance().fromJson(data, new TypeToken<ArrayList<SongItemData>>() {
            }.getType());
        } else {
            this.songs = new ArrayList<>();
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
        if (entity instanceof PlayerEntity) {
            if (((PlayerEntity) entity).getPlayerId() != this.getRoom().getData().getOwnerId() && !((PlayerEntity) entity).getPlayer().getPermissions().getRank().roomFullControl()) {
                return false;
            }
        }

        if (requestData == -1) return false;

        if (this.isPlaying) {
            this.stop();
        } else {
            this.play();
        }

        this.sendUpdate();
        return true;
    }

    /**
     * Handles the tick complete callback for this room contract.
     */
    @Override
    public void onTickComplete() {
        if (this.pendingPlay) {
            this.play();
            this.pendingPlay = false;
            this.sendUpdate();
            return;
        }

        if (this.isPlaying) {
            if (this.currentPlayingIndex >= this.getSongs().size()) {
                this.stop();
                this.sendUpdate();
                return;
            }

            for (PlayerEntity entity : this.getRoom().getEntities().getPlayerEntities()) {
                if (!entity.hasAttribute("traxSent") && (System.currentTimeMillis() - entity.getJoinTime() >= 1100)) {
                    entity.getPlayer().getSession().send(this.getComposer());
                    entity.setAttribute("traxSent", true);
                }
            }

            if (this.currentPlayingIndex != -1) {
                SongItemData songItemData = this.getSongs().get(this.currentPlayingIndex);

                if (songItemData != null) {
                    IMusicData musicData = ItemManager.getInstance().getMusicData(songItemData.getSongId());

                    if (musicData != null) {
                        if (this.timePlaying() >= (musicData.getLengthSeconds() + 1.0)) {
                            this.playNextSong();
                        }
                    }
                }
            }

            this.setTicks(RoomItemFactory.getProcessTime(1.0));
        }
    }

    /**
     * Handles the pickup callback for this room contract.
     */
    @Override
    public void onPickup() {
        this.getItemData().setData(this.getDataObject());
    }

    /**
     * Handles the placed callback for this room contract.
     */
    @Override
    public void onPlaced() {
        if (this.isPlaying) {
            this.broadcastSong();
        }
    }

    /**
     * Adds song to this room contract.
     *
     * @param songItemData Song item data supplied by the caller.
     */
    public void addSong(SongItemData songItemData) {
        this.songs.add(songItemData);
    }

    /**
     * Removes song from this room contract.
     *
     * @param index Index supplied by the caller.
     * @return Result produced by the mutation.
     */
    public SongItemData removeSong(int index) {
        if (index == this.currentPlayingIndex) {
            this.playNextSong();
        }

        SongItemData songItemData = this.songs.get(index);
        this.songs.remove(index);

        return songItemData;
    }

    /**
     * Executes play for this room contract.
     */
    public void play() {
        if (this.songs.size() == 0) return;

        this.isPlaying = true;
        this.currentPlayingIndex = -1;

        this.playNextSong();
        this.setTicks(RoomItemFactory.getProcessTime(1.0));
    }

    /**
     * Stops this room component.
     */
    public void stop() {
        this.isPlaying = false;
        this.currentPlayingIndex = -1;

        this.broadcastSong();
    }

    /**
     * Executes play next song for this room contract.
     */
    public void playNextSong() {
        this.startTimestamp = Comet.getTime();
        this.currentPlayingIndex++;

        if (currentPlayingIndex >= this.getSongs().size()) {
            currentPlayingIndex = -1;
        }

        this.broadcastSong();
    }

    /**
     * Executes broadcast song for this room contract.
     */
    public void broadcastSong() {
        if (!this.isPlaying || this.currentPlayingIndex >= this.songs.size()) {
            this.getRoom().getEntities().broadcastMessage(new PlayMusicMessageComposer());

            if (this.isPlaying)
                this.isPlaying = false;
            return;
        }

        for (PlayerEntity entity : this.getRoom().getEntities().getPlayerEntities()) {
            if (!entity.hasAttribute("traxSent")) {
                entity.setAttribute("traxSent", true);
            }

            if (entity.getPlayer() != null && entity.getPlayer().getSession() != null)
                entity.getPlayer().getSession().send(this.getComposer());
        }
    }

    /**
     * Returns the composer for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public MessageComposer getComposer() {
        if (this.currentPlayingIndex == -1) return null;

        SongItemData songItemData = this.songs.get(this.currentPlayingIndex);

        if (songItemData == null) {
            return null;
        }

        int songId = songItemData.getSongId();
        return new PlayMusicMessageComposer(songId, this.currentPlayingIndex, this.songTimeSync());
    }

    private int timePlaying() {
        return (int) (Comet.getTime() - this.startTimestamp);
    }

    /**
     * Executes song time sync for this room contract.
     *
     * @return Result produced by the operation.
     */
    public int songTimeSync() {
        if (!this.isPlaying || this.currentPlayingIndex < 0 || this.currentPlayingIndex >= this.getSongs().size()) {
            return 0;
        }

        SongItemData songItemData = this.getSongs().get(this.currentPlayingIndex);

        if (songItemData != null) {
            IMusicData musicData = ItemManager.getInstance().getMusicData(songItemData.getSongId());

            if (musicData != null) {
                if (this.timePlaying() >= musicData.getLengthSeconds())
                    return musicData.getLengthSeconds();
            }
        }

        return this.timePlaying() * 1000;
    }

    /**
     * Returns the data object for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDataObject() {
        return (this.isPlaying ? "##jukeboxOn" : "##jukeboxOff") + JsonUtil.getInstance().toJson(this.songs);
    }

    /**
     * Handles the unload callback for this room contract.
     */
    @Override
    public void onUnload() {
        this.getSongs().clear();
    }

    /**
     * Returns the songs for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public List<SongItemData> getSongs() {
        return this.songs;
    }

    /**
     * Returns the state for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean getState() {
        return this.isPlaying;
    }
}
