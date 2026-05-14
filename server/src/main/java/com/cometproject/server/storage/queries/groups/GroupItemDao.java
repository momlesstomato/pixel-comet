package com.cometproject.server.storage.queries.groups;

import com.cometproject.api.game.groups.items.IGroupBadgeItem;
import com.cometproject.server.game.groups.items.types.*;
import com.cometproject.server.storage.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


/**
 * Describes group item dao behavior for the storage subsystem.
 */
public class GroupItemDao {
    /**
     * Loads group items for this storage contract.
     *
     * @param bases Bases supplied by the caller.
     * @param symbols Symbols supplied by the caller.
     * @param baseColours Base colours supplied by the caller.
     * @param symbolColours Symbol colours supplied by the caller.
     * @param backgroundColours Background colours supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static int loadGroupItems(List<IGroupBadgeItem> bases, List<IGroupBadgeItem> symbols, List<IGroupBadgeItem> baseColours,
                                     Map<Integer, IGroupBadgeItem> symbolColours, Map<Integer, IGroupBadgeItem> backgroundColours) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int count = 0;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT * FROM group_items", sqlConnection);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                count++;

                switch (resultSet.getString("type")) {
                    case "base":
                        bases.add(new GroupBase(resultSet));
                        break;

                    case "symbol":
                        symbols.add(new GroupSymbol(resultSet));
                        break;

                    case "color":
                        baseColours.add(new GroupBaseColour(resultSet));
                        break;

                    case "color2":
                        symbolColours.put(resultSet.getInt("id"), new GroupSymbolColour(resultSet));
                        break;

                    case "color3":
                        backgroundColours.put(resultSet.getInt("id"), new GroupBackgroundColour(resultSet));
                        break;
                }
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return count;
    }
}
