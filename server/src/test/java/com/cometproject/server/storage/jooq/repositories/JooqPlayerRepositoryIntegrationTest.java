package com.cometproject.server.storage.jooq.repositories;

import com.cometproject.storage.api.repositories.IPlayerRepository;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Verifies the player repository against MariaDB when the integration profile is enabled.
 */
@Tag("integration")
@Testcontainers
final class JooqPlayerRepositoryIntegrationTest extends IPlayerRepositoryContractTest {
    @Container
    private static final MariaDBContainer<?> MARIADB = new MariaDBContainer<>("mariadb:10.11");

    private DSLContext dsl;
    private IPlayerRepository repository;

    @BeforeEach
    void setUp() throws SQLException {
        this.dsl = DSL.using(DriverManager.getConnection(
                MARIADB.getJdbcUrl(),
                MARIADB.getUsername(),
                MARIADB.getPassword()), SQLDialect.MARIADB);
        this.repository = new JooqPlayerRepository(() -> this.dsl);
        this.createSchema();
        this.dsl.deleteFrom(DSL.table("players")).execute();
    }

    /**
     * Executes repository for this jOOQ storage contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    protected IPlayerRepository repository() {
        return this.repository;
    }

    /**
     * Executes dsl for this jOOQ storage contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    protected DSLContext dsl() {
        return this.dsl;
    }

    /**
     * Executes insert test player for this jOOQ storage contract.
     *
     * @param id Id supplied by the caller.
     * @param username Username supplied by the caller.
     * @param authTicket Auth ticket supplied by the caller.
     */
    @Override
    protected void insertTestPlayer(final int id, final String username, final String authTicket) {
        this.dsl.insertInto(DSL.table("players"))
                .columns(
                        DSL.field("id"),
                        DSL.field("username"),
                        DSL.field("figure"),
                        DSL.field("motto"),
                        DSL.field("credits"),
                        DSL.field("vip_points"),
                        DSL.field("activity_points"),
                        DSL.field("rank"),
                        DSL.field("gender"),
                        DSL.field("reg_timestamp"),
                        DSL.field("reg_date"),
                        DSL.field("last_online"),
                        DSL.field("online"),
                        DSL.field("email"),
                        DSL.field("last_ip"),
                        DSL.field("vip"),
                        DSL.field("achievement_points"),
                        DSL.field("favourite_group"),
                        DSL.field("quest_id"),
                        DSL.field("time_muted"),
                        DSL.field("name_colour"),
                        DSL.field("seasonal_points"),
                        DSL.field("black_money"),
                        DSL.field("tag"),
                        DSL.field("job"),
                        DSL.field("view_points"))
                .values(
                        id,
                        username,
                        "hr-100-61",
                        "hello",
                        500,
                        0,
                        0,
                        1,
                        "M",
                        0,
                        "10/06/2013",
                        0,
                        "0",
                        "",
                        "",
                        "0",
                        0,
                        0,
                        0,
                        0,
                        "000000",
                        0,
                        0,
                        "",
                        "",
                        0)
                .execute();
    }

    private void createSchema() {
        this.dsl.execute("""
                CREATE TABLE IF NOT EXISTS players (
                    id INT PRIMARY KEY,
                    username VARCHAR(75),
                    figure VARCHAR(255),
                    motto VARCHAR(100),
                    credits INT,
                    vip_points INT,
                    activity_points INT,
                    rank INT,
                    gender VARCHAR(1),
                    reg_timestamp INT,
                    reg_date VARCHAR(12),
                    last_online INT,
                    online VARCHAR(1),
                    email VARCHAR(72),
                    last_ip VARCHAR(120),
                    vip VARCHAR(1),
                    achievement_points INT,
                    favourite_group INT,
                    quest_id INT,
                    time_muted INT,
                    name_colour VARCHAR(50),
                    seasonal_points INT,
                    black_money INT,
                    tag VARCHAR(250),
                    job VARCHAR(250),
                    view_points INT
                )
                """);
    }
}
