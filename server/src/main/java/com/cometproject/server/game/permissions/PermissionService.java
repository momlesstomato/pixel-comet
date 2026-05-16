package com.cometproject.server.game.permissions;

import com.cometproject.api.events.Event;
import com.cometproject.api.events.EventHandler;
import com.cometproject.api.events.permissions.CancellablePermissionConfigurationEvent;
import com.cometproject.api.events.permissions.PermissionCheckEvent;
import com.cometproject.api.events.permissions.PermissionConfigurationEvent;
import com.cometproject.api.events.permissions.PermissionsReloadedEvent;
import com.cometproject.storage.api.data.permissions.PermissionAuditEntry;
import com.cometproject.storage.api.data.permissions.PermissionCheckResult;
import com.cometproject.storage.api.data.permissions.PermissionContext;
import com.cometproject.storage.api.data.permissions.PermissionEffect;
import com.cometproject.storage.api.data.permissions.PermissionGrantRequest;
import com.cometproject.storage.api.data.permissions.PermissionGroup;
import com.cometproject.storage.api.data.permissions.PermissionGroupMutation;
import com.cometproject.storage.api.data.permissions.PermissionNode;
import com.cometproject.storage.api.data.permissions.PermissionNodeMutation;
import com.cometproject.storage.api.data.permissions.PermissionSnapshot;
import com.cometproject.storage.api.data.permissions.PlayerPermissionGroup;
import com.cometproject.storage.api.repositories.IPermissionRepository;
import com.cometproject.storage.api.services.IPermissionService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Cached permission service backed by JOOQ persistence.
 */
@Singleton
public final class PermissionService implements IPermissionService {
    private final IPermissionRepository repository;
    private final EventHandler eventHandler;
    private final Map<String, PermissionGroup> groupsByCode;
    private final Map<String, List<PermissionNode>> nodesByGroupCode;
    private final Map<Integer, PermissionSnapshot> snapshotsByPlayerId;

    /**
     * Creates the permission service.
     *
     * @param repository the permission repository.
     * @param eventHandler the plugin event handler.
     */
    @Inject
    public PermissionService(final IPermissionRepository repository, final EventHandler eventHandler) {
        this.repository = repository;
        this.eventHandler = eventHandler;
        this.groupsByCode = new ConcurrentHashMap<>();
        this.nodesByGroupCode = new ConcurrentHashMap<>();
        this.snapshotsByPlayerId = new ConcurrentHashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(final int playerId, final String node) {
        return this.hasPermission(playerId, node, PermissionContext.global());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(final int playerId, final String node, final PermissionContext context) {
        return this.check(playerId, node, context).allowed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasAnyPermission(final int playerId, final Collection<String> nodes) {
        for (String node : nodes) {
            if (this.hasPermission(playerId, node)) {
                return true;
            }
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPermissionNodeDefined(final String node) {
        final String normalizedNode = normalizeNode(node);

        if (this.groupsByCode.isEmpty()) {
            this.reload();
        }

        return this.nodesByGroupCode.values().stream()
                .flatMap(Collection::stream)
                .filter(this::activeNode)
                .anyMatch(permissionNode -> permissionNode.node().equals(normalizedNode));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PermissionCheckResult check(final int playerId, final String node, final PermissionContext context) {
        final String normalizedNode = normalizeNode(node);
        PermissionCheckResult result = this.resolve(this.snapshot(playerId), normalizedNode);
        final PermissionCheckEvent event = new PermissionCheckEvent(
                playerId,
                normalizedNode,
                context == null ? PermissionContext.global() : context,
                result,
                true);
        this.publish(event);
        result = event.getResult();
        return result == null ? PermissionCheckResult.denied(normalizedNode) : result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PermissionSnapshot snapshot(final int playerId) {
        return this.snapshotsByPlayerId.computeIfAbsent(playerId, this::buildSnapshot);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int legacyRankId(final int playerId, final int fallbackRankId) {
        final PermissionSnapshot snapshot = this.snapshot(playerId);
        return snapshot.legacyRankId() <= 0 ? fallbackRankId : snapshot.legacyRankId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int highestPriority(final int playerId, final int fallbackPriority) {
        final PermissionSnapshot snapshot = this.snapshot(playerId);
        return snapshot.highestPriority() <= 0 ? fallbackPriority : snapshot.highestPriority();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PermissionGroup> groups(final boolean includeDisabled) {
        if (this.groupsByCode.isEmpty()) {
            this.reload();
        }

        return this.groupsByCode.values().stream()
                .filter(group -> includeDisabled || group.enabled())
                .sorted(groupComparator())
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PermissionGroup upsertGroup(final PermissionGroupMutation mutation) {
        final String groupCode = normalizeGroupCode(mutation.code());
        final CancellablePermissionConfigurationEvent event = new CancellablePermissionConfigurationEvent(
                "permission_group_upsert_requested",
                "group",
                groupCode,
                Map.of("group_code", groupCode));
        this.ensureNotCancelled(event);

        final AtomicReference<PermissionGroup> group = new AtomicReference<>();
        this.repository.upsertGroup(mutation, group::set);
        this.repository.writeAudit(new PermissionAuditEntry(
                "permission_group_upserted",
                "system",
                "system",
                "group",
                groupCode,
                Map.of()));
        this.reload();
        this.publish(new PermissionConfigurationEvent(
                "permission_group_upserted",
                "group",
                groupCode,
                Map.of("group_code", groupCode)));
        return group.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disableGroup(final String groupCode) {
        final String normalizedCode = normalizeGroupCode(groupCode);
        final CancellablePermissionConfigurationEvent event = new CancellablePermissionConfigurationEvent(
                "permission_group_disable_requested",
                "group",
                normalizedCode,
                Map.of("group_code", normalizedCode));
        this.ensureNotCancelled(event);

        this.repository.disableGroup(normalizedCode);
        this.repository.writeAudit(new PermissionAuditEntry(
                "permission_group_disabled",
                "system",
                "system",
                "group",
                normalizedCode,
                Map.of()));
        this.invalidateGroup(normalizedCode);
        this.groupsByCode.remove(normalizedCode);
        this.publish(new PermissionConfigurationEvent(
                "permission_group_disabled",
                "group",
                normalizedCode,
                Map.of("group_code", normalizedCode)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PermissionNode> groupNodes(final String groupCode) {
        final String normalizedCode = normalizeGroupCode(groupCode);
        if (!this.nodesByGroupCode.containsKey(normalizedCode)) {
            final AtomicReference<List<PermissionNode>> nodes = new AtomicReference<>(List.of());
            this.repository.getGroupNodes(normalizedCode, nodes::set);
            this.nodesByGroupCode.put(normalizedCode, List.copyOf(nodes.get()));
        }

        return this.nodesByGroupCode.getOrDefault(normalizedCode, List.of());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PermissionNode upsertGroupNode(final String groupCode, final PermissionNodeMutation mutation) {
        final String normalizedCode = normalizeGroupCode(groupCode);
        final String normalizedNode = normalizeNode(mutation.node());
        final CancellablePermissionConfigurationEvent event = new CancellablePermissionConfigurationEvent(
                "permission_group_node_upsert_requested",
                "group_node",
                normalizedCode + ":" + normalizedNode,
                Map.of("group_code", normalizedCode, "permission_node", normalizedNode));
        this.ensureNotCancelled(event);

        final PermissionNodeMutation normalizedMutation = new PermissionNodeMutation(
                normalizedNode,
                mutation.effect() == null ? PermissionEffect.ALLOW : mutation.effect(),
                normalizeContext(mutation.context()),
                mutation.expiresAt());
        final AtomicReference<PermissionNode> node = new AtomicReference<>();
        this.repository.upsertGroupNode(normalizedCode, normalizedMutation, node::set);
        this.repository.writeAudit(new PermissionAuditEntry(
                "permission_group_node_upserted",
                "system",
                "system",
                "group",
                normalizedCode,
                Map.of("permission_node", normalizedNode)));
        this.invalidateGroup(normalizedCode);
        this.nodesByGroupCode.remove(normalizedCode);
        this.publish(new PermissionConfigurationEvent(
                "permission_group_node_upserted",
                "group_node",
                normalizedCode + ":" + normalizedNode,
                Map.of("group_code", normalizedCode, "permission_node", normalizedNode)));
        return node.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeGroupNode(final String groupCode, final String node, final String context) {
        final String normalizedCode = normalizeGroupCode(groupCode);
        final String normalizedNode = normalizeNode(node);
        final String normalizedContext = normalizeContext(context);
        final CancellablePermissionConfigurationEvent event = new CancellablePermissionConfigurationEvent(
                "permission_group_node_remove_requested",
                "group_node",
                normalizedCode + ":" + normalizedNode,
                Map.of("group_code", normalizedCode, "permission_node", normalizedNode));
        this.ensureNotCancelled(event);

        this.repository.deleteGroupNode(normalizedCode, normalizedNode, normalizedContext);
        this.repository.writeAudit(new PermissionAuditEntry(
                "permission_group_node_removed",
                "system",
                "system",
                "group",
                normalizedCode,
                Map.of("permission_node", normalizedNode)));
        this.invalidateGroup(normalizedCode);
        this.nodesByGroupCode.remove(normalizedCode);
        this.publish(new PermissionConfigurationEvent(
                "permission_group_node_removed",
                "group_node",
                normalizedCode + ":" + normalizedNode,
                Map.of("group_code", normalizedCode, "permission_node", normalizedNode)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlayerPermissionGroup> playerGroups(final int playerId, final boolean includeExpired) {
        final AtomicReference<List<PlayerPermissionGroup>> memberships = new AtomicReference<>(List.of());
        this.repository.getPlayerGroups(playerId, includeExpired, memberships::set);
        return memberships.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void grantGroupToPlayer(
            final int playerId,
            final String groupCode,
            final PermissionGrantRequest request) {
        final String normalizedCode = normalizeGroupCode(groupCode);
        final PermissionGrantRequest resolvedRequest = request == null
                ? new PermissionGrantRequest("system", "", null)
                : request;
        final CancellablePermissionConfigurationEvent event = new CancellablePermissionConfigurationEvent(
                "permission_group_grant_requested",
                "player_group",
                playerId + ":" + normalizedCode,
                Map.of("player_id", Integer.toString(playerId), "group_code", normalizedCode));
        this.ensureNotCancelled(event);

        this.repository.grantGroup(playerId, normalizedCode, resolvedRequest);
        this.repository.writeAudit(new PermissionAuditEntry(
                "permission_group_granted",
                "system",
                blankToDefault(resolvedRequest.grantedBy(), "system"),
                "player",
                Integer.toString(playerId),
                Map.of("group_code", normalizedCode)));
        this.snapshotsByPlayerId.remove(playerId);
        this.publish(new PermissionConfigurationEvent(
                "permission_group_granted",
                "player_group",
                playerId + ":" + normalizedCode,
                Map.of("player_id", Integer.toString(playerId), "group_code", normalizedCode)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void revokeGroupFromPlayer(final int playerId, final String groupCode) {
        final String normalizedCode = normalizeGroupCode(groupCode);
        final CancellablePermissionConfigurationEvent event = new CancellablePermissionConfigurationEvent(
                "permission_group_revoke_requested",
                "player_group",
                playerId + ":" + normalizedCode,
                Map.of("player_id", Integer.toString(playerId), "group_code", normalizedCode));
        this.ensureNotCancelled(event);

        this.repository.revokeGroup(playerId, normalizedCode);
        this.repository.writeAudit(new PermissionAuditEntry(
                "permission_group_revoked",
                "system",
                "system",
                "player",
                Integer.toString(playerId),
                Map.of("group_code", normalizedCode)));
        this.snapshotsByPlayerId.remove(playerId);
        this.publish(new PermissionConfigurationEvent(
                "permission_group_revoked",
                "player_group",
                playerId + ":" + normalizedCode,
                Map.of("player_id", Integer.toString(playerId), "group_code", normalizedCode)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reload() {
        final AtomicReference<List<PermissionGroup>> groups = new AtomicReference<>(List.of());
        final AtomicReference<List<PermissionNode>> nodes = new AtomicReference<>(List.of());
        this.repository.getGroups(false, groups::set);
        this.repository.getAllNodes(nodes::set);

        this.groupsByCode.clear();
        groups.get().forEach(group -> this.groupsByCode.put(group.code(), group));

        this.nodesByGroupCode.clear();
        nodes.get().stream()
                .collect(Collectors.groupingBy(PermissionNode::groupCode, LinkedHashMap::new, Collectors.toList()))
                .forEach((groupCode, groupNodes) -> this.nodesByGroupCode.put(groupCode, List.copyOf(groupNodes)));

        this.snapshotsByPlayerId.clear();
        this.publish(new PermissionsReloadedEvent(groups.get().size(), nodes.get().size()));
    }

    private PermissionSnapshot buildSnapshot(final int playerId) {
        if (this.groupsByCode.isEmpty()) {
            this.reload();
        }

        final List<PlayerPermissionGroup> memberships = this.playerGroups(playerId, false);
        final Set<String> seenGroupCodes = new HashSet<>();
        final List<PermissionGroup> groups = new ArrayList<>();

        this.groupsByCode.values().stream()
                .filter(PermissionGroup::defaultGroup)
                .filter(PermissionGroup::enabled)
                .forEach(group -> {
                    groups.add(group);
                    seenGroupCodes.add(group.code());
                });

        for (PlayerPermissionGroup membership : memberships) {
            final PermissionGroup group = membership.group();
            if (group.enabled() && seenGroupCodes.add(group.code())) {
                groups.add(group);
            }
        }

        groups.sort(groupComparator());

        final List<PermissionNode> nodes = groups.stream()
                .flatMap(group -> this.groupNodes(group.code()).stream())
                .filter(this::activeNode)
                .toList();
        final PermissionGroup legacyGroup = groups.stream()
                .findFirst()
                .orElse(new PermissionGroup(0, "player", "Player", 0, 1, false, true, true));

        return new PermissionSnapshot(
                playerId,
                List.copyOf(groups),
                List.copyOf(nodes),
                legacyGroup.priority(),
                legacyGroup.legacyRankId(),
                legacyGroup.displayName(),
                Instant.now());
    }

    private PermissionCheckResult resolve(final PermissionSnapshot snapshot, final String permissionNode) {
        Candidate best = null;
        final Map<String, PermissionGroup> groups = snapshot.groups().stream()
                .collect(Collectors.toMap(PermissionGroup::code, group -> group));

        for (PermissionNode node : snapshot.nodes()) {
            final int specificity = specificity(node.node(), permissionNode);
            if (specificity < 0) {
                continue;
            }

            final PermissionGroup group = groups.get(node.groupCode());
            if (group == null) {
                continue;
            }

            final Candidate candidate = new Candidate(node, group, specificity);
            if (best == null || candidate.compareTo(best) < 0) {
                best = candidate;
            }
        }

        if (best == null) {
            return PermissionCheckResult.denied(permissionNode);
        }

        return new PermissionCheckResult(
                best.node().effect() == PermissionEffect.ALLOW,
                permissionNode,
                best.node().node(),
                best.node().effect(),
                best.group().code(),
                best.group().priority(),
                best.node().node().endsWith(".*") || "*".equals(best.node().node())
                        ? "matched_wildcard"
                        : "matched_exact");
    }

    private void invalidateGroup(final String groupCode) {
        final AtomicReference<List<Integer>> playerIds = new AtomicReference<>(List.of());
        this.repository.getPlayersByGroup(groupCode, playerIds::set);
        playerIds.get().forEach(this.snapshotsByPlayerId::remove);
    }

    private void ensureNotCancelled(final CancellablePermissionConfigurationEvent event) {
        if (this.publish(event)) {
            throw new IllegalStateException(event.getCancellationMessage());
        }
    }

    private boolean publish(final Event event) {
        return this.eventHandler != null && this.eventHandler.handleEvent(event);
    }

    private boolean activeNode(final PermissionNode node) {
        return node.expiresAt() == null || node.expiresAt().isAfter(Instant.now());
    }

    private static int specificity(final String candidate, final String requested) {
        if (candidate.equals(requested)) {
            return 1000 + candidate.split("\\.").length;
        }

        if ("*".equals(candidate)) {
            return 0;
        }

        if (candidate.endsWith(".*")) {
            final String prefix = candidate.substring(0, candidate.length() - 1);
            if (requested.startsWith(prefix)) {
                return candidate.split("\\.").length;
            }
        }

        return -1;
    }

    private static Comparator<PermissionGroup> groupComparator() {
        return Comparator.comparingInt(PermissionGroup::priority)
                .reversed()
                .thenComparing(PermissionGroup::code);
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

    private record Candidate(PermissionNode node, PermissionGroup group, int specificity)
            implements Comparable<Candidate> {
        @Override
        public int compareTo(final Candidate other) {
            int result = Integer.compare(other.specificity(), this.specificity());
            if (result != 0) {
                return result;
            }

            result = Boolean.compare(this.node().effect() == PermissionEffect.DENY,
                    other.node().effect() == PermissionEffect.DENY);
            if (result != 0) {
                return -result;
            }

            return Integer.compare(other.group().priority(), this.group().priority());
        }
    }
}
