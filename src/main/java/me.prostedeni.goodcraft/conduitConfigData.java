package me.prostedeni.goodcraft.mekanicacore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class conduitConfigData {

    private static File conduitsFile;
    private static FileConfiguration fileConfiguration;

    public static void configSetup(){

        if (!Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore").getDataFolder().exists()) {
            Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore").getDataFolder().mkdir();
        }

        File recipesFolder = new File(Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore").getDataFolder(), "DataStorage");
        if(!recipesFolder.exists()) {
            recipesFolder.mkdirs();
        }



        conduitsFile = new File(recipesFolder, "ConduitsData.yml");
        if (!conduitsFile.exists()){
            try {
                conduitsFile.createNewFile();
            } catch (java.io.IOException e){
                //nothing
            }
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(conduitsFile);

    }

    public static FileConfiguration get(){
        return fileConfiguration;
    }

    public static void save(){
        try {
            fileConfiguration.save(conduitsFile);
        } catch (IOException e) {
            //nothing
        }
    }

    public static void SaveConduit(Location location){
        fileConfiguration.set("Data." + String.valueOf(location.getBlockX()*10000*location.getBlockY()*location.getBlockZ() + " " + java.time.LocalDate.now()), location);
        save();
    }

    public static void DestroyConduit(Location location){
        if (fileConfiguration.getConfigurationSection("Data") != null) {
            for (String s : Objects.requireNonNull(fileConfiguration.getConfigurationSection("Data")).getKeys(false)) {
                if (Objects.requireNonNull(fileConfiguration.getLocation("Data." + s)).toString().equals(location.toString())) {
                    fileConfiguration.set("Data." + s, null);
                    save();
                    break;
                }
            }
        }
    }

}
