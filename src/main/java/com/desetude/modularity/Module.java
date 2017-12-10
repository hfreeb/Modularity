package com.desetude.modularity;

import com.desetude.modularity.config.ModuleConfig;
import com.google.inject.ScopeAnnotation;
import com.desetude.modularity.additionalmodules.BukkitGuiceModule;
import com.desetude.modularity.loader.ModuleLoader;
import org.atteo.classindex.IndexAnnotated;
import org.bukkit.event.Listener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Any class annotated with {@link Module} will be created and injected by Guice
 * if {@link ModuleLoader#load()} is called with the class in the {@link ModuleLoader}'s
 * classpath and the {@link Module} is enabled with {@link this#enabled()}.
 *
 * If {@link BukkitGuiceModule} has been added as a {@link com.google.inject.Module},
 * the annotated {@code class} will also be registered as a {@link Listener} if the
 * type {@code implements} {@link Listener}.
 */
@IndexAnnotated
@ScopeAnnotation
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Module {

    /**
     * The name representing {@code this} {@link Module}.
     *
     * @return {@code this} {@link Module}'s name
     */
    String name();

    /**
     * The description that explains {@code this} {@link Module}.
     *
     * @return {@code this} {@link Module}'s description
     */
    String desc() default "";

    /**
     * States whether {@code this} {@link Module} should, by default, be enabled and therefore loaded and run.
     * Overwritten by and values from the {@link ModuleLoader}'s {@link ModuleConfig}.
     *
     * @return whether {@code this} {@link Module} should be enabled by default
     */
    boolean enabled() default true;

    /**
     * States whether {@code this} {@link Module} is hidden and therefore should
     * not be set in the {@link ModuleConfig} by default.
     *
     * @return whether {@code this} {@link Module} should be hidden from {@link ModuleConfig}
     */
    boolean hidden() default false;

    /**
     * Returns the list of any additional Guice {@link com.google.inject.Module}s that should
     * be loaded for the {@link Module} class's injection.
     *
     * @return list of Guice {@link com.google.inject.Module}s to load
     */
    Class<? extends com.google.inject.Module>[] injectorModules() default {};

}
