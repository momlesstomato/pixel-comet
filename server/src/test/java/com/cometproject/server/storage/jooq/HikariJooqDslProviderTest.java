package com.cometproject.server.storage.jooq;

import com.cometproject.storage.mysql.connections.HikariConnectionProvider;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.SQLDialect;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Verifies the production JOOQ provider renders SQL compatible with the configured JDBC stack.
 */
final class HikariJooqDslProviderTest {
    @Test
    void dslUsesMySqlDialectForLimitSyntaxCompatibility() {
        final JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:jooq_provider;MODE=MySQL;DATABASE_TO_LOWER=TRUE");

        final HikariConnectionProvider connectionProvider = mock(HikariConnectionProvider.class);
        when(connectionProvider.getDataSource()).thenReturn(dataSource);

        final HikariJooqDslProvider provider = new HikariJooqDslProvider(connectionProvider);

        assertEquals(SQLDialect.MYSQL, provider.dsl().dialect());
    }
}
