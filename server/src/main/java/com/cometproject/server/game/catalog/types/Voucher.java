package com.cometproject.server.game.catalog.types;

import com.cometproject.api.game.catalog.types.vouchers.IVoucher;
import com.cometproject.api.game.catalog.types.vouchers.VoucherStatus;
import com.cometproject.api.game.catalog.types.vouchers.VoucherType;

/**
 * Describes voucher behavior for the catalog subsystem.
 */
public class Voucher implements IVoucher {
    private final int id;
    private final VoucherType type;
    private final String data;
    private final String currencyCode;
    private final int createdBy;
    private final int createdAt;
    private final String claimedBy;
    private final int claimedAt;
    private final int limitUse;
    private final VoucherStatus status;
    private final String code;

    /**
     * Creates a voucher instance for the catalog subsystem.
     *
     * @param id Id supplied by the caller.
     * @param type Type supplied by the caller.
     * @param data Data supplied by the caller.
     * @param createdBy Created by supplied by the caller.
     * @param createdAt Created at supplied by the caller.
     * @param claimedBy Claimed by supplied by the caller.
     * @param claimedAt Claimed at supplied by the caller.
     * @param limitUse Limit use supplied by the caller.
     * @param status Status supplied by the caller.
     * @param code Code supplied by the caller.
     */
    public Voucher(int id, VoucherType type, String data, int createdBy, int createdAt, String claimedBy, int claimedAt, int limitUse, VoucherStatus status, String code) {
        this(id, type, data, "", createdBy, createdAt, claimedBy, claimedAt, limitUse, status, code);
    }

    /**
     * Creates a voucher instance for the catalog subsystem.
     *
     * @param id Id supplied by the caller.
     * @param type Type supplied by the caller.
     * @param data Data supplied by the caller.
     * @param currencyCode Currency code supplied by the caller.
     * @param createdBy Created by supplied by the caller.
     * @param createdAt Created at supplied by the caller.
     * @param claimedBy Claimed by supplied by the caller.
     * @param claimedAt Claimed at supplied by the caller.
     * @param limitUse Limit use supplied by the caller.
     * @param status Status supplied by the caller.
     * @param code Code supplied by the caller.
     */
    public Voucher(int id, VoucherType type, String data, String currencyCode, int createdBy, int createdAt, String claimedBy, int claimedAt, int limitUse, VoucherStatus status, String code) {
        this.id = id;
        this.type = type;
        this.data = data;
        this.currencyCode = currencyCode == null ? "" : currencyCode;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.claimedBy = claimedBy;
        this.claimedAt = claimedAt;
        this.limitUse = limitUse;
        this.status = status;
        this.code = code;
    }

    /**
     * Returns the id for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Returns the type for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public VoucherType getType() {
        return type;
    }

    /**
     * Returns the data for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getData() {
        return data;
    }

    /**
     * Returns the currency code for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getCurrencyCode() {
        return this.currencyCode;
    }

    /**
     * Returns the created by for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getCreatedBy() {
        return createdBy;
    }

    /**
     * Returns the created at for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getCreatedAt() {
        return createdAt;
    }

    /**
     * Returns the claimed by for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getClaimedBy() {
        return claimedBy;
    }

    /**
     * Returns the claimed at for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getClaimedAt() {
        return claimedAt;
    }

    /**
     * Returns the limit use for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getLimitUse() { return limitUse; }

    /**
     * Returns the status for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public VoucherStatus getStatus() {
        return status;
    }

    /**
     * Returns the code for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getCode() {
        return code;
    }
}
