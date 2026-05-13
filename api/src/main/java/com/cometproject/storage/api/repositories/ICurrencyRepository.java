package com.cometproject.storage.api.repositories;

import com.cometproject.storage.api.data.currency.CurrencyAdjustment;
import com.cometproject.storage.api.data.currency.CurrencyAlias;
import com.cometproject.storage.api.data.currency.CurrencyDefinitionMutation;
import com.cometproject.storage.api.data.currency.CurrencyMovementResult;
import com.cometproject.storage.api.data.currency.CurrencyRoleRuleMutation;
import com.cometproject.storage.api.data.currency.ICurrencyDefinition;
import com.cometproject.storage.api.data.currency.ICurrencyMovement;
import com.cometproject.storage.api.data.currency.ICurrencyRoleRule;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Provides persistence operations for currency definitions, balances, and movements.
 *
 * <p>Implementations must apply balance changes atomically and record exactly one movement row
 * for every successful adjustment.
 */
public interface ICurrencyRepository {
    /**
     * Loads all configured currency definitions ordered for stable display.
     *
     * @param consumer the callback receiving the definitions.
     */
    void getDefinitions(Consumer<List<ICurrencyDefinition>> consumer);

    /**
     * Loads one configured currency definition.
     *
     * @param currencyCode the requested currency code.
     * @param consumer     the callback receiving the definition, or null when absent.
     */
    void getDefinition(String currencyCode, Consumer<ICurrencyDefinition> consumer);

    /**
     * Loads all persisted inventory balances for a player.
     *
     * @param playerId the target player id.
     * @param consumer the callback receiving balances keyed by currency code.
     */
    void getBalances(int playerId, Consumer<Map<String, Long>> consumer);

    /**
     * Loads the persisted rank for a player.
     *
     * @param playerId the target player id.
     * @param consumer the callback receiving the rank, or zero when absent.
     */
    void getPlayerRank(int playerId, Consumer<Integer> consumer);

    /**
     * Applies one atomic balance adjustment and writes the matching audit movement.
     *
     * @param adjustment the requested balance adjustment.
     * @param consumer   the callback receiving the persisted movement result.
     */
    void adjust(CurrencyAdjustment adjustment, Consumer<CurrencyMovementResult> consumer);

    /**
     * Creates or updates a non-credits currency definition.
     *
     * @param mutation the requested definition metadata.
     * @param consumer the callback receiving the persisted definition.
     */
    void upsertDefinition(CurrencyDefinitionMutation mutation, Consumer<ICurrencyDefinition> consumer);

    /**
     * Soft-disables a currency definition while preserving balances and movements.
     *
     * @param currencyCode the currency code to disable.
     */
    void disableDefinition(String currencyCode);

    /**
     * Loads role rules for a currency.
     *
     * @param currencyCode the currency code.
     * @param consumer     the callback receiving role rules.
     */
    void getRoleRules(String currencyCode, Consumer<List<ICurrencyRoleRule>> consumer);

    /**
     * Creates or updates one currency role rule.
     *
     * @param mutation the role rule mutation.
     * @param consumer the callback receiving the persisted rule.
     */
    void upsertRoleRule(CurrencyRoleRuleMutation mutation, Consumer<ICurrencyRoleRule> consumer);

    /**
     * Deletes one currency role rule.
     *
     * @param currencyCode the currency code.
     * @param rankId       the rank id.
     */
    void deleteRoleRule(String currencyCode, int rankId);

    /**
     * Loads aliases for a currency.
     *
     * @param currencyCode the currency code.
     * @param consumer     the callback receiving aliases.
     */
    void getAliases(String currencyCode, Consumer<List<CurrencyAlias>> consumer);

    /**
     * Resolves an alias to its canonical currency code.
     *
     * @param alias    the alias to resolve.
     * @param consumer the callback receiving the currency code, or null when absent.
     */
    void resolveAlias(String alias, Consumer<String> consumer);

    /**
     * Resolves a business use-case key to its configured currency code.
     *
     * @param useCase  the stable use-case key.
     * @param consumer the callback receiving the currency code, or null when absent.
     */
    void resolveUseCase(String useCase, Consumer<String> consumer);

    /**
     * Creates or replaces a currency alias.
     *
     * @param alias        the alias.
     * @param currencyCode the canonical currency code.
     */
    void upsertAlias(String alias, String currencyCode);

    /**
     * Deletes a currency alias.
     *
     * @param alias the alias.
     */
    void deleteAlias(String alias);

    /**
     * Loads recent movement records for a player.
     *
     * @param playerId the target player id.
     * @param limit    the maximum number of movement rows to return.
     * @param consumer the callback receiving movements sorted newest first.
     */
    void getMovements(int playerId, int limit, Consumer<List<ICurrencyMovement>> consumer);
}
