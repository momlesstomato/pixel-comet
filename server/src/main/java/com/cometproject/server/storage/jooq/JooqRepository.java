package com.cometproject.server.storage.jooq;

import org.jooq.DSLContext;

/**
 * Base class for repositories backed by JOOQ.
 */
public abstract class JooqRepository {
    private final IJooqDslProvider dslProvider;

    /**
     * Creates the repository with the DSL provider used for all database access.
     *
     * @param dslProvider the JOOQ DSL provider.
     */
    protected JooqRepository(final IJooqDslProvider dslProvider) {
        this.dslProvider = dslProvider;
    }

    /**
     * Returns the configured DSL context.
     *
     * @return the JOOQ DSL context.
     */
    protected final DSLContext dsl() {
        return this.dslProvider.dsl();
    }
}
