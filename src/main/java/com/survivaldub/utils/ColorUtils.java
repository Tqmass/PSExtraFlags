package com.survivaldub.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;

public class ColorUtils {

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static Component translate(String message) {
        if (isMiniMessage(message)) {
            message = translateGradient(message);
            return miniMessage.deserialize(message);
        } else {
            String legacyMessage = ChatColor.translateAlternateColorCodes('&', message);
            return Component.text(legacyMessage);
        }
    }

    public static String translateLegacy(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static boolean isMiniMessage(String message) {
        return message.contains("<") && message.contains(">");
    }

    /* Generado para usar RegExp */
    private static String translateGradient(String message) {
        return message.replaceAll("<#([A-Fa-f0-9]{6})>(.*?)</#([A-Fa-f0-9]{6})>",
                "<gradient:#$1:#$3>$2</gradient>");
    }
}
