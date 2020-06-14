package me.prostedeni.goodcraft.mekanicacore.recipes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class recipeLoader {

    public static void addRecipe(String recipeName){
        switch (recipeName){
            case "Conduit" : {
                ItemStack item = new ItemStack(Material.WHITE_WOOL);

                ItemMeta meta = item.getItemMeta();
                ArrayList<String> lores = new ArrayList<>();
                lores.add(ChatColor.translateAlternateColorCodes('&', "&3ID: 001"));
                lores.add(ChatColor.translateAlternateColorCodes('&', "&3Transfers energy from"));
                lores.add(ChatColor.translateAlternateColorCodes('&', "&3point A to point B"));
                meta.setLore(lores);
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lConduit"));
                item.setAmount(6);

                item.setItemMeta(meta);

                NamespacedKey key = new NamespacedKey(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore")), "Conduit");
                ShapedRecipe recipe = new ShapedRecipe(key, item);

                recipe.shape("WWW", "III", "WWW");

                recipe.setIngredient('W', Material.WHITE_WOOL);
                recipe.setIngredient('I', Material.IRON_INGOT);

                Bukkit.addRecipe(recipe);
                break;
            }

            case "Wrench" : {

                ItemStack item = new ItemStack(Material.STICK);

                ItemMeta meta = item.getItemMeta();
                ArrayList<String> lores = new ArrayList<>();
                lores.add(ChatColor.translateAlternateColorCodes('&', "&3ID: 002"));
                lores.add(ChatColor.translateAlternateColorCodes('&', "&3Connect machines by"));
                lores.add(ChatColor.translateAlternateColorCodes('&', "&3clicking on them"));
                meta.setLore(lores);
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lWrench"));

                item.setItemMeta(meta);

                NamespacedKey key = new NamespacedKey(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore")), "Wrench");
                ShapedRecipe recipe = new ShapedRecipe(key, item);

                recipe.shape(" R ", "NSN", " I ");

                recipe.setIngredient('R', Material.REDSTONE);
                recipe.setIngredient('N', Material.GOLD_NUGGET);
                recipe.setIngredient('S', Material.STICK);
                recipe.setIngredient('I', Material.IRON_INGOT);

                Bukkit.addRecipe(recipe);
                break;
            }

            case "Stirling": {
                ItemStack item = new ItemStack(Material.FURNACE);

                ItemMeta meta = item.getItemMeta();
                ArrayList<String> lores = new ArrayList<>();
                lores.add(ChatColor.translateAlternateColorCodes('&', "&3ID: 003"));
                lores.add(ChatColor.translateAlternateColorCodes('&', "&3Accepts any fuel"));
                lores.add(ChatColor.translateAlternateColorCodes('&', "&3generates 20 RF/tick"));
                meta.setLore(lores);
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lStirling Generator"));

                item.setItemMeta(meta);

                NamespacedKey key = new NamespacedKey(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore")), "Stirling");
                ShapedRecipe recipe = new ShapedRecipe(key, item);

                recipe.shape("SSS", "SFS", "GPG");

                ItemStack Gear = new ItemStack(Material.SUNFLOWER);
                ItemMeta meta1 = Gear.getItemMeta();
                ArrayList<String> lores1 = new ArrayList<>();
                lores1.add(ChatColor.translateAlternateColorCodes('&', "&3ID: 004"));
                lores1.add(ChatColor.translateAlternateColorCodes('&', "&3Used in recipes"));
                meta1.setLore(lores1);
                meta1.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lBasic Gear"));
                Gear.setItemMeta(meta1);

                recipe.setIngredient('S', Material.STONE_BRICKS);
                recipe.setIngredient('F', Material.FURNACE);
                recipe.setIngredient('G', (new RecipeChoice.ExactChoice(Gear)));
                recipe.setIngredient('P', Material.PISTON);

                Bukkit.addRecipe(recipe);
                break;
            }

            case "Gear": {
                ItemStack item = new ItemStack(Material.SUNFLOWER);

                ItemMeta meta = item.getItemMeta();
                ArrayList<String> lores = new ArrayList<>();
                lores.add(ChatColor.translateAlternateColorCodes('&', "&3ID: 004"));
                lores.add(ChatColor.translateAlternateColorCodes('&', "&3Used in recipes"));
                meta.setLore(lores);
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&lBasic Gear"));
                item.setAmount(4);

                item.setItemMeta(meta);

                NamespacedKey key = new NamespacedKey(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore")), "Gear");
                ShapedRecipe recipe = new ShapedRecipe(key, item);

                recipe.shape("SBS", "B B", "SBS");

                recipe.setIngredient('S', Material.STICK);
                recipe.setIngredient('B', Material.STONE);

                Bukkit.addRecipe(recipe);
                break;
            }

        }

    }

}
