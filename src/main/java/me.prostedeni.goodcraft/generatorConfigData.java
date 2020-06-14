package me.prostedeni.goodcraft.mekanicacore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class generatorConfigData {

        private static File generatorsFile;
        private static FileConfiguration fileConfiguration;

        public static void configSetup(){

            if (!Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore").getDataFolder().exists()) {
                Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore").getDataFolder().mkdir();
            }

            File recipesFolder = new File(Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore").getDataFolder(), "DataStorage");
            if(!recipesFolder.exists()) {
                recipesFolder.mkdirs();
            }



            generatorsFile = new File(recipesFolder, "GeneratorsData.yml");
            if (!generatorsFile.exists()){
                try {
                    generatorsFile.createNewFile();
                } catch (java.io.IOException e){
                    //nothing
                }
            }
            fileConfiguration = YamlConfiguration.loadConfiguration(generatorsFile);

        }

        public static FileConfiguration get(){
            return fileConfiguration;
        }

        public static void save(){
            try {
                fileConfiguration.save(generatorsFile);
            } catch (IOException e) {
                //nothing
            }
        }

        public static void SaveGenerator(Location location){
            fileConfiguration.set("Data." + String.valueOf((location.getBlockX()*10000*location.getBlockY()*location.getBlockZ() + " " + java.time.LocalDate.now() + " " + (hologramsUpdater.createArmorStand(location, "&5&lStirling Generator")) + ".Location").toString()), location);
            save();
        }

        public static void DestroyGenerator(Location location){
            if (fileConfiguration.getConfigurationSection("Data") != null) {
                for (String s : Objects.requireNonNull(fileConfiguration.getConfigurationSection("Data")).getKeys(false)) {
                    if (Objects.requireNonNull(fileConfiguration.getLocation("Data." + s + ".Location")).toString().equals(location.toString())) {

                        String uuid = s.substring(s.length() - 36);
                        World world = (generatorConfigData.get().getLocation("Data." + s + ".Location")).getWorld();

                        for (Entity e : world.getEntities()){
                            if (e.getUniqueId().toString().equals(uuid)){
                                e.remove();
                            }
                        }

                        fileConfiguration.set("Data." + s, null);
                        save();
                        break;
                    }
                }
            }
        }
}
