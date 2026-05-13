package com.cometproject.api.game.catalog.types.vouchers;

public interface IVoucher {
    int getId();

    VoucherType getType();

    String getData();

    /**
     * Returns the configured currency code when this voucher grants inventory currency.
     *
     * @return the currency code, or an empty string for non-currency vouchers.
     */
    String getCurrencyCode();

    int getCreatedBy();

    int getCreatedAt();

    String getClaimedBy();

    int getClaimedAt();

    int getLimitUse();

    VoucherStatus getStatus();

    String getCode();
}
