package com.cometproject.storage.api.data.currency;

/**
 * Describes rank-specific inventory permissions for one currency.
 */
public interface ICurrencyRoleRule {
    /**
     * Returns the currency code controlled by this rule.
     *
     * @return the currency code.
     */
    String getCurrencyCode();

    /**
     * Returns the rank id controlled by this rule.
     *
     * @return the rank id.
     */
    int getRankId();

    /**
     * Returns whether this rank can see the currency.
     *
     * @return true when visible.
     */
    boolean canView();

    /**
     * Returns whether this rank can earn the currency.
     *
     * @return true when earning is allowed.
     */
    boolean canEarn();

    /**
     * Returns whether this rank can spend the currency.
     *
     * @return true when spending is allowed.
     */
    boolean canSpend();

    /**
     * Returns whether this rank can manage the currency.
     *
     * @return true when management is allowed.
     */
    boolean canManage();
}
