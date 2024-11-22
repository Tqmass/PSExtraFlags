package com.survivaldub.handlers;

import com.survivaldub.PSExtraFlags;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigHandler {

    private final PSExtraFlags plugin;
    private FileConfiguration messagesFile;

    public ConfigHandler(PSExtraFlags plugin) {
        this.plugin = plugin;
        createDefaultFiles();
    }

    public void createDefaultFiles() {
        File file = new File(plugin.getDataFolder(), "messages.yml");

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource("messages.yml", false);
        }

        messagesFile = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getMessages() {
        return messagesFile;
    }

}