package com.cometproject.server.storage.jooq;

import com.cometproject.storage.mysql.connections.HikariConnectionProvider;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

/**
 * Creates a MySQL-flavoured JOOQ DSL context from the shared Hikari data source.
 */
@Singleton
public final class HikariJooqDslProvider implements IJooqDslProvider {
    private final DSLContext dslContext;

    /**
     * Creates the provider from the server's pooled database connection source.
     *
     * @param connectionProvider the shared Hikari connection provider.
     */
    @Inject
    public HikariJooqDslProvider(final HikariConnectionProvider connectionProvider) {
        this.dslContext = DSL.using(connectionProvider.getDataSource(), SQLDialect.MYSQL);
    }

    /**
     * Returns the shared JOOQ DSL context.
     *
     * @return the configured DSL context.
     */
    @Override
    public DSLContext dsl() {
        return this.dslContext;
    }
}
