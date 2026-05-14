package com.cometproject.server.modules;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cometproject.api.config.Configuration;
import com.cometproject.api.config.ModuleConfig;
import com.cometproject.api.config.modules.ModuleConfiguration;
import com.cometproject.api.events.EventHandler;
import com.cometproject.api.game.GameContext;
import com.cometproject.api.modules.BaseModule;
import com.cometproject.api.modules.PluginGuiceModule;
import com.cometproject.api.server.IGameService;
import com.cometproject.api.utilities.JsonUtil;
import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.modules.events.EventHandlerService;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Manages module runtime state for the module subsystem.
 */
public class ModuleManager implements Startable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleManager.class.getName());
    private final Injector serverInjector;
    private final EventHandler eventHandler;
    private final CometGameService gameService;
    private final Map<String, Injector> pluginInjectors;

    private Map<String, BaseModule> modules;

    @Inject
    ModuleManager(final Injector serverInjector) {
        this.serverInjector = serverInjector;
        this.eventHandler = new EventHandlerService();
        this.gameService = new CometGameService(this.eventHandler, this);
        this.pluginInjectors = new ConcurrentHashMap<>();
    }

    /**
     * Returns the instance for this module contract.
     *
     * @return Value exposed by the contract.
     */
    public static ModuleManager getInstance() {
        return CometBootstrap.resolve(ModuleManager.class);
    }

    /**
     * Starts this module component.
     */
    @Override
    public void start() {
        if (this.modules != null) {
            this.modules.clear();
        } else {
            this.modules = new ConcurrentHashMap<>();
        }

        this.pluginInjectors.clear();

        this.getEventHandler().initialize();

        this.loadModules();
    }

    /**
     * Updates the up modules for this module contract.
     */
    public void setupModules() {
        for (BaseModule baseModule : this.modules.values()) {
            this.activateModule(baseModule);
        }
    }

    private void activateModule(final BaseModule baseModule) {
        baseModule.setup();

        final PluginGuiceModule pluginGuiceModule = baseModule.getGuiceModule();

        if (pluginGuiceModule != null) {
            final Injector childInjector = this.serverInjector.createChildInjector(pluginGuiceModule);
            this.pluginInjectors.put(baseModule.getConfig().getName(), childInjector);
        }

        baseModule.initialiseServices(GameContext.getCurrent());
    }

    private List<CometModule> loadModules() {
        final List<CometModule> cometModules = Lists.newArrayList();

        for (Path moduleJar : this.discoverJars()) {
            try {
                final CometModule module = this.loadModule(moduleJar);
                if (module != null) {
                    cometModules.add(module);
                }
            } catch (Exception e) {
                LOGGER.error("Failed to load module jar: {}", moduleJar, e);
            }
        }

        return cometModules;
    }

    private List<Path> discoverJars() {
        final Path modulesDir = Path.of(Configuration.currentConfig().getOrDefault(ModuleConfiguration.MODULES_DIR, ModuleConfiguration.defaults().get(ModuleConfiguration.MODULES_DIR)));

        if (!Files.isDirectory(modulesDir)) {
            LOGGER.info("No modules directory found at {}, skipping plugin loading", modulesDir);
            return List.of();
        }

        try (var stream = Files.list(modulesDir)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".jar"))
                    .sorted()
                    .toList();
        } catch (IOException e) {
            LOGGER.error("Failed to scan modules directory {}", modulesDir, e);
            return List.of();
        }
    }

    private CometModule loadModule(final Path moduleJar) throws Exception {
        URLClassLoader loader = URLClassLoader.newInstance(
                new URL[]{new URL("jar:file:" + moduleJar.toAbsolutePath() + "!/")},
                getClass().getClassLoader()
        );

        URL configJsonLocation = loader.getResource("module.json");

        if (configJsonLocation == null) {
            LOGGER.warn("Skipping module jar without module.json: {}", moduleJar.getFileName());
            return null;
        }

        final ModuleConfig moduleConfig = JsonUtil.getInstance().fromJson(Resources.toString(configJsonLocation, Charsets.UTF_8), ModuleConfig.class);

        if (this.modules.containsKey(moduleConfig.getName())) {
            if (!this.modules.get(moduleConfig.getName()).getConfig().getVersion().equals(moduleConfig.getVersion())) {
                LOGGER.warn("Modules with same name but different version was detected: " + moduleConfig.getName());
            }

            return null;
        }

        LOGGER.info("Loaded module: {} from {}", moduleConfig.getName(), moduleJar.getFileName());

        Class<?> clazz = Class.forName(moduleConfig.getEntryPoint(), true, loader);
        Class<? extends BaseModule> runClass = clazz.asSubclass(BaseModule.class);
        Constructor<? extends BaseModule> ctor = runClass.getConstructor(ModuleConfig.class, IGameService.class);

        BaseModule cometModule = ctor.newInstance(moduleConfig, this.gameService);

        cometModule.loadModule();

        final CometModule loadedModule = new CometModule(moduleJar, moduleConfig);
        loadedModule.setClassLoader(loader);
        this.modules.put(moduleConfig.getName(), cometModule);

        //loader.close();
        return loadedModule;
    }

    /**
     * Returns the event handler for this module contract.
     *
     * @return Value exposed by the contract.
     */
    public EventHandler getEventHandler() {
        return eventHandler;
    }

    Injector getPluginInjector(final String moduleName) {
        return this.pluginInjectors.get(moduleName);
    }
}
