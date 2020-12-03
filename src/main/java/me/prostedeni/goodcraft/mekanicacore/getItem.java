package me.prostedeni.goodcraft.mekanicacore;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Iterator;

import static org.bukkit.ChatColor.*;

public class getItem {

    public static ItemStack getItemFromID(int ID){
        switch (ID){
            case 001: {
                return getItemStack(Material.WHITE_WOOL,"&6&lConduit", "&3ID: 001", "&3Transfers energy from", "&3point A to point B");
            }

            case 002 : {
                return getItemStack(Material.STICK, "&c&lWrench", "&3ID: 002", "&3Connect machines by", "&3clicking on them");
            }

            case 003 : {
                return getItemStack(Material.DROPPER, "&5&lStirling Generator", "&3ID: 003", "&3Accepts any fuel", "&3generates 200 RF/second");
            }

            case 004 : {
                return getItemStack(Material.SUNFLOWER, "&f&lBasic Gear", "&3ID: 004", "&3Used in recipes");
            }

            case 005 : {
                return getItemStack(Material.FURNACE, "&6&lElectric Furnace", "&3ID: 005", "&3Uses electricity to cook", "&3requires 1000 RF/item");
            }

            default: {
                return getItemStack(Material.DIRT, "&f&lRickRoll", "&3ID: 999", "&3Never gonna give you up", "&3Never gonna let you down", "&3Never gonna run around and desert you", "&3Never gonna make you cry");
            }
        }
    }

    public static ItemStack getItemStack(Material material, String name){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getItemStack(Material material, String name, String lore1){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lores = new ArrayList<>();
        lores.add(translateAlternateColorCodes('&', lore1));
        meta.setDisplayName(translateAlternateColorCodes('&', name));
        meta.setLore(lores);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getItemStack(Material material, String name, String lore1, String lore2){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lores = new ArrayList<>();
        lores.add(translateAlternateColorCodes('&', lore1));
        lores.add(translateAlternateColorCodes('&', lore2));
        meta.setDisplayName(translateAlternateColorCodes('&', name));
        meta.setLore(lores);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getItemStack(Material material, String name, String lore1, String lore2, String lore3){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lores = new ArrayList<>();
        lores.add(translateAlternateColorCodes('&', lore1));
        lores.add(translateAlternateColorCodes('&', lore2));
        lores.add(translateAlternateColorCodes('&', lore3));
        meta.setDisplayName(translateAlternateColorCodes('&', name));
        meta.setLore(lores);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getItemStack(Material material, String name, String lore1, String lore2, String lore3, String lore4){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lores = new ArrayList<>();
        lores.add(translateAlternateColorCodes('&', lore1));
        lores.add(translateAlternateColorCodes('&', lore2));
        lores.add(translateAlternateColorCodes('&', lore3));
        lores.add(translateAlternateColorCodes('&', lore4));
        meta.setDisplayName(translateAlternateColorCodes('&', name));
        meta.setLore(lores);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getItemStack(Material material, String name, String lore1, String lore2, String lore3, String lore4, String lore5){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lores = new ArrayList<>();
        lores.add(translateAlternateColorCodes('&', lore1));
        lores.add(translateAlternateColorCodes('&', lore2));
        lores.add(translateAlternateColorCodes('&', lore3));
        lores.add(translateAlternateColorCodes('&', lore4));
        lores.add(translateAlternateColorCodes('&', lore5));
        meta.setDisplayName(translateAlternateColorCodes('&', name));
        meta.setLore(lores);
        item.setItemMeta(meta);

        return item;
    }

    public static String getIDpane(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        return meta.getDisplayName();
    }

    public static ItemStack getBurnable(ItemStack itemStack){
        ItemStack result = new ItemStack(Material.AIR);
        Iterator<Recipe> iter = Bukkit.recipeIterator();
        while (iter.hasNext()) {
            Recipe recipe = iter.next();
            if (!(recipe instanceof FurnaceRecipe)) continue;
            if (((FurnaceRecipe) recipe).getInput().getType() != itemStack.getType()) continue;
            result = recipe.getResult();
            break;
        }
        return result;
    }

}
