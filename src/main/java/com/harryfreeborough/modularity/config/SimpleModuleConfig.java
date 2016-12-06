package com.harryfreeborough.modularity.config;

import com.harryfreeborough.modularity.Module;

/**
 * Simple {@link ModuleConfig} implementation where each {@link Module}
 * will be enabled per their {@link Module#enabled()} value and therefore
 * not changed by the {@link ModuleConfig}.
 */
public class SimpleModuleConfig implements ModuleConfig {

    @Override
    public boolean isSet(String name) {
        return false;
    }

    @Override
    public void setDefault(String name, String description, boolean defaultValue) {

    }

    @Override
    public boolean isEnabled(String name) {
        return true;
    }

}
