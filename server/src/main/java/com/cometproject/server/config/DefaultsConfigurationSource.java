package com.cometproject.server.config;

import java.util.LinkedHashMap;
import java.util.Map;

import com.cometproject.api.config.ConfigurationSource;
import com.cometproject.api.config.api.ApiConfiguration;
import com.cometproject.api.config.cache.RedisConfiguration;
import com.cometproject.api.config.database.DatabaseConfiguration;
import com.cometproject.api.config.game.GameConfiguration;
import com.cometproject.api.config.modules.ModuleConfiguration;
import com.cometproject.api.config.network.ConnectionRegistryConfiguration;
import com.cometproject.api.config.network.NetworkConfiguration;
import com.cometproject.api.config.network.TransportConfiguration;
import com.cometproject.api.config.rcon.RconConfiguration;
import com.cometproject.api.config.system.SystemConfiguration;

/**
 * Exposes the documented safe defaults for optional configuration keys.
 */
public final class DefaultsConfigurationSource implements ConfigurationSource {
    private final Map<String, String> defaults;

    /**
     * Creates a new defaults-backed configuration source.
     */
    public DefaultsConfigurationSource() {
        final Map<String, String> defaultsMap = new LinkedHashMap<>();
        defaultsMap.putAll(DatabaseConfiguration.defaults());
        defaultsMap.putAll(NetworkConfiguration.defaults());
        defaultsMap.putAll(SystemConfiguration.defaults());
        defaultsMap.putAll(GameConfiguration.defaults());
        defaultsMap.putAll(RedisConfiguration.defaults());
        defaultsMap.putAll(ApiConfiguration.defaults());
        defaultsMap.putAll(TransportConfiguration.defaults());
        defaultsMap.putAll(ConnectionRegistryConfiguration.defaults());
        defaultsMap.putAll(RconConfiguration.defaults());
        defaultsMap.putAll(ModuleConfiguration.defaults());
        this.defaults = Map.copyOf(defaultsMap);
    }

    @Override
    public String get(final String key) {
        return this.defaults.get(key);
    }

    @Override
    public Map<String, String> getAll() {
        return this.defaults;
    }
}