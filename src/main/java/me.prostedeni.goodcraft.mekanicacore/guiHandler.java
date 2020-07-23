package me.prostedeni.goodcraft.mekanicacore;

import me.prostedeni.goodcraft.mekanicacore.configFiles.stirlingConfigData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.ChatColor.*;

public class guiHandler {

    public static Inventory createGUI(String invName, String ID){
        switch(invName){
            case "Stirling":{
                Inventory GUI = Bukkit.createInventory(null, 36, ChatColor.translateAlternateColorCodes('&',"&5&lStirling Generator"));

                ItemStack yellowPane = getItem.getItemStack(Material.ORANGE_STAINED_GLASS_PANE, "Status");

                GUI.setItem(5, yellowPane);
                GUI.setItem(13, yellowPane);
                GUI.setItem(15, yellowPane);
                GUI.setItem(23, yellowPane);

                ItemStack grayPane = getItem.getItemStack(Material.GRAY_STAINED_GLASS_PANE, "Fuel");

                GUI.setItem(10, grayPane);
                GUI.setItem(18, grayPane);
                GUI.setItem(20, grayPane);
                GUI.setItem(28, grayPane);

                ItemStack lightGrayPane = getItem.getItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ");

                ItemStack IDpane = getItem.getItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ID);

                GUI.setItem(0, IDpane);

                for (int i = 0; i < 36; i++){
                    if (GUI.getItem(i) == null){
                        if (i != 19 && i != 14 && i != 8 && i != 17 && i != 26 && i != 35) {
                            GUI.setItem(i, lightGrayPane);
                        }
                    }
                }

                updateStirlingGUI(GUI, ID);

                return GUI;
            }
        }
        return null;
    }

    public static void updateStirlingGUI(Inventory GUI, String ID){

        ItemStack greenPane = getItem.getItemStack(Material.LIME_STAINED_GLASS_PANE, " ");

        ItemStack redPane = getItem.getItemStack(Material.RED_STAINED_GLASS_PANE, " ");

        ItemStack blazePowder = getItem.getItemStack(Material.BLAZE_POWDER, "&r&3&lWorking");

        ItemStack lightGrayPane = getItem.getItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ");

        ItemStack enchantedGreenPane = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        enchantedGreenPane.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        ItemMeta enchantedGreenPaneMeta = enchantedGreenPane.getItemMeta();
        enchantedGreenPaneMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        new BukkitRunnable(){
            @Override
            public void run(){

                GUI.setItem(19, stirlingConfigData.getItem(ID));
                //sets the item/fuel in the correct slot

                int RF = stirlingConfigData.getRF(ID);
                //gets RF from config

                if (RF > 0) {

                    enchantedGreenPaneMeta.setDisplayName(translateAlternateColorCodes('&', "&3&lRF: &r&l") + stirlingConfigData.getRF(ID));
                    enchantedGreenPane.setItemMeta(enchantedGreenPaneMeta);

                    if (RF <= 75000) {
                        GUI.setItem(8, redPane);

                        if (RF <= 50000) {
                            GUI.setItem(17, redPane);

                            if (RF <= 25000) {
                                GUI.setItem(26, redPane);
                                GUI.setItem(35, enchantedGreenPane);

                            } else if (RF > 25000) {
                                GUI.setItem(26, enchantedGreenPane);
                                GUI.setItem(35, greenPane);
                            }

                        } else if (RF > 50000) {

                            GUI.setItem(17, enchantedGreenPane);
                            GUI.setItem(26, greenPane);
                            GUI.setItem(35, greenPane);
                        }
                    } else if (RF > 75000) {

                        GUI.setItem(8, enchantedGreenPane);
                        GUI.setItem(17, greenPane);
                        GUI.setItem(26, greenPane);
                        GUI.setItem(35, greenPane);
                    }
                } else {
                    GUI.setItem(8, redPane);
                    GUI.setItem(17, redPane);
                    GUI.setItem(26, redPane);
                    GUI.setItem(35, redPane);
                }
                //this function sets the RF meter on the right side of the UI

                Boolean burning = stirlingConfigData.getBurning(ID);
                if (burning){
                    GUI.setItem(14, blazePowder);
                } else {
                    GUI.setItem(14, lightGrayPane);
                }
                //this gets the status of the generator



            }
        }.runTaskTimerAsynchronously(MekanicaCore.getInstance(), 0, 100);

    }

}
