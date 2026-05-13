package com.cometproject.storage.api.data.currency;

import java.util.OptionalInt;

/**
 * Describes a configured currency and its protocol mapping.
 */
public interface ICurrencyDefinition {
    /**
     * Returns the stable numeric currency id used by relational storage.
     *
     * @return the currency id.
     */
    long getId();

    /**
     * Returns the stable currency code used by server APIs.
     *
     * @return the lowercase currency code.
     */
    String getCode();

    /**
     * Returns the human-readable name used in management surfaces.
     *
     * @return the display name.
     */
    String getDisplayName();

    /**
     * Returns the Pixel client activity-points currency id when the currency is protocol-visible.
     *
     * @return the protocol currency id, or empty for credits and server-only currencies.
     */
    OptionalInt getProtocolCurrencyId();

    /**
     * Indicates whether this currency must be sent with the credits purse packet.
     *
     * @return true when this definition represents credits.
     */
    boolean isCredits();

    /**
     * Indicates whether this currency should appear in the activity-points purse packet.
     *
     * @return true when visible in the purse.
     */
    boolean isVisibleInPurse();

    /**
     * Indicates whether normal writes are allowed for this currency.
     *
     * @return true when enabled.
     */
    boolean isEnabled();

    /**
     * Returns the ordering hint for purse and management displays.
     *
     * @return the sort order.
     */
    int getSortOrder();

    /**
     * Returns the optional icon key for management/client presentation.
     *
     * @return the icon key, or an empty string when unset.
     */
    String getIconKey();

    /**
     * Returns the singular noun used in user-facing currency text.
     *
     * @return the singular noun, or display name when unset.
     */
    String getNounSingular();

    /**
     * Returns the plural noun used in user-facing currency text.
     *
     * @return the plural noun, or display name when unset.
     */
    String getNounPlural();

    /**
     * Returns the optional description for management surfaces.
     *
     * @return the description, or an empty string when absent.
     */
    String getDescription();

    /**
     * Returns how rank-specific rules are interpreted.
     *
     * @return the role policy.
     */
    CurrencyRolePolicy getRolePolicy();
}
