package me.prostedeni.goodcraft.mekanicacore.events.block;

import me.prostedeni.goodcraft.mekanicacore.configFiles.Machines.furnaceConfigData;
import me.prostedeni.goodcraft.mekanicacore.configFiles.Systems.conduitConfigData;
import me.prostedeni.goodcraft.mekanicacore.configFiles.Generators.stirlingConfigData;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class PlaceBlock implements Listener {

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e){
        try {
            if (e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
                ItemMeta meta = e.getPlayer().getInventory().getItemInMainHand().getItemMeta();
                String lore = meta.getLore().get(0);
                String lorePlain = ChatColor.stripColor(lore);
                String lorePlainWithoutID = lorePlain.replaceFirst("ID: ", "");

                switch (lorePlainWithoutID) {
                    case "001": {
                        conduitConfigData.SaveConduit(e.getBlockPlaced().getLocation());
                        break;
                    }
                    case "003": {
                        stirlingConfigData.SaveGenerator(e.getBlockPlaced().getLocation());

                        Block block = e.getBlockPlaced();
                        Directional blockData = (Directional) block.getBlockData();
                        blockData.setFacing(BlockFace.UP);
                        block.setBlockData(blockData);

                        //makes Stirlings point up

                        break;
                    }
                    case "005": {
                        furnaceConfigData.SaveMachine(e.getBlockPlaced().getLocation());
                        break;
                    }

                }
            }
        } catch (NullPointerException e1){
            //nothing
        }
        //saves placed conduits or generators
    }

}
