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

import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ModuleManager implements Initialisable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleManager.class.getName());
    private static ModuleManager moduleManagerInstance;
    private EventHandler eventHandler;
    private CometGameService gameService;

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

        this.loadModules();
    }

    public void setupModules() {
        for (BaseModule baseModule : this.modules.values()) {
            baseModule.setup();

            baseModule.initialiseServices(GameContext.getCurrent());
        }
    }

    private List<CometModule> loadModules() {
        final ModulesConfig modulesConfig;

        try {
            final String json = Files.readString(Path.of("./config/modules.json"), StandardCharsets.UTF_8);
            modulesConfig = JsonUtil.getInstance().fromJson(json, ModulesConfig.class);
        } catch (Exception e) {
            LOGGER.error("Failed to load modules configuration", e);
            return Lists.newArrayList();
        }

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
    }
//
//    private List<String> findModules() {
//       List<String> results = new ArrayList<>();
//
//        File[] files = new File("./modules").listFiles();
//
//        if (files == null) return results;
//
//        for (File file : files) {
//            if (file.isFile() && file.getName().endsWith(".jar")) {
//                results.add(file.getName());
//            }
//        }
//
//        return results;
//    }

    private void loadModule(CometModule module) throws Exception {
        URLClassLoader loader = URLClassLoader.newInstance(
                new URL[]{new URL("jar:file:" + module.getPath() + "!/")},
                getClass().getClassLoader()
        );

        URL configJsonLocation = loader.getResource("module.json");

        if (configJsonLocation == null) throw new Exception("module.json does not exist");

        final ModuleConfig moduleConfig = JsonUtil.getInstance().fromJson(Resources.toString(configJsonLocation, Charsets.UTF_8), ModuleConfig.class);

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

        //loader.close();
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
