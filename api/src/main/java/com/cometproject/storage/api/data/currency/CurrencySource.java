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
