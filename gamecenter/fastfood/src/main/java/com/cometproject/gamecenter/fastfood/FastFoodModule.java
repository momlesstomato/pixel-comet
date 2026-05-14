package com.cometproject.gamecenter.fastfood;

import com.cometproject.api.config.ModuleConfig;
import com.cometproject.api.game.GameContext;
import com.cometproject.api.modules.BaseModule;
import com.cometproject.api.server.IGameService;
import com.cometproject.gamecenter.fastfood.net.FastFoodMessageHandler;
import com.cometproject.gamecenter.fastfood.net.SessionFactory;
import com.cometproject.gamecenter.fastfood.storage.MySQLFastFoodRepository;
import com.cometproject.networking.api.INetworkingServer;
import com.cometproject.networking.api.NetworkingContext;
import com.cometproject.networking.api.config.NetworkingServerConfig;
import com.cometproject.storage.mysql.MySQLStorageContext;
import com.google.common.collect.Sets;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Describes fast food module behavior for the Fast Food game subsystem.
 */
public class FastFoodModule extends BaseModule {

    private MySQLFastFoodRepository fastFoodRepository;
    private INetworkingServer fastFoodServer;
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

    /**
     * Creates a fast food module instance for the Fast Food game subsystem.
     *
     * @param config Config supplied by the caller.
     * @param gameService Game service supplied by the caller.
     */
    public FastFoodModule(ModuleConfig config, IGameService gameService) {
        super(config, gameService);
    }

    /**
     * Updates the up for this Fast Food game contract.
     */
    @Override
    public void setup() {
        this.fastFoodRepository = new MySQLFastFoodRepository(MySQLStorageContext.getCurrentContext().getConnectionProvider());
    }

    /**
     * Executes initialise services for this Fast Food game contract.
     *
     * @param gameContext Game context supplied by the caller.
     */
    @Override
    public void initialiseServices(GameContext gameContext) {
        final short serverPort = 30010;

        this.fastFoodServer = NetworkingContext.getCurrentContext().getServerFactory().createServer(
                new NetworkingServerConfig("0.0.0.0", Sets.newHashSet(serverPort)),
                new SessionFactory(new FastFoodMessageHandler(this.executorService, gameContext.getPlayerService(),
                        this.fastFoodRepository)));

        this.fastFoodServer.start();
    }
}
