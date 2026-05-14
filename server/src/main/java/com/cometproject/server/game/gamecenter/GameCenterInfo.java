package com.cometproject.server.game.gamecenter;

/**
 * Describes game center info behavior for the Comet subsystem.
 */
public class GameCenterInfo {
    private int gameId;
    private String gameName;
    private String gamePath;
    private int roomId;

    /**
     * Creates a game center info instance for the Comet subsystem.
     *
     * @param gameId Game id supplied by the caller.
     * @param gameName Game name supplied by the caller.
     * @param gamePath Game path supplied by the caller.
     * @param roomId Room identifier used by the operation.
     */
    public GameCenterInfo(int gameId, String gameName, String gamePath, int roomId) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gamePath = gamePath;
        this.roomId = roomId;
    }

    /**
     * Returns the game id for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getGameId() {
        return this.gameId;
    }

    /**
     * Returns the game name for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getGameName() {
        return this.gameName;
    }

    /**
     * Returns the game path for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getGamePath() {
        return this.gamePath;
    }

    /**
     * Returns the game room id for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getGameRoomId() {
        return this.roomId;
    }
}