package me.prostedeni.goodcraft.mekanicacore.gui.generators;

import me.prostedeni.goodcraft.mekanicacore.MekanicaCore;
import me.prostedeni.goodcraft.mekanicacore.configFiles.Generators.stirlingConfigData;
import me.prostedeni.goodcraft.mekanicacore.getItem;
import me.prostedeni.goodcraft.mekanicacore.gui.RFmeter;
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

public class Stirling {

    public static Inventory createGUI(String ID){
        Inventory GUI = Bukkit.createInventory(null, 36, ChatColor.translateAlternateColorCodes('&',"&5&lStirling Generator"));

        ItemStack yellowPane = getItem.getItemStack(Material.ORANGE_STAINED_GLASS_PANE, "Status");

        GUI.setItem(4, yellowPane);
        GUI.setItem(5, yellowPane);
        GUI.setItem(6, yellowPane);
        GUI.setItem(13, yellowPane);
        GUI.setItem(15, yellowPane);
        GUI.setItem(22, yellowPane);
        GUI.setItem(23, yellowPane);
        GUI.setItem(24, yellowPane);

        ItemStack grayPane = getItem.getItemStack(Material.GRAY_STAINED_GLASS_PANE, "Fuel");

        GUI.setItem(9, grayPane);
        GUI.setItem(10, grayPane);
        GUI.setItem(11, grayPane);
        GUI.setItem(18, grayPane);
        GUI.setItem(20, grayPane);
        GUI.setItem(27, grayPane);
        GUI.setItem(28, grayPane);
        GUI.setItem(29, grayPane);

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

        MekanicaCore.transactionInventory.put(ID, false);

        updateStirlingGUI(GUI, ID);

        return GUI;
    }


    public static void updateStirlingGUI(Inventory GUI, String ID){

        ItemStack lightGrayPane = getItem.getItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ");

        new BukkitRunnable(){
            @Override
            public void run(){

                if (MekanicaCore.transactionInventory.containsKey(ID)) {
                    if (!(MekanicaCore.transactionInventory.get(ID))) {

                        GUI.setItem(19, stirlingConfigData.getItem(ID));
                        //sets the item/fuel in the correct slot

                        RFmeter.diplayRF(GUI, stirlingConfigData.getRF(ID));
                        //gets RF from config and updates the meter on the right

                        Boolean burning = stirlingConfigData.getBurning(ID);
                        if (burning) {

                            ItemStack blazePowder = getItem.getItemStack(Material.BLAZE_POWDER, "&3&lWorking", "&6Remaining RF: &b" + String.valueOf(stirlingConfigData.getBurnedRF(ID)), "&6Time left: &b" + String.valueOf(stirlingConfigData.getBurnedRF(ID)/200) + "s");

                            GUI.setItem(14, blazePowder);
                        } else {
                            GUI.setItem(14, lightGrayPane);
                        }
                        //updates the flame
                    }
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(MekanicaCore.getInstance(), 0, 20);

    }

}
