package com.cometproject.storage.api.data.currency;

/**
 * Describes who initiated a currency movement and which subsystem caused it.
 */
public final class CurrencySource {
    private final String actorType;
    private final String actorId;
    private final String sourceType;
    private final String sourceRef;
    private final String reason;

    /**
     * Creates currency movement source metadata.
     *
     * @param actorType  the actor type, such as {@code system}, {@code staff}, or {@code api}.
     * @param actorId    the actor id, or null when absent.
     * @param sourceType the source subsystem.
     * @param sourceRef  the optional source correlation reference.
     * @param reason     the human-readable reason.
     */
    public CurrencySource(
            final String actorType,
            final String actorId,
            final String sourceType,
            final String sourceRef,
            final String reason) {
        this.actorType = actorType;
        this.actorId = actorId == null ? "" : actorId;
        this.sourceType = sourceType;
        this.sourceRef = sourceRef == null ? "" : sourceRef;
        this.reason = reason;
    }

    /**
     * Returns system migration metadata.
     *
     * @param reason the migration reason.
     * @return a system source object.
     */
    public static CurrencySource system(final String reason) {
        return new CurrencySource("system", "", "system", "", reason);
    }

    /**
     * Returns system source metadata with an explicit reference.
     *
     * @param sourceRef the source correlation reference.
     * @param reason    the movement reason.
     * @return a system source object.
     */
    public static CurrencySource system(final String sourceRef, final String reason) {
        return new CurrencySource("system", "", "system", sourceRef, reason);
    }

    /**
     * Returns plugin source metadata.
     *
     * @param pluginId  the plugin/module identifier.
     * @param sourceRef the source correlation reference.
     * @param reason    the movement reason.
     * @return a plugin source object.
     */
    public static CurrencySource plugin(final String pluginId, final String sourceRef, final String reason) {
        return new CurrencySource("plugin", pluginId, "plugin", sourceRef, reason);
    }

    /**
     * Returns management API source metadata.
     *
     * @param apiClientId the API client identifier, or empty when not available.
     * @param sourceRef   the source correlation reference.
     * @param reason      the movement reason.
     * @return an API source object.
     */
    public static CurrencySource api(final String apiClientId, final String sourceRef, final String reason) {
        return new CurrencySource("api", apiClientId, "management_api", sourceRef, reason);
    }

    /**
     * Returns staff source metadata.
     *
     * @param staffId   the staff player id.
     * @param sourceRef the source correlation reference.
     * @param reason    the movement reason.
     * @return a staff source object.
     */
    public static CurrencySource staff(final int staffId, final String sourceRef, final String reason) {
        return new CurrencySource("staff", Integer.toString(staffId), "staff_command", sourceRef, reason);
    }

    /**
     * Returns gameplay source metadata.
     *
     * @param sourceType the gameplay subsystem.
     * @param sourceRef  the source correlation reference.
     * @param reason     the movement reason.
     * @return a gameplay source object.
     */
    public static CurrencySource gameplay(final String sourceType, final String sourceRef, final String reason) {
        return new CurrencySource("system", "", sourceType, sourceRef, reason);
    }

    /**
     * Returns the actor type.
     *
     * @return the actor type.
     */
    public String getActorType() {
        return this.actorType;
    }

    /**
     * Returns the actor id.
     *
     * @return the actor id, or an empty string when absent.
     */
    public String getActorId() {
        return this.actorId;
    }

    /**
     * Returns the source type.
     *
     * @return the source type.
     */
    public String getSourceType() {
        return this.sourceType;
    }

    /**
     * Returns the source reference.
     *
     * @return the source reference, or an empty string when absent.
     */
    public String getSourceRef() {
        return this.sourceRef;
    }

    /**
     * Returns the movement reason.
     *
     * @return the reason.
     */
    public String getReason() {
        return this.reason;
    }
}
