package com.cometproject.storage.api.services;

import com.cometproject.storage.api.data.currency.CurrencyMovementResult;
import com.cometproject.storage.api.data.currency.CurrencySource;
import com.cometproject.storage.api.data.currency.ICurrencyDefinition;

import java.util.List;
import java.util.Map;

/**
 * Coordinates player currency inventory operations for gameplay, staff tooling, and management APIs.
 */
public interface ICurrencyService {
    /**
     * Adds a positive amount to a player balance.
     *
     * @param playerId     the target player id.
     * @param currencyCode the target currency code.
     * @param amount       the positive amount to add.
     * @param source       the audit source metadata.
     * @return the recorded movement result.
     */
    CurrencyMovementResult add(int playerId, String currencyCode, long amount, CurrencySource source);

    /**
     * Removes a positive amount from a player balance.
     *
     * @param playerId     the target player id.
     * @param currencyCode the target currency code.
     * @param amount       the positive amount to remove.
     * @param source       the audit source metadata.
     * @return the recorded movement result.
     */
    CurrencyMovementResult remove(int playerId, String currencyCode, long amount, CurrencySource source);

    /**
     * Sets a player balance to an exact non-negative value.
     *
     * @param playerId     the target player id.
     * @param currencyCode the target currency code.
     * @param amount       the exact new balance.
     * @param source       the audit source metadata.
     * @return the recorded movement result.
     */
    CurrencyMovementResult set(int playerId, String currencyCode, long amount, CurrencySource source);

    /**
     * Returns the current balances for a player keyed by currency code.
     *
     * @param playerId the target player id.
     * @return the player's inventory balances.
     */
    Map<String, Long> balances(int playerId);

    /**
     * Returns the current balance for one player currency.
     *
     * @param playerId     the target player id.
     * @param currencyCode the target currency code.
     * @return the current balance, or zero when absent.
     */
    long balance(int playerId, String currencyCode);

    /**
     * Returns all currency definitions known to the inventory.
     *
     * @return the configured currency definitions.
     */
    List<ICurrencyDefinition> definitions();

    /**
     * Returns definitions visible to a rank.
     *
     * @param rankId the rank id.
     * @return visible definitions for the rank.
     */
    List<ICurrencyDefinition> definitionsForRank(int rankId);

    /**
     * Returns protocol-visible balances keyed by Pixel currency id.
     *
     * @param playerId the target player id.
     * @return visible non-credit balances keyed by protocol currency id.
     */
    Map<Integer, Integer> protocolVisibleBalances(int playerId);

    /**
     * Returns whether a rank can view a currency.
     *
     * @param rankId       the rank id.
     * @param currencyCode the currency code or alias.
     * @return true when view is allowed.
     */
    boolean canView(int rankId, String currencyCode);

    /**
     * Returns whether a rank can earn a currency.
     *
     * @param rankId       the rank id.
     * @param currencyCode the currency code or alias.
     * @return true when earning is allowed.
     */
    boolean canEarn(int rankId, String currencyCode);

    /**
     * Returns whether a rank can spend a currency.
     *
     * @param rankId       the rank id.
     * @param currencyCode the currency code or alias.
     * @return true when spending is allowed.
     */
    boolean canSpend(int rankId, String currencyCode);

    /**
     * Returns whether a rank can manage a currency.
     *
     * @param rankId       the rank id.
     * @param currencyCode the currency code or alias.
     * @return true when management is allowed.
     */
    boolean canManage(int rankId, String currencyCode);

    /**
     * Resolves a canonical currency code or compatibility alias.
     *
     * @param currencyCodeOrAlias the code or alias.
     * @return the canonical currency code, or the original value when no alias exists.
     */
    String resolveCurrencyCode(String currencyCodeOrAlias);

    /**
     * Resolves the configured inventory currency for a Pixel Protocol activity-point id.
     *
     * @param protocolCurrencyId the Pixel Protocol activity-point id.
     * @return the configured currency code.
     * @throws IllegalStateException when no enabled currency is mapped to the protocol id.
     */
    String currencyCodeForProtocolId(int protocolCurrencyId);

    /**
     * Resolves the configured currency for a business use case.
     *
     * @param useCase the stable use-case key.
     * @return the configured currency code.
     * @throws IllegalStateException when no enabled currency is mapped to the use case.
     */
    String currencyCodeForUseCase(String useCase);

    /**
     * Returns the first enabled, non-credit currency intended for purse display.
     *
     * @return the first non-credit currency code.
     * @throws IllegalStateException when no non-credit currency is configured.
     */
    String firstNonCreditCurrencyCode();

    /**
     * Returns the display definition for one currency code or alias.
     *
     * @param currencyCodeOrAlias the currency code or alias.
     * @return the currency definition.
     * @throws IllegalStateException when the currency does not exist.
     */
    ICurrencyDefinition definition(String currencyCodeOrAlias);

    /**
     * Returns whether inventory-backed reads should be used for runtime balances.
     *
     * @return true when inventory reads are enabled.
     */
    boolean readFromInventory();
}
