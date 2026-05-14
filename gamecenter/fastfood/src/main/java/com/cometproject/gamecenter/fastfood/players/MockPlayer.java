package com.cometproject.gamecenter.fastfood.players;

import com.cometproject.gamecenter.fastfood.net.FastFoodGameSession;
import com.cometproject.gamecenter.fastfood.net.FastFoodNetSession;

/**
 * Describes mock player behavior for the Fast Food game subsystem.
 */
public class MockPlayer extends FastFoodNetSession {

    /**
     * Creates a mock player instance for the Fast Food game subsystem.
     *
     * @param gameSession Game session supplied by the caller.
     */
    public MockPlayer(FastFoodGameSession gameSession) {
        super(null, gameSession, null);
    }
}
