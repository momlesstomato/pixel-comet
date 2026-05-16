package com.cometproject.server.storage.jooq.repositories;

import com.cometproject.server.storage.jooq.IJooqDslProvider;
import com.cometproject.server.storage.jooq.JooqRepository;
import com.cometproject.storage.api.data.permissions.PermissionAuditEntry;
import com.cometproject.storage.api.data.permissions.PermissionEffect;
import com.cometproject.storage.api.data.permissions.PermissionGrantRequest;
import com.cometproject.storage.api.data.permissions.PermissionGroup;
import com.cometproject.storage.api.data.permissions.PermissionGroupMutation;
import com.cometproject.storage.api.data.permissions.PermissionNode;
import com.cometproject.storage.api.data.permissions.PermissionNodeMutation;
import com.cometproject.storage.api.data.permissions.PlayerPermissionGroup;
import com.cometproject.storage.api.data.permissions.exceptions.PermissionNotFoundException;
import com.cometproject.storage.api.repositories.IPermissionRepository;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.DSL;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

/**
 * JOOQ-backed repository for permission groups, nodes, memberships, and audit records.
 */
@Singleton
public final class JooqPermissionRepository extends JooqRepository implements IPermissionRepository {
    private static final Gson GSON = new Gson();

    private static final Table<Record> GROUPS = DSL.table(DSL.name("permission_groups"));
    private static final Field<Long> GROUP_ID = DSL.field(DSL.name("permission_groups", "id"), Long.class);
    private static final Field<String> GROUP_CODE = DSL.field(DSL.name("permission_groups", "code"), String.class);
    private static final Field<String> GROUP_DISPLAY_NAME =
            DSL.field(DSL.name("permission_groups", "display_name"), String.class);
    private static final Field<Integer> GROUP_PRIORITY =
            DSL.field(DSL.name("permission_groups", "priority"), Integer.class);
    private static final Field<Integer> GROUP_LEGACY_RANK_ID =
            DSL.field(DSL.name("permission_groups", "legacy_rank_id"), Integer.class);
    private static final Field<String> GROUP_STAFF = DSL.field(DSL.name("permission_groups", "staff"), String.class);
    private static final Field<String> GROUP_DEFAULT =
            DSL.field(DSL.name("permission_groups", "default_group"), String.class);
    private static final Field<String> GROUP_ENABLED =
            DSL.field(DSL.name("permission_groups", "enabled"), String.class);

    private static final Table<Record> NODES = DSL.table(DSL.name("permission_group_nodes"));
    private static final Field<Long> NODE_ID = DSL.field(DSL.name("permission_group_nodes", "id"), Long.class);
    private static final Field<Long> NODE_GROUP_ID =
            DSL.field(DSL.name("permission_group_nodes", "group_id"), Long.class);
    private static final Field<String> NODE_VALUE =
            DSL.field(DSL.name("permission_group_nodes", "node"), String.class);
    private static final Field<String> NODE_EFFECT =
            DSL.field(DSL.name("permission_group_nodes", "effect"), String.class);
    private static final Field<String> NODE_CONTEXT =
            DSL.field(DSL.name("permission_group_nodes", "context"), String.class);
    private static final Field<Timestamp> NODE_EXPIRES_AT =
            DSL.field(DSL.name("permission_group_nodes", "expires_at"), Timestamp.class);

    private static final Table<Record> MEMBERSHIPS = DSL.table(DSL.name("player_permission_groups"));
    private static final Field<Long> MEMBERSHIP_ID =
            DSL.field(DSL.name("player_permission_groups", "id"), Long.class);
    private static final Field<Integer> MEMBERSHIP_PLAYER_ID =
            DSL.field(DSL.name("player_permission_groups", "player_id"), Integer.class);
    private static final Field<Long> MEMBERSHIP_GROUP_ID =
            DSL.field(DSL.name("player_permission_groups", "group_id"), Long.class);
    private static final Field<String> MEMBERSHIP_GRANTED_BY =
            DSL.field(DSL.name("player_permission_groups", "granted_by"), String.class);
    private static final Field<String> MEMBERSHIP_REASON =
            DSL.field(DSL.name("player_permission_groups", "reason"), String.class);
    private static final Field<Timestamp> MEMBERSHIP_EXPIRES_AT =
            DSL.field(DSL.name("player_permission_groups", "expires_at"), Timestamp.class);

    private static final Table<Record> AUDIT = DSL.table(DSL.name("permission_audit_log"));
    private static final Field<String> AUDIT_ACTION =
            DSL.field(DSL.name("permission_audit_log", "action"), String.class);
    private static final Field<String> AUDIT_ACTOR_TYPE =
            DSL.field(DSL.name("permission_audit_log", "actor_type"), String.class);
    private static final Field<String> AUDIT_ACTOR_ID =
            DSL.field(DSL.name("permission_audit_log", "actor_id"), String.class);
    private static final Field<String> AUDIT_TARGET_TYPE =
            DSL.field(DSL.name("permission_audit_log", "target_type"), String.class);
    private static final Field<String> AUDIT_TARGET_ID =
            DSL.field(DSL.name("permission_audit_log", "target_id"), String.class);
    private static final Field<String> AUDIT_METADATA =
            DSL.field(DSL.name("permission_audit_log", "metadata"), String.class);

    /**
     * Creates the permission repository.
     *
     * @param dslProvider the shared JOOQ DSL provider.
     */
    @Inject
    public JooqPermissionRepository(final IJooqDslProvider dslProvider) {
        super(dslProvider);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getGroups(final boolean includeDisabled, final Consumer<List<PermissionGroup>> consumer) {
        final Condition condition = includeDisabled ? DSL.trueCondition() : GROUP_ENABLED.eq("1");

        consumer.accept(this.dsl()
                .select(GROUP_ID, GROUP_CODE, GROUP_DISPLAY_NAME, GROUP_PRIORITY, GROUP_LEGACY_RANK_ID,
                        GROUP_STAFF, GROUP_DEFAULT, GROUP_ENABLED)
                .from(GROUPS)
                .where(condition)
                .orderBy(GROUP_PRIORITY.desc(), GROUP_CODE.asc())
                .fetch(this::mapGroup));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getGroup(final String groupCode, final Consumer<PermissionGroup> consumer) {
        consumer.accept(this.dsl()
                .select(GROUP_ID, GROUP_CODE, GROUP_DISPLAY_NAME, GROUP_PRIORITY, GROUP_LEGACY_RANK_ID,
                        GROUP_STAFF, GROUP_DEFAULT, GROUP_ENABLED)
                .from(GROUPS)
                .where(GROUP_CODE.eq(normalizeGroupCode(groupCode)))
                .limit(1)
                .fetchOne(this::mapGroup));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upsertGroup(final PermissionGroupMutation mutation, final Consumer<PermissionGroup> consumer) {
        final String groupCode = normalizeGroupCode(mutation.code());

        this.dsl()
                .insertInto(GROUPS)
                .columns(GROUP_CODE, GROUP_DISPLAY_NAME, GROUP_PRIORITY, GROUP_LEGACY_RANK_ID,
                        GROUP_STAFF, GROUP_DEFAULT, GROUP_ENABLED)
                .values(groupCode, mutation.displayName(), mutation.priority(), mutation.legacyRankId(),
                        bool(mutation.staff()), bool(mutation.defaultGroup()), bool(mutation.enabled()))
                .onDuplicateKeyUpdate()
                .set(GROUP_DISPLAY_NAME, mutation.displayName())
                .set(GROUP_PRIORITY, mutation.priority())
                .set(GROUP_LEGACY_RANK_ID, mutation.legacyRankId())
                .set(GROUP_STAFF, bool(mutation.staff()))
                .set(GROUP_DEFAULT, bool(mutation.defaultGroup()))
                .set(GROUP_ENABLED, bool(mutation.enabled()))
                .execute();

        this.getGroup(groupCode, consumer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disableGroup(final String groupCode) {
        this.dsl()
                .update(GROUPS)
                .set(GROUP_ENABLED, "0")
                .where(GROUP_CODE.eq(normalizeGroupCode(groupCode)))
                .execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getGroupNodes(final String groupCode, final Consumer<List<PermissionNode>> consumer) {
        consumer.accept(this.dsl()
                .select(NODE_ID, NODE_GROUP_ID, GROUP_CODE, NODE_VALUE, NODE_EFFECT, NODE_CONTEXT, NODE_EXPIRES_AT)
                .from(NODES)
                .join(GROUPS).on(GROUP_ID.eq(NODE_GROUP_ID))
                .where(GROUP_CODE.eq(normalizeGroupCode(groupCode)))
                .orderBy(NODE_VALUE.asc(), NODE_CONTEXT.asc())
                .fetch(this::mapNode));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getAllNodes(final Consumer<List<PermissionNode>> consumer) {
        consumer.accept(this.dsl()
                .select(NODE_ID, NODE_GROUP_ID, GROUP_CODE, NODE_VALUE, NODE_EFFECT, NODE_CONTEXT, NODE_EXPIRES_AT)
                .from(NODES)
                .join(GROUPS).on(GROUP_ID.eq(NODE_GROUP_ID))
                .where(GROUP_ENABLED.eq("1"))
                .orderBy(GROUP_PRIORITY.desc(), GROUP_CODE.asc(), NODE_VALUE.asc())
                .fetch(this::mapNode));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upsertGroupNode(
            final String groupCode,
            final PermissionNodeMutation mutation,
            final Consumer<PermissionNode> consumer) {
        final PermissionGroup group = this.requireGroup(groupCode);
        final String node = normalizeNode(mutation.node());
        final String context = normalizeContext(mutation.context());

        this.dsl()
                .insertInto(NODES)
                .columns(NODE_GROUP_ID, NODE_VALUE, NODE_EFFECT, NODE_CONTEXT, NODE_EXPIRES_AT)
                .values(group.id(), node, mutation.effect().name(), context, timestamp(mutation.expiresAt()))
                .onDuplicateKeyUpdate()
                .set(NODE_EFFECT, mutation.effect().name())
                .set(NODE_EXPIRES_AT, timestamp(mutation.expiresAt()))
                .execute();

        consumer.accept(this.dsl()
                .select(NODE_ID, NODE_GROUP_ID, GROUP_CODE, NODE_VALUE, NODE_EFFECT, NODE_CONTEXT, NODE_EXPIRES_AT)
                .from(NODES)
                .join(GROUPS).on(GROUP_ID.eq(NODE_GROUP_ID))
                .where(NODE_GROUP_ID.eq(group.id()))
                .and(NODE_VALUE.eq(node))
                .and(NODE_CONTEXT.eq(context))
                .limit(1)
                .fetchOne(this::mapNode));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteGroupNode(final String groupCode, final String node, final String context) {
        final PermissionGroup group = this.requireGroup(groupCode);

        this.dsl()
                .deleteFrom(NODES)
                .where(NODE_GROUP_ID.eq(group.id()))
                .and(NODE_VALUE.eq(normalizeNode(node)))
                .and(NODE_CONTEXT.eq(normalizeContext(context)))
                .execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getPlayerGroups(
            final int playerId,
            final boolean includeExpired,
            final Consumer<List<PlayerPermissionGroup>> consumer) {
        Condition condition = MEMBERSHIP_PLAYER_ID.eq(playerId).and(GROUP_ENABLED.eq("1"));
        if (!includeExpired) {
            condition = condition.and(MEMBERSHIP_EXPIRES_AT.isNull()
                    .or(MEMBERSHIP_EXPIRES_AT.greaterThan(Timestamp.from(Instant.now()))));
        }

        consumer.accept(this.dsl()
                .select(MEMBERSHIP_ID, MEMBERSHIP_PLAYER_ID, MEMBERSHIP_GRANTED_BY, MEMBERSHIP_REASON,
                        MEMBERSHIP_EXPIRES_AT, GROUP_ID, GROUP_CODE, GROUP_DISPLAY_NAME, GROUP_PRIORITY,
                        GROUP_LEGACY_RANK_ID, GROUP_STAFF, GROUP_DEFAULT, GROUP_ENABLED)
                .from(MEMBERSHIPS)
                .join(GROUPS).on(GROUP_ID.eq(MEMBERSHIP_GROUP_ID))
                .where(condition)
                .orderBy(GROUP_PRIORITY.desc(), GROUP_CODE.asc())
                .fetch(this::mapMembership));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void grantGroup(final int playerId, final String groupCode, final PermissionGrantRequest request) {
        final PermissionGroup group = this.requireGroup(groupCode);

        this.dsl()
                .insertInto(MEMBERSHIPS)
                .columns(MEMBERSHIP_PLAYER_ID, MEMBERSHIP_GROUP_ID, MEMBERSHIP_GRANTED_BY,
                        MEMBERSHIP_REASON, MEMBERSHIP_EXPIRES_AT)
                .values(playerId, group.id(), blankToDefault(request.grantedBy(), "system"),
                        blankToDefault(request.reason(), ""), timestamp(request.expiresAt()))
                .onDuplicateKeyUpdate()
                .set(MEMBERSHIP_GRANTED_BY, blankToDefault(request.grantedBy(), "system"))
                .set(MEMBERSHIP_REASON, blankToDefault(request.reason(), ""))
                .set(MEMBERSHIP_EXPIRES_AT, timestamp(request.expiresAt()))
                .execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void revokeGroup(final int playerId, final String groupCode) {
        final PermissionGroup group = this.requireGroup(groupCode);

        this.dsl()
                .deleteFrom(MEMBERSHIPS)
                .where(MEMBERSHIP_PLAYER_ID.eq(playerId))
                .and(MEMBERSHIP_GROUP_ID.eq(group.id()))
                .execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getPlayersByGroup(final String groupCode, final Consumer<List<Integer>> consumer) {
        final PermissionGroup group = this.requireGroup(groupCode);

        consumer.accept(this.dsl()
                .select(MEMBERSHIP_PLAYER_ID)
                .from(MEMBERSHIPS)
                .where(MEMBERSHIP_GROUP_ID.eq(group.id()))
                .fetch(MEMBERSHIP_PLAYER_ID));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAudit(final PermissionAuditEntry entry) {
        this.dsl()
                .insertInto(AUDIT)
                .columns(AUDIT_ACTION, AUDIT_ACTOR_TYPE, AUDIT_ACTOR_ID, AUDIT_TARGET_TYPE,
                        AUDIT_TARGET_ID, AUDIT_METADATA)
                .values(entry.action(), entry.actorType(), entry.actorId(), entry.targetType(),
                        entry.targetId(), GSON.toJson(entry.metadata()))
                .execute();
    }

    private PermissionGroup requireGroup(final String groupCode) {
        final PermissionGroup group = this.dsl()
                .select(GROUP_ID, GROUP_CODE, GROUP_DISPLAY_NAME, GROUP_PRIORITY, GROUP_LEGACY_RANK_ID,
                        GROUP_STAFF, GROUP_DEFAULT, GROUP_ENABLED)
                .from(GROUPS)
                .where(GROUP_CODE.eq(normalizeGroupCode(groupCode)))
                .limit(1)
                .fetchOne(this::mapGroup);

        if (group == null) {
            throw new PermissionNotFoundException("Permission group does not exist: " + groupCode);
        }

        return group;
    }

    private PermissionGroup mapGroup(final Record record) {
        return new PermissionGroup(
                record.get(GROUP_ID),
                record.get(GROUP_CODE),
                record.get(GROUP_DISPLAY_NAME),
                record.get(GROUP_PRIORITY),
                record.get(GROUP_LEGACY_RANK_ID),
                truthy(record.get(GROUP_STAFF)),
                truthy(record.get(GROUP_DEFAULT)),
                truthy(record.get(GROUP_ENABLED)));
    }

    private PermissionNode mapNode(final Record record) {
        return new PermissionNode(
                record.get(NODE_ID),
                record.get(NODE_GROUP_ID),
                record.get(GROUP_CODE),
                record.get(NODE_VALUE),
                PermissionEffect.valueOf(record.get(NODE_EFFECT)),
                record.get(NODE_CONTEXT),
                instant(record.get(NODE_EXPIRES_AT)));
    }

    private PlayerPermissionGroup mapMembership(final Record record) {
        return new PlayerPermissionGroup(
                record.get(MEMBERSHIP_ID),
                record.get(MEMBERSHIP_PLAYER_ID),
                this.mapGroup(record),
                record.get(MEMBERSHIP_GRANTED_BY),
                record.get(MEMBERSHIP_REASON),
                instant(record.get(MEMBERSHIP_EXPIRES_AT)));
    }

    private static String normalizeGroupCode(final String value) {
        return blankToDefault(value, "").trim().toLowerCase(Locale.ROOT);
    }

    private static String normalizeNode(final String value) {
        return blankToDefault(value, "").trim().toLowerCase(Locale.ROOT);
    }

    private static String normalizeContext(final String value) {
        return blankToDefault(value, "global").trim().toLowerCase(Locale.ROOT);
    }

    private static String blankToDefault(final String value, final String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }

    private static String bool(final boolean value) {
        return value ? "1" : "0";
    }

    private static boolean truthy(final String value) {
        return "1".equals(value) || "true".equalsIgnoreCase(value);
    }

    private static Timestamp timestamp(final Instant value) {
        return value == null ? null : Timestamp.from(value);
    }

    private static Instant instant(final Timestamp value) {
        return value == null ? null : value.toInstant();
    }
}
