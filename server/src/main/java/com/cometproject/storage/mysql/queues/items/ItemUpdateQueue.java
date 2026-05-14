package com.cometproject.storage.mysql.queues.items;

import com.cometproject.api.game.rooms.objects.IFloorItem;
import com.cometproject.storage.mysql.BlockingMySQLStorageQueue;
import com.cometproject.storage.mysql.MySQLConnectionProvider;

import java.sql.PreparedStatement;

/**
 * Describes item update queue behavior for the MySQL storage subsystem.
 */
public class ItemUpdateQueue extends BlockingMySQLStorageQueue<Long, IFloorItem> {

    /**
     * Creates a item update queue instance for the MySQL storage subsystem.
     *
     * @param connectionProvider Connection provider supplied by the caller.
     */
    public ItemUpdateQueue(MySQLConnectionProvider connectionProvider) {
        super(connectionProvider, "UPDATE items SET x = ?, y = ?, z = ?, rot = ?, extra_data = ? WHERE id = ?;", 25);
    }

    /**
     * Processes batch for this MySQL storage contract.
     *
     * @param preparedStatement Prepared statement supplied by the caller.
     * @param id Id supplied by the caller.
     * @param floor Floor supplied by the caller.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void processBatch(PreparedStatement preparedStatement, Long id, IFloorItem floor) throws Exception {
        preparedStatement.setInt(1, floor.getPosition().getX());
        preparedStatement.setInt(2, floor.getPosition().getY());
        preparedStatement.setDouble(3, floor.getPosition().getZ());
        preparedStatement.setInt(4, floor.getRotation());
        preparedStatement.setString(5, floor.getDataObject());
        preparedStatement.setLong(6, floor.getId());

        preparedStatement.addBatch();
    }
}
