package me.prostedeni.goodcraft.mekanicacore.configFiles.Configuration;

import me.prostedeni.goodcraft.mekanicacore.MekanicaCore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public class MainConfig {

    private static File configFile;
    private static FileConfiguration fileConfiguration;

    public static void configSetup(){

        if (!Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore").getDataFolder().exists()) {
            Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore").getDataFolder().mkdir();
        }

        File generalDataFolder = new File(Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore").getDataFolder(), "Configuration");
        if(!generalDataFolder.exists()) {
            generalDataFolder.mkdirs();
        }

        configFile = new File(generalDataFolder, "ConduitsData.yml");
        if (!configFile.exists()){
            try {
                configFile.createNewFile();
            } catch (java.io.IOException e){
                //nothing
            }
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);

    }

    public static void save(){
        new BukkitRunnable() {
            public void run() {
                try {
                    fileConfiguration.save(configFile);
                } catch (IOException | NullPointerException e) {
                    //nothing
                }
            }
        }.runTaskAsynchronously(MekanicaCore.getInstance());
    }

    public static void saveSynchronously() throws IOException {
        try {
            fileConfiguration.save(configFile);

        } catch (IOException | NullPointerException e) {
            //nothing
        }
    }

    public FileConfiguration get(){
        return fileConfiguration;
    }

}
