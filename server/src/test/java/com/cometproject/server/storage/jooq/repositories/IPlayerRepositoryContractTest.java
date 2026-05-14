package com.cometproject.server.storage.jooq.repositories;

import com.cometproject.api.game.players.data.IPlayerData;
import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.storage.api.repositories.IPlayerRepository;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

abstract class IPlayerRepositoryContractTest {
    /**
     * Executes repository for this jOOQ storage contract.
     *
     * @return Result produced by the operation.
     */
    protected abstract IPlayerRepository repository();

    /**
     * Executes dsl for this jOOQ storage contract.
     *
     * @return Result produced by the operation.
     */
    protected abstract DSLContext dsl();

    /**
     * Executes insert test player for this jOOQ storage contract.
     *
     * @param id Id supplied by the caller.
     * @param username Username supplied by the caller.
     * @param authTicket Auth ticket supplied by the caller.
     */
    protected abstract void insertTestPlayer(int id, String username, String authTicket);

    @Test
    void findAvatarByIdReturnsAvatarWhenPlayerExists() {
        this.insertTestPlayer(100, "pixie", "ticket-100");

        final AtomicReference<PlayerAvatar> avatar = new AtomicReference<>();

        this.repository().findAvatarById(100, PlayerAvatar.USERNAME_FIGURE_MOTTO, avatar::set);

        assertNotNull(avatar.get());
        assertEquals(100, avatar.get().getId());
        assertEquals("pixie", avatar.get().getUsername());
        assertEquals("hello", avatar.get().getMotto());
    }

    @Test
    void findAvatarByIdDoesNotCallConsumerWhenPlayerDoesNotExist() {
        final AtomicBoolean called = new AtomicBoolean(false);

        this.repository().findAvatarById(404, PlayerAvatar.USERNAME_FIGURE_MOTTO, avatar -> called.set(true));

        assertFalse(called.get());
    }

    @Test
    void savePersistsMutablePlayerData() {
        this.insertTestPlayer(101, "before", "ticket-101");

        this.repository().save(this.playerData(101, "after", 750));

        final org.jooq.Record record = this.dsl()
                .select()
                .from("players")
                .where("id = ?", 101)
                .fetchOne();

        assertNotNull(record);
        assertEquals("after", record.get("username", String.class));
        assertEquals(750, record.get("credits", Integer.class));
        assertEquals("working", record.get("motto", String.class));
    }

    @Test
    void updateLastOnlineUpdatesTimestampAndIpAddress() {
        this.insertTestPlayer(102, "online", "ticket-102");

        this.repository().updateLastOnline(102, "127.0.0.1");

        final org.jooq.Record record = this.dsl()
                .select()
                .from("players")
                .where("id = ?", 102)
                .fetchOne();

        assertNotNull(record);
        assertEquals("127.0.0.1", record.get("last_ip", String.class));
        assertNotNull(record.get("last_online", Integer.class));
    }

    @Test
    void resetOnlineStatusClearsStaleOnlinePlayers() {
        this.insertTestPlayer(103, "stale", "ticket-103");
        this.dsl().update(org.jooq.impl.DSL.table("players"))
                .set(org.jooq.impl.DSL.field("online", String.class), "1")
                .where("id = ?", 103)
                .execute();

        this.repository().resetOnlineStatus();

        final String online = this.dsl()
                .select(org.jooq.impl.DSL.field("online", String.class))
                .from("players")
                .where("id = ?", 103)
                .fetchOneInto(String.class);

        assertEquals("0", online);
    }

    private IPlayerData playerData(final int id, final String username, final int credits) {
        final IPlayerData data = mock(IPlayerData.class);

        when(data.getId()).thenReturn(id);
        when(data.getUsername()).thenReturn(username);
        when(data.getMotto()).thenReturn("working");
        when(data.getFigure()).thenReturn("hr-100-61");
        when(data.getCredits()).thenReturn(credits);
        when(data.getGender()).thenReturn("M");
        when(data.getFavouriteGroup()).thenReturn(0);
        when(data.getQuestId()).thenReturn(4);
        when(data.getAchievementPoints()).thenReturn(5);
        when(data.getNameColour()).thenReturn("000000");
        when(data.getTag()).thenReturn("tag");
        when(data.getJob()).thenReturn("job");

        return data;
    }
}
