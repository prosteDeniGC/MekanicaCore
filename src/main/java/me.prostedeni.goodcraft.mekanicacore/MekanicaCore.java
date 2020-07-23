package me.prostedeni.goodcraft.mekanicacore;

import me.prostedeni.goodcraft.mekanicacore.configFiles.conduitConfigData;
import me.prostedeni.goodcraft.mekanicacore.configFiles.stirlingConfigData;
import me.prostedeni.goodcraft.mekanicacore.recipes.recipeLoader;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.event.inventory.InventoryAction.*;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public final class MekanicaCore extends JavaPlugin implements Listener {

    private static MekanicaCore instance;

    public static MekanicaCore getInstance() {
        return instance;
    }


    public static int getRandomIntegerBetweenRange(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    //method of generating random ints

    @Override
    public void onEnable() {

        instance = this;

        this.getCommand("mget").setExecutor(new getCommand());
        //registers the command from getCommand class, got this from
        //https://www.spigotmc.org/wiki/create-a-simple-command/

        getServer().getPluginManager().registerEvents(this, this);

        conduitConfigData.configSetup();
        conduitConfigData.save();

        stirlingConfigData.configSetup();
        stirlingConfigData.save();

        //these two generate data files if they don't exist

        Bukkit.resetRecipes();
        recipeLoader.addRecipe("Conduit");
        recipeLoader.addRecipe("Wrench");
        recipeLoader.addRecipe("Gear");
        recipeLoader.addRecipe("Stirling");
    }

    @Override
    public void onDisable(){
        conduitConfigData.save();
        stirlingConfigData.save();
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
                        stirlingConfigData.SaveGenerator(e.getBlockPlaced().getLocation());

                        Block block = e.getBlockPlaced();
                        Directional blockData = (Directional) block.getBlockData();
                        blockData.setFacing(BlockFace.UP);
                        block.setBlockData(blockData);

                        //makes Stirlings point up

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

        if (e.getBlock().getType().equals(Material.DROPPER)){
            stirlingConfigData.DestroyGenerator(e.getBlock().getLocation());
        }

    }
    //erases broken conduits/generators

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if (!(e.getPlayer().isSneaking())){

                ItemStack wrench = getItem.getItemFromID(002);

                if (e.getPlayer().getInventory().getItemInMainHand() != wrench) {
                    try {
                        for (String s : stirlingConfigData.get().getConfigurationSection("Data.").getKeys(false)) {

                            if (stirlingConfigData.getLocation(s).equals((e.getClickedBlock().getLocation()))) {

                                e.setCancelled(true);
                                e.getPlayer().openInventory(Objects.requireNonNull(guiHandler.createGUI("Stirling", s)));

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


    @EventHandler(priority = EventPriority.HIGH)
    public void invMove(InventoryClickEvent e){
        if (!(e.getWhoClicked() instanceof Player)){
            return;
        }
        String invTitle = ChatColor.stripColor(e.getView().getTitle());
        if (invTitle.equals("Stirling Generator")){
            if (!(e.getWhoClicked().getInventory().equals(e.getClickedInventory()))){
                if (e.getRawSlot() == 19){

                    stirlingConfigData.setItem(e.getClickedInventory().getItem(19), getItem.getIDpane(e.getClickedInventory().getItem(0)));
                    stirlingConfigData.setItem(e.getClickedInventory().getItem(19), getItem.getIDpane(e.getClickedInventory().getItem(0)));

                    Bukkit.broadcastMessage(getItem.getIDpane(e.getClickedInventory().getItem(0)));

                } else {
                    e.setCancelled(true);
                }
            }
        }
    }
    //this event handles items in generators and machines


}
