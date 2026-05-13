package com.cometproject.storage.api.data.currency;

/**
 * Maps a legacy or user-facing alias to a canonical currency code.
 */
public final class CurrencyAlias {
    private final String alias;
    private final String currencyCode;

    /**
     * Creates a currency alias.
     *
     * @param alias        the alias accepted by compatibility code.
     * @param currencyCode the canonical currency code.
     */
    public CurrencyAlias(final String alias, final String currencyCode) {
        this.alias = alias;
        this.currencyCode = currencyCode;
    }

    /**
     * Returns the alias.
     *
     * @return the alias.
     */
    public String getAlias() {
        return this.alias;
    }

    /**
     * Returns the canonical currency code.
     *
     * @return the currency code.
     */
    public String getCurrencyCode() {
        return this.currencyCode;
    }
}
