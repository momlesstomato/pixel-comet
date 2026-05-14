package com.cometproject.server.storage.queries.catalog;

import com.cometproject.api.game.catalog.types.vouchers.VoucherStatus;
import com.cometproject.api.game.catalog.types.vouchers.VoucherType;
import com.cometproject.server.game.catalog.types.Voucher;
import com.cometproject.server.storage.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Describes voucher dao behavior for the storage subsystem.
 */
public class VoucherDao {
    /**
     * Finds voucher by code for this storage contract.
     *
     * @param code Code supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static Voucher findVoucherByCode(final String code) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Voucher voucher = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT * FROM `vouchers` WHERE `code` = ?", sqlConnection);

            preparedStatement.setString(1, code);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final String rawType = resultSet.getString("type");
                voucher = new Voucher(resultSet.getInt("id"), voucherType(rawType),
                        resultSet.getString("data"), currencyCode(rawType), resultSet.getInt("created_by"), resultSet.getInt("created_at"),
                        resultSet.getString("claimed_by"), resultSet.getInt("claimed_at"), resultSet.getInt("limit_use"),
                        VoucherStatus.valueOf(resultSet.getString("status")), resultSet.getString("code"));
            }

        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return voucher;
    }

    /**
     * Executes claim voucher for this storage contract.
     *
     * @param voucherId Voucher id supplied by the caller.
     * @param playerId Player identifier used by the operation.
     * @param claimed Claimed supplied by the caller.
     */
    public static void claimVoucher(final int voucherId, final int playerId, final boolean claimed) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE `vouchers` SET `status` = ?, `claimed_by` = concat(?, claimed_by), `limit_use` = limit_use - 1, `claimed_at` = UNIX_TIMESTAMP() WHERE id = ?;", sqlConnection);

            preparedStatement.setString(1, claimed ? "CLAIMED" : "UNCLAIMED");
            preparedStatement.setString(2, playerId + ",");
            preparedStatement.setInt(3, voucherId);

            preparedStatement.execute();

        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Adds voucher to this storage contract.
     *
     * @param type Type supplied by the caller.
     * @param prize Prize supplied by the caller.
     * @param playerId Player identifier used by the operation.
     * @param limit Limit supplied by the caller.
     * @param code Code supplied by the caller.
     */
    public static void addVoucher(String type, String prize, int playerId, int limit, String code) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = sqlConnection.prepareStatement(
                    "INSERT INTO vouchers (type, data, created_by, limit_use, code) VALUES(?, ?, ?, ?, ?);");

            preparedStatement.setString(1, type);
            preparedStatement.setString(2, prize);
            preparedStatement.setInt(3, playerId);
            preparedStatement.setInt(4, limit);
            preparedStatement.setString(5, code);

            preparedStatement.execute();
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    private static VoucherType voucherType(final String rawType) {
        try {
            return VoucherType.valueOf(rawType);
        } catch (IllegalArgumentException exception) {
            return VoucherType.CURRENCY;
        }
    }

    private static String currencyCode(final String rawType) {
        return voucherType(rawType) == VoucherType.CURRENCY ? rawType : "";
    }
}
