package com.cometproject.server.storage.jooq.repositories;

import com.cometproject.storage.api.repositories.IPlayerRepository;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Verifies the JOOQ player repository against an in-memory MySQL-compatible schema.
 */
final class JooqPlayerRepositoryTest extends IPlayerRepositoryContractTest {
    private DSLContext dsl;
    private IPlayerRepository repository;

    @BeforeEach
    void setUp() throws SQLException {
        final Connection connection = DriverManager.getConnection(
                "jdbc:h2:mem:player_repository;MODE=MySQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1");
        this.dsl = DSL.using(connection, SQLDialect.H2);
        this.repository = new JooqPlayerRepository(() -> this.dsl);
        this.createSchema();
        this.dsl.deleteFrom(DSL.table("players")).execute();
    }

    @Override
    protected IPlayerRepository repository() {
        return this.repository;
    }

    @Override
    protected DSLContext dsl() {
        return this.dsl;
    }

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
