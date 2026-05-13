package com.cometproject.storage.api.data.currency;

import java.time.Instant;

/**
 * Immutable result returned after a successful currency movement.
 */
public final class CurrencyMovementResult implements ICurrencyMovement {
    private final long id;
    private final int playerId;
    private final long currencyId;
    private final String currencyCode;
    private final CurrencyOperation operation;
    private final long delta;
    private final long oldBalance;
    private final long newBalance;
    private final String actorType;
    private final String actorId;
    private final String sourceType;
    private final String sourceRef;
    private final String reason;
    private final Instant createdAt;

    /**
     * Creates a movement result.
     *
     * @param id           the movement id.
     * @param playerId     the affected player id.
     * @param currencyId   the affected currency id.
     * @param currencyCode the affected currency code.
     * @param operation    the operation.
     * @param delta        the signed balance delta.
     * @param oldBalance   the old balance.
     * @param newBalance   the new balance.
     * @param actorType    the actor type.
     * @param actorId      the actor id.
     * @param sourceType   the source type.
     * @param sourceRef    the source reference.
     * @param reason       the movement reason.
     * @param createdAt    when the movement was recorded.
     */
    public CurrencyMovementResult(
            final long id,
            final int playerId,
            final long currencyId,
            final String currencyCode,
            final CurrencyOperation operation,
            final long delta,
            final long oldBalance,
            final long newBalance,
            final String actorType,
            final String actorId,
            final String sourceType,
            final String sourceRef,
            final String reason,
            final Instant createdAt) {
        this.id = id;
        this.playerId = playerId;
        this.currencyId = currencyId;
        this.currencyCode = currencyCode;
        this.operation = operation;
        this.delta = delta;
        this.oldBalance = oldBalance;
        this.newBalance = newBalance;
        this.actorType = actorType;
        this.actorId = actorId == null ? "" : actorId;
        this.sourceType = sourceType;
        this.sourceRef = sourceRef == null ? "" : sourceRef;
        this.reason = reason;
        this.createdAt = createdAt;
    }

    /**
     * Returns the affected currency id.
     *
     * @return the currency id.
     */
    public long getCurrencyId() {
        return this.currencyId;
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
    public int getPlayerId() {
        return this.playerId;
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
    public CurrencyOperation getOperation() {
        return this.operation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getDelta() {
        return this.delta;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getOldBalance() {
        return this.oldBalance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getNewBalance() {
        return this.newBalance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getActorType() {
        return this.actorType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getActorId() {
        return this.actorId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSourceType() {
        return this.sourceType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSourceRef() {
        return this.sourceRef;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getReason() {
        return this.reason;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instant getCreatedAt() {
        return this.createdAt;
    }
}
