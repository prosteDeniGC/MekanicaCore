package me.prostedeni.goodcraft.mekanicacore;

import me.prostedeni.goodcraft.mekanicacore.calculations.BurnFuel;
import me.prostedeni.goodcraft.mekanicacore.calculations.PassRF;
import me.prostedeni.goodcraft.mekanicacore.calculations.UseEnergy;
import me.prostedeni.goodcraft.mekanicacore.configFiles.Configuration.JarReloaderConfig;
import me.prostedeni.goodcraft.mekanicacore.configFiles.Machines.furnaceConfigData;
import me.prostedeni.goodcraft.mekanicacore.configFiles.Systems.conduitConfigData;
import me.prostedeni.goodcraft.mekanicacore.configFiles.Generators.stirlingConfigData;
import me.prostedeni.goodcraft.mekanicacore.events.PlayerInteract;
import me.prostedeni.goodcraft.mekanicacore.events.block.BreakBlock;
import me.prostedeni.goodcraft.mekanicacore.events.block.PlaceBlock;
import me.prostedeni.goodcraft.mekanicacore.events.inventory.InventoryClick;
import me.prostedeni.goodcraft.mekanicacore.events.inventory.InventoryClose;
import me.prostedeni.goodcraft.mekanicacore.recipes.recipeLoader;
import org.bukkit.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

public final class MekanicaCore extends JavaPlugin implements Listener {

    private static MekanicaCore instance;

    public static MekanicaCore getInstance() {
        return instance;
    }

    public static HashMap<String, Boolean> transactionInventory = new HashMap<>();
    //HashMap for storing ID of inventory, if its being saved
    //0 == not being saved
    //1 == being saved

    @Override
    public void onEnable() {

        instance = this;

        this.getCommand("mget").setExecutor(new getCommand());
        //registers the command from getCommand class, got this from
        //https://www.spigotmc.org/wiki/create-a-simple-command/

        getServer().getPluginManager().registerEvents(new PlaceBlock(), this);
        getServer().getPluginManager().registerEvents(new BreakBlock(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new InventoryClose(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        //registering Listeners

        conduitConfigData.configSetup();
        conduitConfigData.save();

        stirlingConfigData.configSetup();
        stirlingConfigData.save();

        furnaceConfigData.configSetup();
        furnaceConfigData.save();

        JarReloaderConfig.configSetup();
        JarReloaderConfig.save();
        //these generate data files if they don't exist

        Bukkit.resetRecipes();
        recipeLoader loader = new recipeLoader();
        loader.addRecipe("Conduit");
        loader.addRecipe("Wrench");
        loader.addRecipe("Gear");
        loader.addRecipe("Stirling");
        //this loads recipes. will rework later, probably

        BurnFuel burnFuel = new BurnFuel();
        UseEnergy useEnergy = new UseEnergy();
        PassRF passRF = new PassRF();
        new BukkitRunnable(){

            int counter = 0;

            @Override
            public void run() {

                passRF.moveRF();

                if (counter >= 5) {
                    burnFuel.burn();
                    useEnergy.use();

                    counter = 0;

                    stirlingConfigData.save();
                    furnaceConfigData.save();
                    conduitConfigData.save();
                }
                counter++;
            }
        }.runTaskTimerAsynchronously(this, 20, 10);

        FileWatch fileWatch = new FileWatch();
        try {
            fileWatch.InitiateCheck();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable(){
        try {
            conduitConfigData.saveSynchronously();
            stirlingConfigData.saveSynchronously();
            furnaceConfigData.saveSynchronously();
            JarReloaderConfig.saveSynchronously();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}