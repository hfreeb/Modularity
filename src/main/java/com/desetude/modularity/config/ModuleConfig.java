package com.desetude.modularity.config;

import com.desetude.modularity.Module;

/**
 * Configuration for whether each {@link Module} is enabled.
 */
public interface ModuleConfig {

    /**
     * Initializes the {@link ModuleConfig}, for use for loading files, connecting to databases, etc.
     */
    default void initialize() {
    }

    /**
     * Returns whether the {@link Module} with the specified name has been set
     * and has a value with the current {@link ModuleConfig}.
     *
     * @param name of {@link Module} to check
     * @return whether the {@link Module} with the specified name has been set
     */
    boolean isSet(String name);

    /**
     * Sets the default value of the {@link Module} with the specified name.
     *
     * @param name of the {@link Module} to set default value of
     * @param description to be used for comments on the entry, can be ignored
     * @param defaultValue to set
     */
    void setDefault(String name, String description, boolean defaultValue);

    /**
     * States whether the {@link Module} with the specified name is enabled
     * and therefore should be loaded.
     *
     * @param name of module to check
     * @return whether the {@link Module} with the specified name is enabled
     */
    boolean isEnabled(String name);

    /**
     * Closes the ModuleConfig for saving files, closing connections, etc.
     */
    default void close() {
    }

}
