package me.prostedeni.goodcraft.mekanicacore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class guiHandler {

    public static Inventory createGUI(String invName){
        switch(invName){
            case "Stirling":{
                Inventory GUI = Bukkit.createInventory(null, 36, ChatColor.translateAlternateColorCodes('&',"&5&lStirling Generator"));

                ItemStack yellowPane = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
                ItemMeta yellowPaneMeta = yellowPane.getItemMeta();
                yellowPaneMeta.setDisplayName("");
                yellowPane.setItemMeta(yellowPaneMeta);

                GUI.setItem(5, new ItemStack(yellowPane));
                GUI.setItem(13, new ItemStack(yellowPane));
                GUI.setItem(15, new ItemStack(yellowPane));
                GUI.setItem(23, new ItemStack(yellowPane));

                ItemStack grayPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta grayPaneMeta = grayPane.getItemMeta();
                grayPaneMeta.setDisplayName("");
                grayPane.setItemMeta(grayPaneMeta);

                GUI.setItem(10, new ItemStack(grayPane));
                GUI.setItem(18, new ItemStack(grayPane));
                GUI.setItem(20, new ItemStack(grayPane));
                GUI.setItem(28, new ItemStack(grayPane));

                GUI.setItem(8, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                GUI.setItem(17, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                GUI.setItem(26, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                GUI.setItem(35, new ItemStack(Material.RED_STAINED_GLASS_PANE));

                return GUI;
            }
        }
        return null;
    }

}
