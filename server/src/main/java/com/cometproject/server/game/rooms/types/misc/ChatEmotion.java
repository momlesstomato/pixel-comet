package com.cometproject.server.game.rooms.types.misc;

/**
 * Enumerates chat emotion values used by the room subsystem.
 */
public enum ChatEmotion {
    NONE(0),
    SMILE(1),
    ANGRY(2),
    SHOCKED(3),
    SAD(4),
    LAUGH(6);

    private int emotionId;

    ChatEmotion(int emotionId) {
        this.emotionId = emotionId;
    }

    /**
     * Returns the emotion id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getEmotionId() {
        return emotionId;
    }
}
