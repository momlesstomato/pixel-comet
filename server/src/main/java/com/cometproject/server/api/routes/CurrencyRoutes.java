package com.cometproject.server.api.routes;

import com.cometproject.server.api.ApiRequestUtils;
import com.cometproject.server.api.ApiResponseUtils;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.game.currencies.CurrencyService;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.storage.api.data.currency.CurrencyDefinitionMutation;
import com.cometproject.storage.api.data.currency.CurrencyMovementResult;
import com.cometproject.storage.api.data.currency.CurrencyRolePolicy;
import com.cometproject.storage.api.data.currency.CurrencyRoleRuleMutation;
import com.cometproject.storage.api.data.currency.CurrencySource;
import com.cometproject.storage.api.data.currency.CurrencyAlias;
import com.cometproject.storage.api.data.currency.ICurrencyDefinition;
import com.cometproject.storage.api.data.currency.ICurrencyMovement;
import com.cometproject.storage.api.data.currency.ICurrencyRoleRule;
import com.cometproject.storage.api.data.currency.exceptions.CurrencyAdjustmentException;
import com.cometproject.storage.api.data.currency.exceptions.CurrencyDisabledException;
import com.cometproject.storage.api.data.currency.exceptions.CurrencyNotFoundException;
import com.cometproject.storage.api.data.currency.exceptions.InsufficientCurrencyBalanceException;
import com.cometproject.storage.api.repositories.ICurrencyRepository;
import com.cometproject.storage.api.services.ICurrencyService;
import io.javalin.http.Context;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Javalin handlers for currency definition, balance, and movement management.
 */
public final class CurrencyRoutes {
    private CurrencyRoutes() {
    }

    /**
     * Returns all configured currency definitions.
     *
     * @param context the request context.
     */
    public static void listCurrencies(final Context context) {
        ApiResponseUtils.success(context, Map.of("currencies", currencyService().definitions().stream()
                .map(CurrencyRoutes::definitionResponse)
                .toList()));
    }

    /**
     * Creates or updates a currency definition.
     *
     * @param context the request context.
     */
    public static void upsertCurrency(final Context context) {
        final CurrencyDefinitionMutation mutation = mutationFromBody(context, context.pathParamMap().get("code"));

        if (mutation == null) {
            ApiResponseUtils.error(context, 400, "invalid_currency", "Currency code and display_name are required.");
            return;
        }

        try {
            final AtomicReference<ICurrencyDefinition> definition = new AtomicReference<>();
            currencyRepository().upsertDefinition(mutation, definition::set);
            ApiResponseUtils.success(context, definitionResponse(definition.get()));
        } catch (CurrencyAdjustmentException exception) {
            ApiResponseUtils.error(context, 400, "invalid_currency", exception.getMessage());
        }
    }

    /**
     * Disables a currency definition.
     *
     * @param context the request context.
     */
    public static void disableCurrency(final Context context) {
        final String currencyCode = context.pathParam("code");

        if (StringUtils.isBlank(currencyCode)) {
            ApiResponseUtils.error(context, 400, "invalid_currency", "Currency code is required.");
            return;
        }

        currencyRepository().disableDefinition(currencyCode);
        ApiResponseUtils.success(context, Map.of("currency_code", currencyCode, "enabled", false));
    }

    /**
     * Returns all inventory balances for a player.
     *
     * @param context the request context.
     */
    public static void playerBalances(final Context context) {
        final Integer playerId = ApiRequestUtils.pathInt(context, "id");

        if (playerId == null) {
            ApiResponseUtils.error(context, 400, "invalid_id", "Invalid player id.");
            return;
        }

        ApiResponseUtils.success(context, Map.of(
                "player_id", playerId,
                "balances", currencyService().balances(playerId)));
    }

    /**
     * Adds a positive amount to a player currency balance.
     *
     * @param context the request context.
     */
    public static void addBalance(final Context context) {
        adjustBalance(context, "add");
    }

    /**
     * Removes a positive amount from a player currency balance.
     *
     * @param context the request context.
     */
    public static void removeBalance(final Context context) {
        adjustBalance(context, "remove");
    }

    /**
     * Sets a player currency balance to an exact amount.
     *
     * @param context the request context.
     */
    public static void setBalance(final Context context) {
        adjustBalance(context, "set");
    }

    /**
     * Returns recent currency movements for a player.
     *
     * @param context the request context.
     */
    public static void movements(final Context context) {
        final Integer playerId = ApiRequestUtils.pathInt(context, "id");
        final int limit = parseLimit(context.queryParam("limit"));

        if (playerId == null) {
            ApiResponseUtils.error(context, 400, "invalid_id", "Invalid player id.");
            return;
        }

        final AtomicReference<List<ICurrencyMovement>> movements = new AtomicReference<>(List.of());
        currencyRepository().getMovements(playerId, limit, movements::set);

        ApiResponseUtils.success(context, Map.of(
                "player_id", playerId,
                "movements", movements.get().stream().map(CurrencyRoutes::movementResponse).toList()));
    }

    /**
     * Returns role rules for a currency.
     *
     * @param context the request context.
     */
    public static void listRoleRules(final Context context) {
        final AtomicReference<List<ICurrencyRoleRule>> rules = new AtomicReference<>(List.of());
        currencyRepository().getRoleRules(context.pathParam("code"), rules::set);

        ApiResponseUtils.success(context, Map.of(
                "currency_code", context.pathParam("code"),
                "roles", rules.get().stream().map(CurrencyRoutes::roleRuleResponse).toList()));
    }

    /**
     * Creates or replaces one role rule.
     *
     * @param context the request context.
     */
    public static void upsertRoleRule(final Context context) {
        final Integer rankId = ApiRequestUtils.pathInt(context, "rank_id");

        if (rankId == null) {
            ApiResponseUtils.error(context, 400, "invalid_rank", "Invalid rank id.");
            return;
        }

        try {
            final AtomicReference<ICurrencyRoleRule> roleRule = new AtomicReference<>();
            currencyRepository().upsertRoleRule(new CurrencyRoleRuleMutation(
                    context.pathParam("code"),
                    rankId,
                    ApiRequestUtils.bodyBoolean(context, "can_view", true),
                    ApiRequestUtils.bodyBoolean(context, "can_earn", true),
                    ApiRequestUtils.bodyBoolean(context, "can_spend", true),
                    ApiRequestUtils.bodyBoolean(context, "can_manage", false)), roleRule::set);

            ApiResponseUtils.success(context, roleRuleResponse(roleRule.get()));
        } catch (CurrencyAdjustmentException exception) {
            ApiResponseUtils.error(context, 400, "invalid_role_rule", exception.getMessage());
        }
    }

    /**
     * Deletes one role rule.
     *
     * @param context the request context.
     */
    public static void deleteRoleRule(final Context context) {
        final Integer rankId = ApiRequestUtils.pathInt(context, "rank_id");

        if (rankId == null) {
            ApiResponseUtils.error(context, 400, "invalid_rank", "Invalid rank id.");
            return;
        }

        currencyRepository().deleteRoleRule(context.pathParam("code"), rankId);
        ApiResponseUtils.success(context, Map.of(
                "currency_code", context.pathParam("code"),
                "rank_id", rankId,
                "deleted", true));
    }

    /**
     * Returns aliases for a currency.
     *
     * @param context the request context.
     */
    public static void listAliases(final Context context) {
        final AtomicReference<List<CurrencyAlias>> aliases = new AtomicReference<>(List.of());
        currencyRepository().getAliases(context.pathParam("code"), aliases::set);

        ApiResponseUtils.success(context, Map.of(
                "currency_code", context.pathParam("code"),
                "aliases", aliases.get().stream().map(CurrencyRoutes::aliasResponse).toList()));
    }

    /**
     * Creates or replaces an alias for a currency.
     *
     * @param context the request context.
     */
    public static void upsertAlias(final Context context) {
        final String alias = ApiRequestUtils.bodyField(context, "alias");

        if (StringUtils.isBlank(alias)) {
            ApiResponseUtils.error(context, 400, "invalid_alias", "Alias is required.");
            return;
        }

        try {
            currencyRepository().upsertAlias(alias, context.pathParam("code"));
            ApiResponseUtils.success(context, aliasResponse(new CurrencyAlias(alias, context.pathParam("code"))));
        } catch (CurrencyAdjustmentException exception) {
            ApiResponseUtils.error(context, 400, "invalid_alias", exception.getMessage());
        }
    }

    /**
     * Deletes a currency alias.
     *
     * @param context the request context.
     */
    public static void deleteAlias(final Context context) {
        currencyRepository().deleteAlias(context.pathParam("alias"));
        ApiResponseUtils.success(context, Map.of("alias", context.pathParam("alias"), "deleted", true));
    }

    private static void adjustBalance(final Context context, final String operation) {
        final Integer playerId = ApiRequestUtils.pathInt(context, "id");
        final String currencyCode = context.pathParam("code");
        final Long amount = ApiRequestUtils.bodyLong(context, "amount");

        if (playerId == null) {
            ApiResponseUtils.error(context, 400, "invalid_id", "Invalid player id.");
            return;
        }

        if (StringUtils.isBlank(currencyCode)) {
            ApiResponseUtils.error(context, 400, "invalid_currency", "Currency code is required.");
            return;
        }

        if (amount == null) {
            ApiResponseUtils.error(context, 400, "invalid_amount", "Amount must be a non-negative integer.");
            return;
        }

        try {
            final CurrencySource source = new CurrencySource(
                    "api",
                    "",
                    "management_api",
                    ApiRequestUtils.bodyField(context, "source_ref"),
                    ApiRequestUtils.firstNonBlank(ApiRequestUtils.bodyField(context, "reason"), "Management API adjustment"));
            final CurrencyMovementResult result = switch (operation) {
                case "add" -> currencyService().add(playerId, currencyCode, amount, source);
                case "remove" -> currencyService().remove(playerId, currencyCode, amount, source);
                default -> currencyService().set(playerId, currencyCode, amount, source);
            };

            if (ApiRequestUtils.bodyBoolean(context, "notify_player", true)) {
                notifyOnlinePlayer(playerId, result);
            }

            ApiResponseUtils.success(context, movementResponse(result));
        } catch (CurrencyDisabledException exception) {
            ApiResponseUtils.error(context, 409, "currency_disabled", exception.getMessage());
        } catch (CurrencyNotFoundException exception) {
            ApiResponseUtils.error(context, 400, "invalid_currency", exception.getMessage());
        } catch (InsufficientCurrencyBalanceException exception) {
            ApiResponseUtils.error(context, 400, "negative_balance", exception.getMessage());
        } catch (CurrencyAdjustmentException exception) {
            ApiResponseUtils.error(context, 400, "invalid_amount", exception.getMessage());
        }
    }

    private static CurrencyDefinitionMutation mutationFromBody(final Context context, final String pathCode) {
        final String code = ApiRequestUtils.firstNonBlank(pathCode, ApiRequestUtils.bodyField(context, "code"));
        final ICurrencyDefinition existingDefinition = existingDefinition(code);
        final String displayName = ApiRequestUtils.firstNonBlank(
                ApiRequestUtils.bodyField(context, "display_name"),
                existingDefinition == null ? null : existingDefinition.getDisplayName());

        if (StringUtils.isBlank(code) || StringUtils.isBlank(displayName)) {
            return null;
        }

        final Integer requestedSortOrder = ApiRequestUtils.bodyInt(context, "sort_order");

        return new CurrencyDefinitionMutation(
                code,
                displayName,
                ApiRequestUtils.bodyInt(context, "protocol_currency_id") == null && existingDefinition != null
                        ? existingDefinition.getProtocolCurrencyId().isPresent()
                                ? existingDefinition.getProtocolCurrencyId().getAsInt()
                                : null
                        : ApiRequestUtils.bodyInt(context, "protocol_currency_id"),
                ApiRequestUtils.bodyBoolean(
                        context,
                        "visible_in_purse",
                        existingDefinition == null || existingDefinition.isVisibleInPurse()),
                ApiRequestUtils.bodyBoolean(
                        context,
                        "enabled",
                        existingDefinition == null || existingDefinition.isEnabled()),
                requestedSortOrder == null && existingDefinition != null ? existingDefinition.getSortOrder() : requestedSortOrder == null ? 0 : requestedSortOrder,
                ApiRequestUtils.firstNonBlank(
                        ApiRequestUtils.bodyField(context, "icon_key"),
                        existingDefinition == null ? null : existingDefinition.getIconKey()),
                ApiRequestUtils.firstNonBlank(
                        ApiRequestUtils.bodyField(context, "noun_singular"),
                        existingDefinition == null ? null : existingDefinition.getNounSingular()),
                ApiRequestUtils.firstNonBlank(
                        ApiRequestUtils.bodyField(context, "noun_plural"),
                        existingDefinition == null ? null : existingDefinition.getNounPlural()),
                ApiRequestUtils.firstNonBlank(
                        ApiRequestUtils.bodyField(context, "description"),
                        existingDefinition == null ? null : existingDefinition.getDescription()),
                rolePolicy(context, existingDefinition));
    }

    private static ICurrencyDefinition existingDefinition(final String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }

        final AtomicReference<ICurrencyDefinition> definition = new AtomicReference<>();
        currencyRepository().getDefinition(code, definition::set);
        return definition.get();
    }

    private static void notifyOnlinePlayer(final int playerId, final CurrencyMovementResult result) {
        final Session session = NetworkManager.getInstance().getSessions().getByPlayerId(playerId);

        if (session != null) {
            CometBootstrap.resolve(CurrencyService.class).getMessageDispatcher().sendBalanceChange(session, result);
        }
    }

    private static Map<String, Object> definitionResponse(final ICurrencyDefinition definition) {
        final Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", definition.getId());
        response.put("code", definition.getCode());
        response.put("display_name", definition.getDisplayName());
        response.put("protocol_currency_id", definition.getProtocolCurrencyId().isPresent()
                ? definition.getProtocolCurrencyId().getAsInt()
                : "");
        response.put("is_credits", definition.isCredits());
        response.put("visible_in_purse", definition.isVisibleInPurse());
        response.put("enabled", definition.isEnabled());
        response.put("sort_order", definition.getSortOrder());
        response.put("icon_key", definition.getIconKey());
        response.put("noun_singular", definition.getNounSingular());
        response.put("noun_plural", definition.getNounPlural());
        response.put("description", definition.getDescription());
        response.put("role_policy", definition.getRolePolicy().name());
        return response;
    }

    private static Map<String, Object> roleRuleResponse(final ICurrencyRoleRule roleRule) {
        final Map<String, Object> response = new LinkedHashMap<>();
        response.put("currency_code", roleRule.getCurrencyCode());
        response.put("rank_id", roleRule.getRankId());
        response.put("can_view", roleRule.canView());
        response.put("can_earn", roleRule.canEarn());
        response.put("can_spend", roleRule.canSpend());
        response.put("can_manage", roleRule.canManage());
        return response;
    }

    private static Map<String, Object> aliasResponse(final CurrencyAlias alias) {
        return Map.of("alias", alias.getAlias(), "currency_code", alias.getCurrencyCode());
    }

    private static Map<String, Object> movementResponse(final ICurrencyMovement movement) {
        final Map<String, Object> response = new LinkedHashMap<>();
        response.put("movement_id", movement.getId());
        response.put("player_id", movement.getPlayerId());
        response.put("currency_code", movement.getCurrencyCode());
        response.put("operation", movement.getOperation().name());
        response.put("old_balance", movement.getOldBalance());
        response.put("delta", movement.getDelta());
        response.put("new_balance", movement.getNewBalance());
        response.put("actor_type", movement.getActorType());
        response.put("actor_id", movement.getActorId());
        response.put("source_type", movement.getSourceType());
        response.put("source_ref", movement.getSourceRef());
        response.put("reason", movement.getReason());
        response.put("created_at", movement.getCreatedAt().toString());
        return response;
    }

    private static int parseLimit(final String rawLimit) {
        if (!StringUtils.isNumeric(rawLimit)) {
            return 100;
        }

        return Integer.parseInt(rawLimit);
    }

    private static ICurrencyService currencyService() {
        return CometBootstrap.resolve(ICurrencyService.class);
    }

    private static ICurrencyRepository currencyRepository() {
        return CometBootstrap.resolve(ICurrencyRepository.class);
    }

    private static CurrencyRolePolicy rolePolicy(
            final Context context,
            final ICurrencyDefinition existingDefinition) {
        final String rolePolicy = ApiRequestUtils.bodyField(context, "role_policy");
        if (StringUtils.isBlank(rolePolicy)) {
            return existingDefinition == null ? CurrencyRolePolicy.ALL : existingDefinition.getRolePolicy();
        }

        try {
            return CurrencyRolePolicy.valueOf(rolePolicy.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new CurrencyAdjustmentException("Unknown role_policy: " + rolePolicy, exception);
        }
    }
}
