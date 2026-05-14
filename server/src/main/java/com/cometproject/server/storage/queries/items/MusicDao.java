package com.cometproject.server.storage.queries.items;

import com.cometproject.api.game.furniture.types.IMusicData;
import com.cometproject.server.game.items.music.MusicData;
import com.cometproject.server.storage.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Describes music dao behavior for the storage subsystem.
 */
public class MusicDao {
    /**
     * Returns the music data for this storage contract.
     *
     * @param musicData Music data supplied by the caller.
     */
    public static void getMusicData(Map<Integer, IMusicData> musicData) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT * FROM furniture_music", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                musicData.put(resultSet.getInt("id"), new MusicData(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("title"), resultSet.getString("artist"), resultSet.getString("song_data"),
                        resultSet.getInt("length")));
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }
}
