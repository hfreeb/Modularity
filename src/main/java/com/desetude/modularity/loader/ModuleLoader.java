package com.desetude.modularity.loader;

import com.desetude.modularity.Module;
import com.desetude.modularity.config.ModuleConfig;
import com.desetude.modularity.config.SimpleModuleConfig;
import com.desetude.modularity.injector.AutoRegister;
import com.desetude.modularity.injector.ModularityGuiceModule;
import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.atteo.classindex.ClassFilter;
import org.atteo.classindex.ClassIndex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class which is used to configure the loading of and load the {@link Module}s
 * in the current classpath.
 */
public class ModuleLoader {

    private final List<com.google.inject.Module> injectorModules;
    private ClassFilter.Predicate moduleFilter;
    private ModuleConfig moduleConfig;

    public ModuleLoader() {
        this.injectorModules = new ArrayList<>();
        this.moduleFilter = ClassFilter.only();
        this.moduleConfig = new SimpleModuleConfig();
    }

    /**
     * Adds the specified {@link com.google.inject.Module} as a Guice injector {@link com.google.inject.Module}
     * for injecting {@link Module} and {@link AutoRegister} classes.
     *
     * @param injectorModules to add
     * @return {@code this} for chaining
     */
    public ModuleLoader addInjectorModules(com.google.inject.Module... injectorModules) {
        this.injectorModules.addAll(Arrays.asList(injectorModules));
        return this;
    }

    /**
     * Sets {@code this} {@link ModuleLoader}'s class filter which allows
     * for filtering the classes which will be analyzed as {@link Module}s.
     * E.g. it could be used to only load classes from a specific package.
     *
     * @param filter for
     * @return {@code this} for chaining
     */
    public ModuleLoader setFilter(ClassFilter.Predicate filter) {
        this.moduleFilter = filter;
        return this;
    }

    /**
     * Sets {@code this} {@link ModuleLoader}'s {@link ModuleConfig} which
     * configures whether each {@link Module} is enabled.
     *
     * @param moduleConfig to set
     * @return {@code this} for chaining
     */
    public ModuleLoader setConfig(ModuleConfig moduleConfig) {
        this.moduleConfig = moduleConfig;
        return this;
    }

    /**
     * Loads all the {@link Module}s found in the current classpath, matching the set
     * filter and enabled from the set {@link ModuleConfig}.
     * Uses the {@link ClassLoader} used to load {@link ModuleLoader}.
     *
     * @return the created {@link Injector} to be used for manual injection
     */
    public Injector load() {
        return this.load(ModuleLoader.class.getClassLoader());
    }

    /**
     * Loads all the {@link Module}s found in the current classpath, matching the set
     * filter and enabled from the set {@link ModuleConfig}.
     *
     * @param classLoader uses the specified {@link ClassLoader} to find the {@link Module}s list
     * @return the created {@link Injector} to be used for manual injection
     */
    public Injector load(ClassLoader classLoader) {
        List<com.google.inject.Module> injectorModules = new ArrayList<>();
        injectorModules.add(new ModularityGuiceModule());
        injectorModules.addAll(this.injectorModules);

        List<Class<?>> modules = Lists.newArrayList(ClassIndex.getAnnotated(Module.class, classLoader)).stream()
                .filter(this.moduleFilter::matches)
                .collect(Collectors.toList());

        List<Class<?>> toLoad = new ArrayList<>();

        this.moduleConfig.initialize();

        for (Class<?> module : modules) {
            Module annotation = module.getAnnotation(Module.class);

            boolean enabled = annotation.enabled();

            if (this.moduleConfig.isSet(annotation.name())) {
                enabled = this.moduleConfig.isEnabled(annotation.name());
            } else {
                if (!annotation.hidden()) {
                    this.moduleConfig.setDefault(annotation.name(), annotation.desc(), annotation.enabled());
                }
            }

            if (enabled) {
                for (Class<? extends com.google.inject.Module> injectorModule : annotation.injectorModules()) {
                    try {
                        injectorModules.add(injectorModule.newInstance());
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException("Failed to load Guice module: " + injectorModule.getClass(), e);
                    }
                }

                toLoad.add(module);
            }
        }

        this.moduleConfig.close();

        Injector injector = Guice.createInjector(injectorModules);
        for (Class<?> module : toLoad) {
            injector.getInstance(module);
        }

        return injector;
    }

}
