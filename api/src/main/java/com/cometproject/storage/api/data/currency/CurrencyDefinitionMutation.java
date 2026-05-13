package com.cometproject.storage.api.data.currency;

/**
 * Request object for creating or updating a currency definition.
 */
public final class CurrencyDefinitionMutation {
    private final String code;
    private final String displayName;
    private final Integer protocolCurrencyId;
    private final boolean visibleInPurse;
    private final boolean enabled;
    private final int sortOrder;
    private final String iconKey;
    private final String nounSingular;
    private final String nounPlural;
    private final String description;
    private final CurrencyRolePolicy rolePolicy;

    /**
     * Creates a currency definition mutation.
     *
     * @param code               the stable currency code.
     * @param displayName        the human-readable display name.
     * @param protocolCurrencyId the Pixel protocol currency id, or null when absent.
     * @param visibleInPurse     whether the currency appears in purse packets.
     * @param enabled            whether normal writes are accepted.
     * @param sortOrder          the display ordering hint.
     * @param iconKey            the optional icon key.
     */
    public CurrencyDefinitionMutation(
            final String code,
            final String displayName,
            final Integer protocolCurrencyId,
            final boolean visibleInPurse,
            final boolean enabled,
            final int sortOrder,
            final String iconKey) {
        this(code, displayName, protocolCurrencyId, visibleInPurse, enabled, sortOrder, iconKey, displayName,
                displayName, "", CurrencyRolePolicy.ALL);
    }

    /**
     * Creates a currency definition mutation with presentation and role-policy metadata.
     *
     * @param code               the stable currency code.
     * @param displayName        the human-readable display name.
     * @param protocolCurrencyId the Pixel protocol currency id, or null when absent.
     * @param visibleInPurse     whether the currency appears in purse packets.
     * @param enabled            whether normal writes are accepted.
     * @param sortOrder          the display ordering hint.
     * @param iconKey            the optional icon key.
     * @param nounSingular       the singular noun.
     * @param nounPlural         the plural noun.
     * @param description        the optional description.
     * @param rolePolicy         the role policy.
     */
    public CurrencyDefinitionMutation(
            final String code,
            final String displayName,
            final Integer protocolCurrencyId,
            final boolean visibleInPurse,
            final boolean enabled,
            final int sortOrder,
            final String iconKey,
            final String nounSingular,
            final String nounPlural,
            final String description,
            final CurrencyRolePolicy rolePolicy) {
        this.code = code;
        this.displayName = displayName;
        this.protocolCurrencyId = protocolCurrencyId;
        this.visibleInPurse = visibleInPurse;
        this.enabled = enabled;
        this.sortOrder = sortOrder;
        this.iconKey = iconKey == null ? "" : iconKey;
        this.nounSingular = nounSingular == null ? "" : nounSingular;
        this.nounPlural = nounPlural == null ? "" : nounPlural;
        this.description = description == null ? "" : description;
        this.rolePolicy = rolePolicy == null ? CurrencyRolePolicy.ALL : rolePolicy;
    }

    /**
     * Returns the stable currency code.
     *
     * @return the currency code.
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Returns the display name.
     *
     * @return the display name.
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Returns the optional protocol currency id.
     *
     * @return the protocol currency id, or null when absent.
     */
    public Integer getProtocolCurrencyId() {
        return this.protocolCurrencyId;
    }

    /**
     * Returns whether the currency appears in purse packets.
     *
     * @return true when purse-visible.
     */
    public boolean isVisibleInPurse() {
        return this.visibleInPurse;
    }

    /**
     * Returns whether normal writes are accepted.
     *
     * @return true when enabled.
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Returns the display ordering hint.
     *
     * @return the sort order.
     */
    public int getSortOrder() {
        return this.sortOrder;
    }

    /**
     * Returns the optional icon key.
     *
     * @return the icon key, or an empty string when absent.
     */
    public String getIconKey() {
        return this.iconKey;
    }

    /**
     * Returns the singular display noun.
     *
     * @return the singular noun.
     */
    public String getNounSingular() {
        return this.nounSingular;
    }

    /**
     * Returns the plural display noun.
     *
     * @return the plural noun.
     */
    public String getNounPlural() {
        return this.nounPlural;
    }

    /**
     * Returns the management description.
     *
     * @return the description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the requested role policy.
     *
     * @return the role policy.
     */
    public CurrencyRolePolicy getRolePolicy() {
        return this.rolePolicy;
    }
}
