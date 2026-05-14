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

    /**
     * Returns the jar path for this module contract.
     *
     * @return Value exposed by the contract.
     */
    public Path getJarPath() {
        return this.jarPath;
    }

    /**
     * Returns the module config for this module contract.
     *
     * @return Value exposed by the contract.
     */
    public ModuleConfig getModuleConfig() {
        return this.moduleConfig;
    }

    /**
     * Returns the class loader for this module contract.
     *
     * @return Value exposed by the contract.
     */
    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    /**
     * Updates the class loader for this module contract.
     *
     * @param classLoader Class loader supplied by the caller.
     */
    public void setClassLoader(final ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
