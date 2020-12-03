package me.prostedeni.goodcraft.mekanicacore.events.block;

import me.prostedeni.goodcraft.mekanicacore.configFiles.Machines.furnaceConfigData;
import me.prostedeni.goodcraft.mekanicacore.configFiles.Systems.conduitConfigData;
import me.prostedeni.goodcraft.mekanicacore.configFiles.Generators.stirlingConfigData;
import me.prostedeni.goodcraft.mekanicacore.getItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakBlock implements Listener {

    conduitConfigData conduitConfigData = new conduitConfigData();

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        if (e.getBlock().getType().equals(Material.WHITE_WOOL)) {
            if (e.getPlayer().getInventory().getItemInHand().equals(getItem.getItemFromID(002))){
                if (conduitConfigData.existsConduit(e.getBlock().getLocation())) {
                    e.setCancelled(true);
                } else {
                    conduitConfigData.DestroyConduit(e.getBlock().getLocation());
                }
            } else {
                conduitConfigData.DestroyConduit(e.getBlock().getLocation());
            }
        }

        if (e.getBlock().getType().equals(Material.DROPPER)){
            stirlingConfigData.DestroyGenerator(e.getBlock().getLocation());
        }

        if (e.getBlock().getType().equals(Material.FURNACE)){
            furnaceConfigData.DestroyMachine(e.getBlock().getLocation());
        }

    }
    //erases broken conduits/generators

}
