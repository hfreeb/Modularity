package com.desetude.modularity.config;

import com.desetude.modularity.Module;

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
    public boolean isEnabled(String name) {
        return true;
    }

}
