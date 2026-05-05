package com.cometproject.server.boot;

import com.cometproject.api.game.GameContext;
import com.cometproject.api.game.sso.ISsoTicketRepository;
import com.cometproject.api.game.sso.ISsoTicketService;
import com.cometproject.api.utilities.Startable;
import com.cometproject.server.api.APIManager;
import com.cometproject.server.game.GameCycle;
import com.cometproject.server.game.achievements.AchievementManager;
import com.cometproject.server.game.catalog.CatalogManager;
import com.cometproject.server.game.commands.CommandManager;
import com.cometproject.server.game.gamecenter.GameCenterManager;
import com.cometproject.server.game.guides.GuideManager;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.game.landing.LandingManager;
import com.cometproject.server.game.moderation.BanManager;
import com.cometproject.server.game.moderation.ModerationManager;
import com.cometproject.server.game.navigator.NavigatorManager;
import com.cometproject.server.game.permissions.PermissionsManager;
import com.cometproject.server.game.pets.PetManager;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.game.polls.PollManager;
import com.cometproject.server.game.quests.QuestManager;
import com.cometproject.server.game.rooms.RoomManager;
import com.cometproject.server.game.rooms.bundles.RoomBundleManager;
import com.cometproject.server.game.sso.SsoTicketService;
import com.cometproject.server.game.sso.storage.RedisSsoTicketRepository;
import com.cometproject.server.logging.LogManager;
import com.cometproject.server.modules.ModuleManager;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.storage.StorageManager;
import com.cometproject.server.storage.cache.CacheManager;
import com.cometproject.server.tasks.CometThreadManager;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.MapBinder;

/**
 * Declares the server-wide Guice object graph.
 *
 * <p>The startup sequence bindings intentionally mirror the historic boot order
 * so the migration keeps the existing runtime behaviour intact.
 */
public final class CometCoreModule extends AbstractModule {
    /**
     * Configures the Guice bindings for the server composition root.
     */
    @Override
    protected void configure() {
        bind(CometThreadManager.class).in(Scopes.SINGLETON);
        bind(StorageManager.class).in(Scopes.SINGLETON);
        bind(CacheManager.class).in(Scopes.SINGLETON);
        bind(LogManager.class).in(Scopes.SINGLETON);
        bind(PermissionsManager.class).in(Scopes.SINGLETON);
        bind(ItemManager.class).in(Scopes.SINGLETON);
        bind(CatalogManager.class).in(Scopes.SINGLETON);
        bind(RoomManager.class).in(Scopes.SINGLETON);
        bind(RoomBundleManager.class).in(Scopes.SINGLETON);
        bind(NavigatorManager.class).in(Scopes.SINGLETON);
        bind(CommandManager.class).in(Scopes.SINGLETON);
        bind(BanManager.class).in(Scopes.SINGLETON);
        bind(ModerationManager.class).in(Scopes.SINGLETON);
        bind(PetManager.class).in(Scopes.SINGLETON);
        bind(LandingManager.class).in(Scopes.SINGLETON);
        bind(PlayerManager.class).in(Scopes.SINGLETON);
        bind(QuestManager.class).in(Scopes.SINGLETON);
        bind(AchievementManager.class).in(Scopes.SINGLETON);
        bind(PollManager.class).in(Scopes.SINGLETON);
        bind(GuideManager.class).in(Scopes.SINGLETON);
        bind(GameCenterManager.class).in(Scopes.SINGLETON);
        bind(NetworkManager.class).in(Scopes.SINGLETON);
        bind(APIManager.class).in(Scopes.SINGLETON);
        bind(ISsoTicketRepository.class).to(RedisSsoTicketRepository.class).in(Scopes.SINGLETON);
        bind(ISsoTicketService.class).to(SsoTicketService.class).in(Scopes.SINGLETON);
        bind(ModuleManager.class).in(Scopes.SINGLETON);
        bind(GameCycle.class).in(Scopes.SINGLETON);
        bind(GameContext.class).in(Scopes.SINGLETON);

        this.bindStartupSequence();
    }

    private void bindStartupSequence() {
        final MapBinder<Integer, Startable> startupSequence =
                MapBinder.newMapBinder(binder(), Integer.class, Startable.class);

        startupSequence.addBinding(1).to(ModuleManager.class);
        startupSequence.addBinding(2).to(APIManager.class);
        startupSequence.addBinding(4).to(CometThreadManager.class);
        startupSequence.addBinding(5).to(StorageManager.class);
        startupSequence.addBinding(6).to(LogManager.class);
        startupSequence.addBinding(10).to(PermissionsManager.class);
        startupSequence.addBinding(11).to(RoomBundleManager.class);
        startupSequence.addBinding(12).to(ItemManager.class);
        startupSequence.addBinding(13).to(CatalogManager.class);
        startupSequence.addBinding(14).to(RoomManager.class);
        startupSequence.addBinding(15).to(NavigatorManager.class);
        startupSequence.addBinding(16).to(CommandManager.class);
        startupSequence.addBinding(17).to(BanManager.class);
        startupSequence.addBinding(18).to(ModerationManager.class);
        startupSequence.addBinding(19).to(PetManager.class);
        startupSequence.addBinding(20).to(LandingManager.class);
        startupSequence.addBinding(21).to(PlayerManager.class);
        startupSequence.addBinding(22).to(QuestManager.class);
        startupSequence.addBinding(23).to(AchievementManager.class);
        startupSequence.addBinding(24).to(PollManager.class);
        startupSequence.addBinding(25).to(GuideManager.class);
        startupSequence.addBinding(26).to(GameCenterManager.class);
        startupSequence.addBinding(29).to(NetworkManager.class);
        startupSequence.addBinding(33).to(GameCycle.class);
    }
}