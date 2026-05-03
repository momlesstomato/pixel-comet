package com.cometproject.server.modules;

import com.cometproject.api.config.ModuleConfig;
import com.cometproject.api.events.EventHandler;
import com.cometproject.api.game.GameContext;
import com.cometproject.api.modules.BaseModule;
import com.cometproject.api.server.IGameService;
import com.cometproject.api.utilities.Initialisable;
import com.cometproject.api.utilities.JsonUtil;
import com.cometproject.server.modules.events.EventHandlerService;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ModuleManager implements Initialisable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleManager.class.getName());

    /** Core plugin class names loaded reflectively to avoid compile-time coupling to plugin subprojects. */
    private static final String[] CORE_MODULE_CLASS_NAMES = {
        "com.cometproject.game.groups.GroupsModule",
        "com.cometproject.gamecenter.fastfood.FastFoodModule",
        "com.cometproject.game.rooms.RoomsModule"
    };

    private static ModuleManager moduleManagerInstance;
    private final EventHandler eventHandler;
    private final CometGameService gameService;

    private Map<String, BaseModule> modules;

    public ModuleManager() {
        this.eventHandler = new EventHandlerService();
        this.gameService = new CometGameService(this.eventHandler);
    }

    public static ModuleManager getInstance() {
        if (moduleManagerInstance == null) {
            moduleManagerInstance = new ModuleManager();
        }

        return moduleManagerInstance;
    }

    @Override
    public void initialize() {
        if (this.modules != null) {
            this.modules.clear();
        } else {
            this.modules = new ConcurrentHashMap<>();
        }

        ModuleManager.getInstance().getEventHandler().initialize();

        for (String className : CORE_MODULE_CLASS_NAMES) {
            this.loadCoreModule(className);
        }
    }

    @SuppressWarnings("unchecked")
    private void loadCoreModule(String className) {
        try {
            Class<? extends BaseModule> moduleClass =
                (Class<? extends BaseModule>) Class.forName(className).asSubclass(BaseModule.class);
            Constructor<? extends BaseModule> ctor = moduleClass.getConstructor(ModuleConfig.class, IGameService.class);
            BaseModule cometModule = ctor.newInstance(null, this.gameService);
            cometModule.loadModule();
            this.modules.put(moduleClass.getSimpleName(), cometModule);
        } catch (Exception e) {
            LOGGER.error("Failed to load system module: " + className, e);
        }
    }

    public void setupModules() {
        for (BaseModule baseModule : this.modules.values()) {
            baseModule.setup();
            baseModule.initialiseServices(GameContext.getCurrent());
        }
    }

    private List<CometModule> loadModules() {
        try {
            final String json = new String(Files.readAllBytes(Paths.get("./config/modules.json")));
            final ModulesConfig modulesConfig = JsonUtil.getInstance().fromJson(json, ModulesConfig.class);
            final List<CometModule> cometModules = Lists.newArrayList();

            for (CometModule module : modulesConfig.getModules()) {
                try {
                    loadModule(module);
                    cometModules.add(module);
                } catch (Exception e) {
                    LOGGER.error("Failed to load module: " + module.getAlias(), e);
                }
            }

            return cometModules;
        } catch (IOException e) {
            LOGGER.error("Failed to read modules.json", e);
            return Lists.newArrayList();
        }
    }

    private void loadModule(CometModule module) throws Exception {
        URLClassLoader loader = URLClassLoader.newInstance(
                new URL[]{URI.create("jar:file:" + module.getPath() + "!/").toURL()},
                getClass().getClassLoader()
        );

        URL configJsonLocation = loader.getResource("module.json");

        if (configJsonLocation == null) throw new Exception("module.json does not exist");

        final ModuleConfig moduleConfig = JsonUtil.getInstance().fromJson(
                Resources.toString(configJsonLocation, Charsets.UTF_8), ModuleConfig.class);

        if (this.modules.containsKey(moduleConfig.getName())) {
            if (!this.modules.get(moduleConfig.getName()).getConfig().getVersion().equals(moduleConfig.getVersion())) {
                LOGGER.warn("Modules with same name but different version was detected: " + moduleConfig.getName());
            }
            return;
        }

        LOGGER.info("Loaded module: " + moduleConfig.getName() + ", alias: " + module.getAlias());

        Class<?> clazz = Class.forName(moduleConfig.getEntryPoint(), true, loader);
        Class<? extends BaseModule> runClass = clazz.asSubclass(BaseModule.class);
        Constructor<? extends BaseModule> ctor = runClass.getConstructor(ModuleConfig.class, IGameService.class);

        BaseModule cometModule = ctor.newInstance(moduleConfig, this.gameService);
        cometModule.loadModule();
        module.setClassLoader(loader);
        this.modules.put(moduleConfig.getName(), cometModule);
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    private class ModulesConfig {
        private final List<CometModule> modules;

        public ModulesConfig(final List<CometModule> modules) {
            this.modules = modules;
        }

        public List<CometModule> getModules() {
            return modules;
        }
    }
}
