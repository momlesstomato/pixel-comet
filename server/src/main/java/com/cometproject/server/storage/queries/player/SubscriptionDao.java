package com.cometproject.server.storage.queries.player;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.storage.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Describes subscription dao behavior for the storage subsystem.
 */
public class SubscriptionDao {
    /**
     * Returns the expire time for this storage contract.
     *
     * @param userId User id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static int getExpireTime(int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT expire FROM player_subscriptions WHERE user_id = ? ORDER BY expire DESC LIMIT 1", sqlConnection);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return 0;
    }

    /**
     * Updates claimable offers for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param offerId Offer id supplied by the caller.
     */
    public static void updateClaimableOffers(int playerId, int offerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE cms_offers_claim SET claimed = '1' WHERE player_id = ? AND offer_id = ? AND claimed='0' ORDER BY id ASC LIMIT 1", sqlConnection);
            preparedStatement.setInt(1, playerId);
            preparedStatement.setInt(2, offerId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Returns the claimable offer for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public static int getClaimableOffer(int playerId){
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT offer_id FROM cms_offers_claim WHERE player_id = ? AND claimed = '0' LIMIT 1", sqlConnection);
            preparedStatement.setInt(1, playerId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return 0;
    }

    /**
     * Returns the start time for this storage contract.
     *
     * @param userId User id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static int getStartTime(int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT start FROM player_subscriptions WHERE user_id = ? ORDER BY start DESC LIMIT 1", sqlConnection);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return 0;
    }

    /**
     * Returns the presents for this storage contract.
     *
     * @param userId User id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static int getPresents(int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT presents FROM player_subscriptions WHERE user_id = ? ORDER BY expire DESC LIMIT 1", sqlConnection);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return 0;
    }

    /**
     * Returns the allowed items for this storage contract.
     *
     * @param userId User id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static int getAllowedItems(int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT allowed_items FROM player_subscriptions WHERE user_id = ?", sqlConnection);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return 50;
    }

    /**
     * Returns the borrowed items for this storage contract.
     *
     * @param userId User id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static int getBorrowedItems(int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT borrowed_items FROM player_subscriptions WHERE user_id = ?", sqlConnection);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return 0;
    }


    /**
     * Executes decrement presents for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param presents Presents supplied by the caller.
     */
    public static void decrementPresents(int playerId, int presents) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_subscriptions SET presents = ? WHERE user_id = ?", sqlConnection);
            preparedStatement.setInt(1, presents);
            preparedStatement.setInt(2, playerId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Updates allowed items for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param items Items supplied by the caller.
     */
    public static void updateAllowedItems(int playerId, int items) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_subscriptions SET allowed_items = ? WHERE user_id = ?", sqlConnection);
            preparedStatement.setInt(1, items);
            preparedStatement.setInt(2, playerId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Updates borrowed items for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param items Items supplied by the caller.
     */
    public static void updateBorrowedItems(int playerId, int items) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_subscriptions SET borrowed_items = ? WHERE user_id = ?", sqlConnection);
            preparedStatement.setInt(1, items);
            preparedStatement.setInt(2, playerId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Executes renew subscription for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param expire Expire supplied by the caller.
     */
    public static void renewSubscription(int playerId, int expire) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_subscriptions SET expire = ? WHERE user_id = ?", sqlConnection);
            preparedStatement.setInt(1, expire);
            preparedStatement.setInt(2, playerId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Adds subscription to this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param expire Expire supplied by the caller.
     */
    public static void addSubscription(int playerId, int expire) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("INSERT INTO player_subscriptions (`user_id`, `expire`, `start`) VALUES(?, ?, ?);", sqlConnection);
            preparedStatement.setInt(1, playerId);
            preparedStatement.setInt(2, expire);
            preparedStatement.setInt(3, (int)Comet.getTime());

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Adds premium item to this storage contract.
     *
     * @param itemId Item id supplied by the caller.
     * @param roomId Room identifier used by the operation.
     */
    public static void addPremiumItem(long itemId, int roomId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("INSERT INTO items_premium (`item_id`, `room_id`) VALUES(?, ?);", sqlConnection);
            preparedStatement.setLong(1, itemId);
            preparedStatement.setInt(2, roomId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Removes premium item from this storage contract.
     *
     * @param itemId Item id supplied by the caller.
     */
    public static void removePremiumItem(long itemId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("DELETE FROM items_premium WHERE item_id = ?;", sqlConnection);
            preparedStatement.setLong(1, itemId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }
}