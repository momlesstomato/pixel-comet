package com.cometproject.storage.mysql.connections;

import com.cometproject.api.config.Configuration;
import com.cometproject.storage.mysql.MySQLConnectionProvider;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Describes hikari connection provider behavior for the MySQL storage subsystem.
 */
public class HikariConnectionProvider extends MySQLConnectionProvider {

    private final Logger LOGGER = LoggerFactory.getLogger(HikariConnectionProvider.class);

    private HikariDataSource hikariDataSource;
    private boolean isConnectionFailed = true;

    /**
     * Creates a hikari connection provider instance for the MySQL storage subsystem.
     */
    public HikariConnectionProvider() {
        try {
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(
                    "jdbc:mysql://" + Configuration.currentConfig().get("comet.db.host") +
                            "/" + Configuration.currentConfig().get("comet.db.name") + "?tcpKeepAlive=" + Configuration.currentConfig().get("comet.db.pool.tcpKeepAlive") +
                            "&autoReconnect=" + Configuration.currentConfig().get("comet.db.pool.autoReconnect")
            );

            config.setUsername(Configuration.currentConfig().get("comet.db.username"));
            config.setPassword(Configuration.currentConfig().get("comet.db.password"));

            config.setMaximumPoolSize(Integer.parseInt(Configuration.currentConfig().get("comet.db.pool.max")));

//            config.setMaxConnectionsPerPartition(Integer.parseInt(Configuration.currentConfig().get("comet.db.pool.max")));
//            config.setPartitionCount(Integer.parseInt(Configuration.currentConfig().get("comet.db.pool.count")));
//
//            config.setIdleMaxAge(Integer.valueOf(Configuration.currentConfig().get("comet.db.pool.idleMaxAgeSeconds")), TimeUnit.SECONDS);
//            config.setMaxConnectionAge(Integer.valueOf(Configuration.currentConfig().get("comet.db.pool.maxConnectionAgeSeconds")), TimeUnit.SECONDS);
//
//            config.setAcquireRetryAttempts(Integer.valueOf(Configuration.currentConfig().get("comet.db.pool.acquireRetryAttempts")));

            LOGGER.info("Connecting to the MySQL server");

            this.isConnectionFailed = false;
            this.hikariDataSource = new HikariDataSource(config);
        } catch (Exception e) {
            isConnectionFailed = true;
            LOGGER.error("Failed to connect to MySQL server", e);
            System.exit(0);
        } finally {
            if (!isConnectionFailed) {
                LOGGER.info("Connection to MySQL server was successful");
            }
        }

    }

    /**
     * Returns the connection for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public Connection getConnection() throws Exception {
        return this.hikariDataSource.getConnection();
    }

    /**
     * Executes close connection for this MySQL storage contract.
     *
     * @param connection Connection supplied by the caller.
     */
    @Override
    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch(Exception e) {
            LOGGER.error("Failed to close Hikari connection", e);
        }
    }

    /**
     * Executes close statement for this MySQL storage contract.
     *
     * @param statement Statement supplied by the caller.
     */
    @Override
    public void closeStatement(PreparedStatement statement) {
        try {
            statement.close();
        } catch (Exception e) {
            LOGGER.error("Failed to close prepared statement", e);
        }
    }

    /**
     * Executes close results for this MySQL storage contract.
     *
     * @param resultSet Result set supplied by the caller.
     */
    @Override
    public void closeResults(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (Exception e) {
            LOGGER.error("Failed to close the result set");
        }
    }

    /**
     * Executes shutdown for this MySQL storage contract.
     */
    public void shutdown() {
        this.hikariDataSource.close();
    }

    /**
     * Returns the pooled data source backing this connection provider.
     *
     * @return the active Hikari data source.
     */
    public DataSource getDataSource() {
        return this.hikariDataSource;
    }
}
