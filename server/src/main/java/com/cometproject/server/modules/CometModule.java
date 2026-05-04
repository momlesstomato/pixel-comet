package com.cometproject.server.modules;

import java.nio.file.Path;

import com.cometproject.api.config.ModuleConfig;

final class CometModule {
    private final Path jarPath;
    private final ModuleConfig moduleConfig;
    private ClassLoader classLoader;

    CometModule(final Path jarPath, final ModuleConfig moduleConfig) {
        this.jarPath = jarPath;
        this.moduleConfig = moduleConfig;
    }

    public Path getJarPath() {
        return this.jarPath;
    }

    public ModuleConfig getModuleConfig() {
        return this.moduleConfig;
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    public void setClassLoader(final ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
