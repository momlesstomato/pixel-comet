package com.cometproject.storage.api.data.currency;

/**
 * Request object for replacing one currency role rule.
 */
public final class CurrencyRoleRuleMutation {
    private final String currencyCode;
    private final int rankId;
    private final boolean canView;
    private final boolean canEarn;
    private final boolean canSpend;
    private final boolean canManage;

    /**
     * Creates a currency role rule mutation.
     *
     * @param currencyCode the currency code.
     * @param rankId       the rank id.
     * @param canView      whether the rank can view the currency.
     * @param canEarn      whether the rank can earn the currency.
     * @param canSpend     whether the rank can spend the currency.
     * @param canManage    whether the rank can manage the currency.
     */
    public CurrencyRoleRuleMutation(
            final String currencyCode,
            final int rankId,
            final boolean canView,
            final boolean canEarn,
            final boolean canSpend,
            final boolean canManage) {
        this.currencyCode = currencyCode;
        this.rankId = rankId;
        this.canView = canView;
        this.canEarn = canEarn;
        this.canSpend = canSpend;
        this.canManage = canManage;
    }

    /**
     * Returns the currency code.
     *
     * @return the currency code.
     */
    public String getCurrencyCode() {
        return this.currencyCode;
    }

    /**
     * Returns the rank id.
     *
     * @return the rank id.
     */
    public int getRankId() {
        return this.rankId;
    }

    /**
     * Returns whether viewing is allowed.
     *
     * @return true when viewing is allowed.
     */
    public boolean canView() {
        return this.canView;
    }

    /**
     * Returns whether earning is allowed.
     *
     * @return true when earning is allowed.
     */
    public boolean canEarn() {
        return this.canEarn;
    }

    /**
     * Returns whether spending is allowed.
     *
     * @return true when spending is allowed.
     */
    public boolean canSpend() {
        return this.canSpend;
    }

    /**
     * Returns whether management is allowed.
     *
     * @return true when management is allowed.
     */
    public boolean canManage() {
        return this.canManage;
    }
}
