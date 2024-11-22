package com.survivaldub.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
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
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AnimalProtectionListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return; // El atacante no es un jugador
        if (player.hasPermission("protectionstones.admin")) return; // El jugador es un admin

        Entity target = event.getEntity();
        if (!(target instanceof Mob)) return; // El objetivo no es un Mob

        Location location = event.getEntity().getLocation();
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = container.get(BukkitAdapter.adapt(location.getWorld()));

        if (regionManager != null) {
            ApplicableRegionSet regionSet = regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(location));

            for (ProtectedRegion region : regionSet) {
                StateFlag.State flagState = region.getFlag(FlagHandler.PROTECT_ANIMALS);
                if (flagState == StateFlag.State.ALLOW) {
                    if (!region.getMembers().contains(player.getUniqueId()) && !region.getOwners().contains(player.getUniqueId())) {
                        if (target instanceof Animals) {
                            event.setCancelled(true);
                            String fileMessage = PSExtraFlags.getInstance().getFiles().getMessages().getString("NO-ANIMAL-KILL");
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
}