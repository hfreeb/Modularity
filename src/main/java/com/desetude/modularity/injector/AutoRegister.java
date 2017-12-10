package com.desetude.modularity.injector;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.google.inject.Module;
import com.google.inject.ScopeAnnotation;
import com.desetude.modularity.additionalmodules.BukkitGuiceModule;
import org.bukkit.event.Listener;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Any class annotated with {@link AutoRegister} will have its instance
 * shared between all modules that request it for injection.
 *
 * If {@link BukkitGuiceModule} has been added as a {@link Module},
 * the annotated {@code class} will also be registered as a {@link Listener} if the
 * type {@code implements} {@link Listener}.
 */
@Retention(RUNTIME)
@Target(TYPE)
@ScopeAnnotation
public @interface AutoRegister {

}