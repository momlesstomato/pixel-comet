package com.cometproject.storage.api.data.currency;

/**
 * Immutable rank-specific currency permission rule.
 */
public final class CurrencyRoleRule implements ICurrencyRoleRule {
    private final String currencyCode;
    private final int rankId;
    private final boolean canView;
    private final boolean canEarn;
    private final boolean canSpend;
    private final boolean canManage;

    /**
     * Creates a currency role rule.
     *
     * @param currencyCode the currency code.
     * @param rankId       the rank id.
     * @param canView      whether the rank can view the currency.
     * @param canEarn      whether the rank can earn the currency.
     * @param canSpend     whether the rank can spend the currency.
     * @param canManage    whether the rank can manage the currency.
     */
    public CurrencyRoleRule(
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
     * {@inheritDoc}
     */
    @Override
    public String getCurrencyCode() {
        return this.currencyCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRankId() {
        return this.rankId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canView() {
        return this.canView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canEarn() {
        return this.canEarn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canSpend() {
        return this.canSpend;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canManage() {
        return this.canManage;
    }
}
