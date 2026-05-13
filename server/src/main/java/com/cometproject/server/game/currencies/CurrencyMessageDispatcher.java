package com.cometproject.server.game.currencies;

import com.cometproject.api.game.players.IPlayer;
import com.cometproject.api.game.players.data.IPlayerData;
import com.cometproject.api.networking.sessions.ISession;
import com.cometproject.server.network.messages.outgoing.user.purse.SendCreditsMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.purse.UpdateActivityPointsMessageComposer;
import com.cometproject.storage.api.data.currency.CurrencyMovementResult;
import com.cometproject.storage.api.data.currency.ICurrencyDefinition;
import com.cometproject.storage.api.services.ICurrencyService;

/**
 * Sends Pixel Protocol purse updates after currency movements.
 */
public final class CurrencyMessageDispatcher {
    private final ICurrencyService currencyService;

    /**
     * Creates a dispatcher backed by the currency service metadata.
     *
     * @param currencyService the currency service.
     */
    public CurrencyMessageDispatcher(final ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * Sends the correct purse packet for a movement when the currency is protocol-visible.
     *
     * @param session the target session.
     * @param result  the recorded movement.
     * @return true when a packet was sent.
     */
    public boolean sendBalanceChange(final ISession session, final CurrencyMovementResult result) {
        if (session == null || result == null) {
            return false;
        }

        final ICurrencyDefinition movementDefinition = this.definition(result.getCurrencyCode());
        if (movementDefinition != null && movementDefinition.isCredits()) {
            session.send(new SendCreditsMessageComposer(Long.toString(result.getNewBalance())));
            return true;
        }

        for (ICurrencyDefinition definition : this.currencyService.definitions()) {
            if (definition.getCode().equals(result.getCurrencyCode())
                    && definition.isEnabled()
                    && definition.isVisibleInPurse()
                    && definition.getProtocolCurrencyId().isPresent()) {
                session.send(new UpdateActivityPointsMessageComposer(
                        Math.toIntExact(result.getNewBalance()),
                        Math.toIntExact(result.getDelta()),
                        definition.getProtocolCurrencyId().getAsInt()));
                return true;
            }
        }

        return false;
    }

    /**
     * Refreshes both purse packets for a player.
     *
     * @param player the target player.
     */
    public void sendFullBalance(final IPlayer player) {
        if (player == null || player.getSession() == null) {
            return;
        }

        player.sendBalance();
    }

    /**
     * Applies an inventory movement result to an online player's in-memory balance snapshot.
     *
     * @param playerData the mutable player data.
     * @param result     the movement result.
     */
    public void applySnapshot(final IPlayerData playerData, final CurrencyMovementResult result) {
        if (playerData == null || result == null) {
            return;
        }

        final int balance = Math.toIntExact(result.getNewBalance());
        final ICurrencyDefinition definition = this.definition(result.getCurrencyCode());
        if (definition == null) {
            return;
        }

        playerData.setCurrencyBalance(definition.getCode(), balance);
    }

    private ICurrencyDefinition definition(final String currencyCode) {
        return this.currencyService.definitions().stream()
                .filter(candidate -> candidate.getCode().equals(currencyCode))
                .findFirst()
                .orElse(null);
    }
}
