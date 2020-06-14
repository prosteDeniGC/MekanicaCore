package me.prostedeni.goodcraft.mekanicacore;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.UUID;

public class hologramsUpdater {

    public static UUID createArmorStand(Location location, String name){

        Location loc1 = new Location(location.getWorld(), (location.getBlockX()+0.5), (location.getBlockY()-0.75), (location.getBlockZ()+0.5));

        ArmorStand hologram = (ArmorStand) location.getWorld().spawnEntity(loc1, EntityType.ARMOR_STAND);

        hologram.setVisible(false);
        hologram.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
        hologram.setCustomNameVisible(true);
        hologram.setSmall(false);
        hologram.setInvulnerable(true);
        hologram.setGravity(false);

        return hologram.getUniqueId();
    }
}
