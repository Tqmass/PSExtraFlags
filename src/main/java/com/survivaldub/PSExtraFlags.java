package com.survivaldub;

import com.survivaldub.handlers.ConfigHandler;
import com.survivaldub.handlers.FlagHandler;
import com.survivaldub.listeners.AnimalProtectionListener;
import com.survivaldub.listeners.TeleportListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PSExtraFlags extends JavaPlugin {

    private static PSExtraFlags instance;
    private ConfigHandler files;

    @Override
    public void onEnable() {
        instance = this;
        files = new ConfigHandler(this);

        FlagHandler.registerFlags();

        Bukkit.getPluginManager().registerEvents(new TeleportListener(), this);
        Bukkit.getPluginManager().registerEvents(new AnimalProtectionListener(), this);

        getLogger().info("Plugin iniciado correctamente.");

    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin desactivado correctamente.");
    }

    public static PSExtraFlags getInstance() {
        return instance;
    }

    public ConfigHandler getFiles() {
        return files;
    }
}
