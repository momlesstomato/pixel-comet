package com.cometproject.storage.api.data.currency;

/**
 * Defines how rank-specific currency role rules are interpreted.
 */
public enum CurrencyRolePolicy {
    /**
     * Currency is available to every rank unless disabled.
     */
    ALL,

    /**
     * Currency is available only when an allow rule exists for the rank.
     */
    ALLOW_LIST,

    /**
     * Currency is available by default unless a rule denies the capability.
     */
    DENY_LIST
}
