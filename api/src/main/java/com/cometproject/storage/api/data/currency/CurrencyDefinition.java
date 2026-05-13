package com.cometproject.storage.api.data.currency;

import java.util.OptionalInt;

/**
 * Immutable currency definition loaded from storage.
 */
public final class CurrencyDefinition implements ICurrencyDefinition {
    private final long id;
    private final String code;
    private final String displayName;
    private final Integer protocolCurrencyId;
    private final boolean credits;
    private final boolean visibleInPurse;
    private final boolean enabled;
    private final int sortOrder;
    private final String iconKey;
    private final String nounSingular;
    private final String nounPlural;
    private final String description;
    private final CurrencyRolePolicy rolePolicy;

    /**
     * Creates a currency definition.
     *
     * @param id                 the stable numeric currency id.
     * @param code               the stable currency code.
     * @param displayName        the human-readable display name.
     * @param protocolCurrencyId the Pixel protocol currency id, or null when absent.
     * @param credits            whether this is the credits currency.
     * @param visibleInPurse     whether this appears in the purse packet.
     * @param enabled            whether normal writes are enabled.
     * @param sortOrder          the display ordering hint.
     * @param iconKey            the optional icon key.
     */
    public CurrencyDefinition(
            final long id,
            final String code,
            final String displayName,
            final Integer protocolCurrencyId,
            final boolean credits,
            final boolean visibleInPurse,
            final boolean enabled,
            final int sortOrder,
            final String iconKey) {
        this(id, code, displayName, protocolCurrencyId, credits, visibleInPurse, enabled, sortOrder,
                iconKey, displayName, displayName, "", CurrencyRolePolicy.ALL);
    }

    /**
     * Creates a currency definition with presentation and role-policy metadata.
     *
     * @param id                 the stable numeric currency id.
     * @param code               the stable currency code.
     * @param displayName        the human-readable display name.
     * @param protocolCurrencyId the Pixel protocol currency id, or null when absent.
     * @param credits            whether this is the credits currency.
     * @param visibleInPurse     whether this appears in the purse packet.
     * @param enabled            whether normal writes are enabled.
     * @param sortOrder          the display ordering hint.
     * @param iconKey            the optional icon key.
     * @param nounSingular       the singular noun.
     * @param nounPlural         the plural noun.
     * @param description        the optional description.
     * @param rolePolicy         the role policy.
     */
    public CurrencyDefinition(
            final long id,
            final String code,
            final String displayName,
            final Integer protocolCurrencyId,
            final boolean credits,
            final boolean visibleInPurse,
            final boolean enabled,
            final int sortOrder,
            final String iconKey,
            final String nounSingular,
            final String nounPlural,
            final String description,
            final CurrencyRolePolicy rolePolicy) {
        this.id = id;
        this.code = code;
        this.displayName = displayName;
        this.protocolCurrencyId = protocolCurrencyId;
        this.credits = credits;
        this.visibleInPurse = visibleInPurse;
        this.enabled = enabled;
        this.sortOrder = sortOrder;
        this.iconKey = iconKey == null ? "" : iconKey;
        this.nounSingular = nounSingular == null || nounSingular.isBlank() ? displayName : nounSingular;
        this.nounPlural = nounPlural == null || nounPlural.isBlank() ? displayName : nounPlural;
        this.description = description == null ? "" : description;
        this.rolePolicy = rolePolicy == null ? CurrencyRolePolicy.ALL : rolePolicy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OptionalInt getProtocolCurrencyId() {
        return this.protocolCurrencyId == null ? OptionalInt.empty() : OptionalInt.of(this.protocolCurrencyId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCredits() {
        return this.credits;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVisibleInPurse() {
        return this.visibleInPurse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSortOrder() {
        return this.sortOrder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIconKey() {
        return this.iconKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNounSingular() {
        return this.nounSingular;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNounPlural() {
        return this.nounPlural;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CurrencyRolePolicy getRolePolicy() {
        return this.rolePolicy;
    }
}
