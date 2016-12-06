package com.harryfreeborough.modularity.injector;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.harryfreeborough.modularity.Module;

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
