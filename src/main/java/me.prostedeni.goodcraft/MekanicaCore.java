package me.prostedeni.goodcraft.mekanicacore;

import me.prostedeni.goodcraft.mekanicacore.recipes.recipeLoader;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;


public final class MekanicaCore extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(this, this);

        conduitConfigData.configSetup();
        conduitConfigData.save();

        generatorConfigData.configSetup();
        generatorConfigData.save();

        Bukkit.resetRecipes();
        recipeLoader.addRecipe("Conduit");
        recipeLoader.addRecipe("Wrench");
        recipeLoader.addRecipe("Gear");
        recipeLoader.addRecipe("Stirling");
        try {
                for (String s : generatorConfigData.get().getConfigurationSection("Data").getKeys(false)){

                    Location savedLoc = generatorConfigData.get().getLocation("Data." + s + ".Location");
                    //saves location itself

                    generatorConfigData.DestroyGenerator(generatorConfigData.get().getLocation("Data." + s + ".Location"));
                    //erases useless data (old uuid)

                    generatorConfigData.SaveGenerator(savedLoc);
                    //regenerates new data with the location
                }
            //loads all armorstands (holograms) when the plugin is enabled
            } catch (NullPointerException e){
            //nothing
        }
    }

    @Override
    public void onDisable() {
        try {
            for (String s : generatorConfigData.get().getConfigurationSection("Data").getKeys(false)){
                String uuid = s.substring(s.length() - 36);
                World world = (generatorConfigData.get().getLocation("Data." + s + ".Location")).getWorld();

                for (Entity e : world.getEntities()){
                    if (e.getUniqueId().toString().equals(uuid)){
                        e.remove();
                    }
                }
            }
            //removes all armorstands (holograms) when the plugin is disabled
        } catch (NullPointerException e){
            //nothing
        }
    }

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
                        generatorConfigData.SaveGenerator(e.getBlockPlaced().getLocation());
                        break;
                    }

                }
            }
        } catch (NullPointerException e1){
            //nothing
        }
        //saves placed conduits or generators
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        if (e.getBlock().getType().equals(Material.WHITE_WOOL)) {
            conduitConfigData.DestroyConduit(e.getBlock().getLocation());
        }

        if (e.getBlock().getType().equals(Material.FURNACE)){
            generatorConfigData.DestroyGenerator(e.getBlock().getLocation());
        }

    }
    //erases broken conduits/generators

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if (!(e.getPlayer().isSneaking())){

                ItemStack wrench = new ItemStack(Material.STICK);

                ItemMeta meta = wrench.getItemMeta();
                ArrayList<String> lores = new ArrayList<>();
                lores.add(ChatColor.translateAlternateColorCodes('&', "&3ID: 002"));
                lores.add(ChatColor.translateAlternateColorCodes('&', "&3Connect machines by"));
                lores.add(ChatColor.translateAlternateColorCodes('&', "&3clicking on them"));
                meta.setLore(lores);
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lWrench"));

                wrench.setItemMeta(meta);

                if (e.getPlayer().getInventory().getItemInMainHand() != wrench) {
                    try {
                        for (String s : generatorConfigData.get().getConfigurationSection("Data").getKeys(false)) {
                            if (generatorConfigData.get().getLocation("Data." + s + ".Location").toString().equals((e.getClickedBlock().getLocation()).toString())) {
                                e.setCancelled(true);
                                e.getPlayer().openInventory(guiHandler.createGUI("Stirling"));

                                break;
                            }
                        }
                    } catch (NullPointerException e1){
                        //nothing
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void invMove(InventoryClickEvent e){
        String invTitle = ChatColor.stripColor(e.getView().getTitle());
        if (invTitle.equals("Stirling Generator")){
            if (e.getClickedInventory() != e.getWhoClicked().getInventory()){
                if (e.getSlot() != 19){
                    e.setCancelled(true);
                }
            }
        }
    }

    

}
