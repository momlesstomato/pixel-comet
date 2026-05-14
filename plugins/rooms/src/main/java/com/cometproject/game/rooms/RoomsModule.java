package com.cometproject.game.rooms;

import com.cometproject.api.caching.Cache;
import com.cometproject.api.config.ModuleConfig;
import com.cometproject.api.game.GameContext;
import com.cometproject.api.game.rooms.IRoomData;
import com.cometproject.api.game.rooms.IRoomService;
import com.cometproject.api.game.rooms.models.IRoomModelFactory;
import com.cometproject.api.game.rooms.models.IRoomModelService;
import com.cometproject.api.modules.BaseModule;
import com.cometproject.api.modules.PluginGuiceModule;
import com.cometproject.api.server.IGameService;
import com.cometproject.common.caching.LastReferenceCache;
import com.cometproject.game.rooms.factories.RoomModelFactory;
import com.cometproject.game.rooms.services.RoomModelService;
import com.cometproject.game.rooms.services.RoomService;
import com.cometproject.storage.api.StorageContext;

/**
 * Describes rooms module behavior for the room subsystem.
 */
public class RoomsModule extends BaseModule {
    private IRoomModelService roomModelService;
    private IRoomService roomService;

    /**
     * Creates a rooms module instance for the room subsystem.
     *
     * @param config Config supplied by the caller.
     * @param gameService Game service supplied by the caller.
     */
    public RoomsModule(ModuleConfig config, IGameService gameService) {
        super(config, gameService);
    }

    /**
     * Updates the up for this room contract.
     */
    @Override
    public void setup() {
        final IRoomModelFactory roomModelFactory = new RoomModelFactory();

        final Cache<Integer, IRoomData> roomDataCache = new LastReferenceCache<>(
                60000, (86400 * 1000),
                null, this.getGameService().getExecutorService());

        this.roomService = new RoomService(StorageContext.getCurrentContext().getRoomRepository(), roomDataCache);

        this.roomModelService = new RoomModelService(roomModelFactory,
                StorageContext.getCurrentContext().getRoomRepository());
    }

    /**
     * Executes initialise services for this room contract.
     *
     * @param gameContext Game context supplied by the caller.
     */
    @Override
    public void initialiseServices(GameContext gameContext) {
        this.roomModelService.loadModels();

        gameContext.setRoomService(this.roomService);
        gameContext.setRoomModelService(this.roomModelService);
    }

    /**
     * Exposes the room services through the plugin child injector.
     *
     * @return The plugin Guice module for the rooms plugin.
     */
    @Override
    public PluginGuiceModule getGuiceModule() {
        return new PluginGuiceModule(this.getGameService()) {
            /**
             * Executes configure for this room contract.
             */
            @Override
            protected void configure() {
                bind(IRoomService.class).toInstance(RoomsModule.this.roomService);
                bind(IRoomModelService.class).toInstance(RoomsModule.this.roomModelService);
                bind(IGameService.class).toInstance(getGameService());
                bind(RoomsModule.class).toInstance(RoomsModule.this);
            }
        };
    }
}
