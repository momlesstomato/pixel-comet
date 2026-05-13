package com.cometproject.server.boot;

import com.cometproject.api.game.GameContext;
import com.cometproject.api.utilities.Startable;
import com.cometproject.games.snowwar.thread.WorkerTasks;
import com.cometproject.server.boot.utils.gui.CometGui;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.catalog.CatalogManager;
import com.cometproject.server.game.groups.items.GroupItemManager;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.game.rooms.RoomManager;
import com.cometproject.server.game.utilities.validator.PlayerFigureValidator;
import com.cometproject.server.modules.ModuleManager;
import com.cometproject.server.storage.queries.config.ConfigDao;
import com.cometproject.server.storage.queries.rooms.RoomDao;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

import java.util.Map;

/**
 * Creates and drives the server composition root.
 */
public final class CometBootstrap {
    private static volatile Injector currentInjector;
    private final Injector injector;

    /**
     * Creates a new bootstrap instance and initializes the Guice injector.
     */
    public CometBootstrap() {
        this.injector = Guice.createInjector(new CometCoreModule());
        currentInjector = this.injector;
    }

    /**
     * Returns the active injector for static bridge methods.
     *
     * @return The active injector.
     * @throws IllegalStateException When bootstrap has not been created yet.
     */
    public static Injector getCurrentInjector() {
        if (currentInjector == null) {
            throw new IllegalStateException("Comet bootstrap has not been initialised");
        }

        return currentInjector;
    }

    /**
     * Resolves a singleton from the active injector.
     *
     * @param type The type to resolve.
     * @param <T> The resolved type.
     * @return The resolved singleton instance.
     */
    public static <T> T resolve(final Class<T> type) {
        return getCurrentInjector().getInstance(type);
    }

    /**
     * Returns this bootstrap's injector.
     *
     * @return The injector backing the server.
     */
    public Injector getInjector() {
        return this.injector;
    }

    /**
     * Starts the server lifecycle in the historic boot order.
     */
    public void start() {
        final Map<Integer, Startable> startupSequence = this.getStartupSequence();

        for (int step = 1; step <= 33; step++) {
            final Startable startable = startupSequence.get(step);

            if (startable != null) {
                startable.start();
            }

            switch (step) {
                case 3:
                    PlayerFigureValidator.loadFigureData();
                    break;
                case 7:
                    ConfigDao.getAll();
                    break;
                case 8:
                    ConfigDao.getSurvivalSettings();
                    break;
                case 9:
                    Locale.initialize();
                    break;
                case 27:
                    WorkerTasks.initWorkers();
                    break;
                case 28:
                    this.configureGameContext();
                    break;
                case 30:
                    this.injector.getInstance(ModuleManager.class).setupModules();
                    break;
                case 31:
                    RoomDao.getEmojis();
                    break;
                case 32:
                    this.configureGroupItemService();
                    break;
                default:
                    break;
            }
        }

        if (Comet.showGui) {
            new CometGui().setVisible(true);
        }
    }

    /**
     * Stops the lifecycle-managed services in reverse startup order.
     */
    public void stop() {
        this.getStartupSequence().entrySet().stream()
                .sorted(Map.Entry.<Integer, Startable>comparingByKey().reversed())
                .forEach(entry -> entry.getValue().stop());

        currentInjector = null;
    }

    private void configureGameContext() {
        final GameContext gameContext = this.injector.getInstance(GameContext.class);

        gameContext.setCatalogService(this.injector.getInstance(CatalogManager.class));
        gameContext.setFurnitureService(this.injector.getInstance(ItemManager.class));
        gameContext.setPlayerService(this.injector.getInstance(PlayerManager.class));
        gameContext.setRoomService(this.injector.getInstance(RoomManager.class));
        gameContext.setRoomModelService(this.injector.getInstance(RoomManager.class));

        GameContext.setCurrent(gameContext);
    }

    private void configureGroupItemService() {
        if (GameContext.getCurrent().getGroupService() != null) {
            GameContext.getCurrent().getGroupService().setItemService(new GroupItemManager());
        }
    }

    private Map<Integer, Startable> getStartupSequence() {
        return this.injector.getInstance(Key.get(new TypeLiteral<Map<Integer, Startable>>() {
        }));
    }

}
