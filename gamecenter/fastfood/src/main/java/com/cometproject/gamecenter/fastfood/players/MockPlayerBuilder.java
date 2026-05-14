package com.cometproject.gamecenter.fastfood.players;

import com.cometproject.gamecenter.fastfood.net.FastFoodGameSession;

/**
 * Describes mock player builder behavior for the Fast Food game subsystem.
 */
public class MockPlayerBuilder {

    private final FastFoodGameSession gameSession = new FastFoodGameSession();

    /**
     * Executes with username for this Fast Food game contract.
     *
     * @param username Username supplied by the caller.
     * @return Result produced by the operation.
     */
    public MockPlayerBuilder withUsername(String username) {
        this.gameSession.setUsername(username);
        return this;
    }

    /**
     * Executes with figure for this Fast Food game contract.
     *
     * @param figure Figure supplied by the caller.
     * @return Result produced by the operation.
     */
    public MockPlayerBuilder withFigure(String figure) {
        this.gameSession.setFigure(figure);
        this.gameSession.setGender("m");

        return this;
    }

    /**
     * Executes with player id for this Fast Food game contract.
     *
     * @param id Id supplied by the caller.
     * @return Result produced by the operation.
     */
    public MockPlayerBuilder withPlayerId(int id) {
        this.gameSession.setPlayerId(id);
        return this;
    }

    /**
     * Executes create for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    public MockPlayer create() {
        return new MockPlayer(this.gameSession);
    }
}
