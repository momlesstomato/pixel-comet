package com.cometproject.storage.api.data.currency;

/**
 * Defines stable capability keys for plugins that integrate with currency APIs.
 */
public final class CurrencyPluginCapabilities {
    /**
     * Allows reading player currency balances.
     */
    public static final String BALANCE_READ = "currency.balance.read";

    /**
     * Allows granting positive currency amounts.
     */
    public static final String BALANCE_GRANT = "currency.balance.grant";

    /**
     * Allows removing currency amounts.
     */
    public static final String BALANCE_REMOVE = "currency.balance.remove";

    /**
     * Allows setting exact currency balances.
     */
    public static final String BALANCE_SET = "currency.balance.set";

    /**
     * Allows managing currency definitions.
     */
    public static final String DEFINITION_MANAGE = "currency.definition.manage";

    /**
     * Allows managing currency role rules.
     */
    public static final String ROLE_RULE_MANAGE = "currency.role_rule.manage";

    /**
     * Allows managing currency aliases.
     */
    public static final String ALIAS_MANAGE = "currency.alias.manage";

    /**
     * Allows managing business use-case mappings.
     */
    public static final String USE_CASE_MANAGE = "currency.use_case.manage";

    private CurrencyPluginCapabilities() {
    }
}
