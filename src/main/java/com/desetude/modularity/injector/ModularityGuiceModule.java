package com.desetude.modularity.injector;

import com.desetude.modularity.Module;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * Basic Guice {@link com.google.inject.Module} that binds {@link Module} and {@link AutoRegister}
 * as Singletons.
 */
public class ModularityGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bindScope(Module.class, Scopes.SINGLETON);
        this.bindScope(AutoRegister.class, Scopes.SINGLETON);
    }

}
