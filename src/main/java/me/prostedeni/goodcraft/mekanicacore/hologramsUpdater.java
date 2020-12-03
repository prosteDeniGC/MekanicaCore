package me.prostedeni.goodcraft.mekanicacore;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.Objects;
import java.util.UUID;

public class hologramsUpdater {

    public static UUID createArmorStand(Location location, String name){

        ArmorStand hologram = (ArmorStand) Objects.requireNonNull(location.getWorld()).spawnEntity(new Location(location.getWorld(), (location.getBlockX()), (location.getBlockY()+20), (location.getBlockZ())), EntityType.ARMOR_STAND);

        hologram.setGravity(false);
        hologram.setVisible(false);
        hologram.setSmall(true);
        hologram.setInvulnerable(true);
        hologram.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
        hologram.setCustomNameVisible(true);

        hologram.teleport(new Location(location.getWorld(), (location.getBlockX()+0.5), (location.getBlockY()), (location.getBlockZ()+0.5)));

        return hologram.getUniqueId();
    }
}
