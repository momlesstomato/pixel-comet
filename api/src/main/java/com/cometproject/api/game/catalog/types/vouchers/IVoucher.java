package com.cometproject.api.game.catalog.types.vouchers;

/**
 * Defines the i voucher contract for the catalog subsystem.
 */
public interface IVoucher {
    /**
     * Returns the id associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Returns the type associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    VoucherType getType();

    /**
     * Returns the data associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getData();

    /**
     * Returns the configured currency code when this voucher grants inventory currency.
     *
     * @return the currency code, or an empty string for non-currency vouchers.
     */
    String getCurrencyCode();

    /**
     * Returns the created by associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getCreatedBy();

    /**
     * Returns the created at associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getCreatedAt();

    /**
     * Returns the claimed by associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getClaimedBy();

    /**
     * Returns the claimed at associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getClaimedAt();

    /**
     * Returns the limit use associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLimitUse();

    /**
     * Returns the status associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    VoucherStatus getStatus();

    /**
     * Returns the code associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getCode();
}
