package me.prostedeni.goodcraft.mekanicacore.gui.machines;

import me.prostedeni.goodcraft.mekanicacore.MekanicaCore;
import me.prostedeni.goodcraft.mekanicacore.configFiles.Generators.stirlingConfigData;
import me.prostedeni.goodcraft.mekanicacore.configFiles.Machines.furnaceConfigData;
import me.prostedeni.goodcraft.mekanicacore.getItem;
import me.prostedeni.goodcraft.mekanicacore.gui.RFmeter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Furnace {

    public static Inventory createGUI(String ID){
        Inventory GUI = Bukkit.createInventory(null, 45, ChatColor.translateAlternateColorCodes('&',"&6&lElectric Furnace"));

        ItemStack lightGrayPane = getItem.getItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ");

        for (int i = 0; i < 45; i++){
            GUI.setItem(i, lightGrayPane);
        }

        ItemStack yellowPane = getItem.getItemStack(Material.ORANGE_STAINED_GLASS_PANE, "Status");

        GUI.setItem(2, yellowPane);
        GUI.setItem(4, yellowPane);
        GUI.setItem(12, yellowPane);


        ItemStack grayPane = getItem.getItemStack(Material.GRAY_STAINED_GLASS_PANE, "Input");

        GUI.setItem(19, grayPane);
        GUI.setItem(27, grayPane);
        GUI.setItem(29, grayPane);
        GUI.setItem(37, grayPane);

        ItemStack limePane = getItem.getItemStack(Material.LIME_STAINED_GLASS_PANE, "Output");

        GUI.setItem(23, limePane);
        GUI.setItem(31, limePane);
        GUI.setItem(33, limePane);
        GUI.setItem(41, limePane);

        ItemStack IDpane = getItem.getItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ID);

        GUI.setItem(0, IDpane);

        GUI.setItem(28, new ItemStack(Material.AIR));
        GUI.setItem(32, new ItemStack(Material.AIR));

        MekanicaCore.transactionInventory.put(ID, false);

        updateFurnaceGUI(GUI, ID);

        return GUI;
    }

    public static void updateFurnaceGUI(Inventory GUI, String ID){

        ItemStack lightGrayPane = getItem.getItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ");

        new BukkitRunnable(){
            @Override
            public void run(){

                if (MekanicaCore.transactionInventory.containsKey(ID)) {
                    if (!(MekanicaCore.transactionInventory.get(ID))) {

                        GUI.setItem(28, furnaceConfigData.getItemPre(ID));
                        //sets the item/fuel in the correct slot

                        GUI.setItem(32, furnaceConfigData.getItemPost(ID));
                        //sets the item/fuel in the correct slot

                        RFmeter.diplayRF(GUI, furnaceConfigData.getRF(ID));
                        //gets RF from config and updates the meter on the right

                        Boolean burning = furnaceConfigData.getBurning(ID);
                        if (burning) {

                            ItemStack blazePowder = getItem.getItemStack(Material.BLAZE_POWDER, "&3&lWorking", "&6Remaining RF: &b" + String.valueOf(furnaceConfigData.getBurnedRF(ID)), "&6Time left: &b" + String.valueOf((1000 - furnaceConfigData.getBurnedRF(ID))/125) + "s");

                            GUI.setItem(3, blazePowder);
                        } else {
                            GUI.setItem(3, lightGrayPane);
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
