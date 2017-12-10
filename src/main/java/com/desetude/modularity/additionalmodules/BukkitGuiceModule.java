package com.desetude.modularity.additionalmodules;

import com.desetude.modularity.injector.AutoRegister;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.io.File;

/**
 * Guice injector {@link Module} which binds the following:
 * {@link Plugin} -> The given {@link Plugin} instance.
 * {@link Server} -> The Bukkit {@link Server} instance.
 * {@link PluginManager} -> The Bukkit {@link PluginManager} instance.
 * {@link File} annotated with {@link DataDir} -> The given {@link Plugin} instance's data directory.
 *
 * Also, this module will register any of the {@link com.desetude.modularity.Module}s or
 * {@link AutoRegister}s as {@link Listener}s if they {@code implement} it.
 */
public class BukkitGuiceModule extends AbstractModule {

    private final Plugin plugin;

    public BukkitGuiceModule(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        this.bind(Plugin.class).toInstance(this.plugin);
        this.bind(Server.class).toProvider(this.plugin::getServer);
        this.bind(PluginManager.class).toProvider(this.plugin.getServer()::getPluginManager);
        this.bind(File.class).annotatedWith(DataDir.class).toProvider(this.plugin::getDataFolder);

        this.bindListener(Matchers.any(), new ListenerTypeListener());
    }

    private class ListenerTypeListener implements TypeListener {

        @Override
        public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
            Class<?> clazz = type.getRawType();
            if (Listener.class.isAssignableFrom(clazz)) {
                encounter.register((InjectionListener<I>) obj -> BukkitGuiceModule.this.plugin.getServer().getPluginManager()
                        .registerEvents((Listener) obj, BukkitGuiceModule.this.plugin));
            }
        }

    }

}
