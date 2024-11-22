package com.survivaldub.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.survivaldub.PSExtraFlags;
import com.survivaldub.handlers.FlagHandler;
import com.survivaldub.utils.ColorUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Location to = event.getTo();

        if (player.hasPermission("protectionstones.admin")) return; // El jugador es un admin
        if (to == null) return;

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(to.getWorld()));

        if (regions != null) {
            BlockVector3 vector = BukkitAdapter.asBlockVector(to);
            ApplicableRegionSet regionSet = regions.getApplicableRegions(vector);

            for (ProtectedRegion region : regionSet) {
                StateFlag.State flagState = region.getFlag(FlagHandler.PREVENT_TELEPORT);
                if (flagState == StateFlag.State.ALLOW) {
                    if (!region.getMembers().contains(player.getUniqueId()) && !region.getOwners().contains(player.getUniqueId())) {
                        event.setCancelled(true);
                        String fileMessage = PSExtraFlags.getInstance().getFiles().getMessages().getString("NO-TELEPORT");
                        Component message = ColorUtils.translate(fileMessage);
                        Audience audience = (Audience) player;
                        audience.sendMessage(message);
                        return;
                    }
                }
            }

        }
    }
}