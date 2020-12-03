package me.prostedeni.goodcraft.mekanicacore.gui;

import me.prostedeni.goodcraft.mekanicacore.configFiles.Generators.stirlingConfigData;
import me.prostedeni.goodcraft.mekanicacore.getItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class RFmeter {

    static ItemStack greenPane = getItem.getItemStack(Material.LIME_STAINED_GLASS_PANE, " ");

    static ItemStack redPane = getItem.getItemStack(Material.RED_STAINED_GLASS_PANE, " ");

    public static void diplayRF(Inventory GUI, Integer RF){
        ItemStack enchantedGreenPane = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        enchantedGreenPane.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        ItemMeta enchantedGreenPaneMeta = enchantedGreenPane.getItemMeta();
        enchantedGreenPaneMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        if (RF > 0) {

            enchantedGreenPaneMeta.setDisplayName(translateAlternateColorCodes('&', "&3&lRF: &r&l") + RF.toString());
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
    }

}
