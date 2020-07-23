package me.prostedeni.goodcraft.mekanicacore.recipes;

import me.prostedeni.goodcraft.mekanicacore.getItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;


public class recipeLoader {

    public static void addRecipe(String recipeName){
        switch (recipeName){
            case "Conduit" : {
                //ID 001

                ItemStack item = getItem.getItemFromID(001);
                item.setAmount(6);

                NamespacedKey key = new NamespacedKey(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore")), "Conduit");
                ShapedRecipe recipe = new ShapedRecipe(key, item);

                recipe.shape("WWW", "III", "WWW");

                recipe.setIngredient('W', Material.WHITE_WOOL);
                recipe.setIngredient('I', Material.IRON_INGOT);

                Bukkit.addRecipe(recipe);
                break;
            }
            //conduits distribute electricity across system

            case "Wrench" : {
                //ID 002

                ItemStack item = getItem.getItemFromID(002);

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
            //wrenches are used to rotate and fast-break things

            case "Stirling": {
                //ID 003

                ItemStack item = getItem.getItemFromID(003);

                NamespacedKey key = new NamespacedKey(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore")), "Stirling");
                ShapedRecipe recipe = new ShapedRecipe(key, item);

                recipe.shape("SSS", "SFS", "GPG");

                ItemStack Gear = getItem.getItemFromID(004);

                recipe.setIngredient('S', Material.STONE_BRICKS);
                recipe.setIngredient('F', Material.DROPPER);
                recipe.setIngredient('G', (new RecipeChoice.ExactChoice(Gear)));
                recipe.setIngredient('P', Material.PISTON);

                Bukkit.addRecipe(recipe);
                break;
            }
            //stirling generators are essential and easy to make generators of electricity

            case "Gear": {
                //ID 004

                ItemStack item = getItem.getItemFromID(004);
                item.setAmount(4);

                NamespacedKey key = new NamespacedKey(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore")), "Gear");
                ShapedRecipe recipe = new ShapedRecipe(key, item);

                recipe.shape("SBS", "B B", "SBS");

                recipe.setIngredient('S', Material.STICK);
                recipe.setIngredient('B', Material.STONE);

                Bukkit.addRecipe(recipe);
                break;
            }
            //gears are used in crafting recipes

        }

    }

}
