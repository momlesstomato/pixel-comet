package com.cometproject.api.modules;

import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

import com.cometproject.api.commands.CommandInfo;
import com.cometproject.api.config.Configuration;
import com.cometproject.api.config.ModuleConfig;
import com.cometproject.api.config.modules.ModuleConfiguration;
import com.cometproject.api.events.Event;
import com.cometproject.api.events.EventListenerContainer;
import com.cometproject.api.game.GameContext;
import com.cometproject.api.networking.messages.IMessageEventHandler;
import com.cometproject.api.networking.sessions.ISession;
import com.cometproject.api.server.IGameService;

public abstract class BaseModule implements EventListenerContainer {

    /**
     * Module configuration
     */
    private final ModuleConfig config;

    /**
     * Assign a random UUD to the module at runtime, so the system can tell it apart from other modules.
     */
    private final UUID moduleId;

    /**
     * The bridge between Comet modules & the main server is the GameService, an object which allows you to
     * attach listeners to specific events which are fired within the server and also allows you to access
     * the game server's main components.
     */
    private final IGameService gameService;

    public BaseModule(ModuleConfig config, IGameService gameService) {
        this.moduleId = UUID.randomUUID();
        this.gameService = gameService;
        this.config = config;
    }

    /**
     * Register event with the event handler service
     *
     * @param event The event that will be called
     */
    protected void registerEvent(Event event) {
        this.getGameService().getEventHandler().registerEvent(event);
    }

    public void registerMessage(IMessageEventHandler messageEventHandler) {

    }

    /**
     * Registers a chat command with the event handler service
     * @param commandExecutor The command name
     * @param consumer The consumer of the command
     */
    protected void registerChatCommand(String commandExecutor, BiConsumer<ISession, String[]> consumer) {
        this.getGameService().getEventHandler().registerChatCommand(commandExecutor, consumer);
    }

    public void setup() {

    }

    /**
     * Gets overridden if the module would like to register game services
     * @param gameContext The game context to register the services to
     */
    public void initialiseServices(GameContext gameContext) {

    }

    /**
     * Resolves a module-scoped configuration value from the active configuration source.
     *
     * @param key The module-local configuration key.
     * @return The configured value, or {@code null} when undefined.
     */
    protected String getModuleConfig(final String key) {
        final String fullModuleKey = ModuleConfiguration.toModuleEnvironmentKey(this.getConfig().getName(), key);
        final String value = Configuration.currentConfig().get(fullModuleKey);

        if (value != null) {
            return value;
        }

        return Configuration.currentConfig().get(ModuleConfiguration.toShortModuleEnvironmentKey(this.getConfig().getName(), key));
    }

    /**
     * Resolves a module-scoped configuration value from the active configuration source using a fallback.
     *
     * @param key The module-local configuration key.
     * @param defaultValue The fallback value when the module key is undefined.
     * @return The configured or fallback value.
     */
    protected String getModuleConfig(final String key, final String defaultValue) {
        final String value = this.getModuleConfig(key);
        return value != null ? value : defaultValue;
    }

    /**
     * Returns the optional Guice module used to create this plugin's child injector.
     *
     * @return The plugin Guice module, or {@code null} when the plugin does not declare bindings.
     */
    public PluginGuiceModule getGuiceModule() {
        return null;
    }

    /**
     * Load all the module resources and then fire the "onModuleLoad" event.
     */
    public void loadModule() {
        if(this.getConfig() != null && this.getConfig().getCommands() != null) {
            for (Map.Entry<String, CommandInfo> commandInfoEntries : this.getConfig().getCommands().entrySet()) {
                this.getGameService().getEventHandler().registerCommandInfo(commandInfoEntries.getKey(), commandInfoEntries.getValue());
            }
        }
    }

    /**
     * Unload all module resources and then fire the "onModuleUnload" event.
     */
    public void unloadModule() {

    }

    /**
     * The random Module ID
     *
     * @return The random Module ID
     */
    public UUID getModuleId() {
        return moduleId;
    }

    /**
     * Get the main game service
     *
     * @return Main game service
     */
    public IGameService getGameService() {
        return this.gameService;
    }

    /**
     * Get the module configuration
     *
     * @return Module configuration
     */
    public ModuleConfig getConfig() {
        return config;
    }

    public static void main(String[] args) {
        // Hides all the "Unused class" stuff for modules, lol
    }
}
