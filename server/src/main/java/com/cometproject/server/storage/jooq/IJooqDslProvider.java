package com.cometproject.server.storage.jooq;

import org.jooq.DSLContext;

/**
 * Supplies the configured JOOQ DSL context for repository implementations.
 *
 * <p>Providers are expected to be lightweight and thread-safe.
 */
public interface IJooqDslProvider {
    /**
     * Returns the DSL context used to execute database queries.
     *
     * @return the configured JOOQ DSL context.
     */
    DSLContext dsl();
}
