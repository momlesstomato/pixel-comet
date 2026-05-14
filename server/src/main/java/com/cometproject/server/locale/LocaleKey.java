package com.cometproject.server.locale;

/**
 * Describes locale key behavior for the Comet subsystem.
 */
public class LocaleKey {
    public static final LocaleKey catalogErrorTooMany = new LocaleKey("catalog.error.toomany", "You can only purchase 1000 items!");
    public static final LocaleKey catalogErrorGiftTooFast = new LocaleKey("catalog.error.gifttoofast", "You're sending gifts way too fast!");
    public static final LocaleKey catalogErrorNoOffer = new LocaleKey("catalog.error.nooffer", "This item doesn't allow bulk purchases!");
    public static final LocaleKey catalogErrorNotEnough = new LocaleKey("catalog.error.notenough", "You don't have enough coins to purchase this item!");

    private final String key;
    private final String defaultValue;

    /**
     * Creates a locale key instance for the Comet subsystem.
     *
     * @param key Key supplied by the caller.
     * @param defaultValue Default value supplied by the caller.
     */
    public LocaleKey(final String key, final String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    /**
     * Returns the key for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the default value for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getDefaultValue() {
        return defaultValue;
    }
}
