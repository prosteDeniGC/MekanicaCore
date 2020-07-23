package me.prostedeni.goodcraft.mekanicacore.configFiles;

import me.prostedeni.goodcraft.mekanicacore.MekanicaCore;
import me.prostedeni.goodcraft.mekanicacore.hologramsUpdater;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.yaml.snakeyaml.events.Event;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class stirlingConfigData {

        private static File generatorsFile;
        private static FileConfiguration fileConfiguration;

        public static void configSetup(){

            if (!Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore").getDataFolder().exists()) {
                Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore").getDataFolder().mkdir();
            }

            File generalDataFolder = new File(Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore").getDataFolder(), "DataStorage");
            if(!generalDataFolder.exists()) {
                generalDataFolder.mkdirs();
            }

            File generatorsDataFolder = new File(generalDataFolder, "Generators");
            if (!generatorsDataFolder.exists()) {
                generatorsDataFolder.mkdirs();
            }


            generatorsFile = new File(generatorsDataFolder, "StirlingData.yml");
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
            //new BukkitRunnable(){
                //@Override
                //public void run(){
                    try {
                        fileConfiguration.save(generatorsFile);

                    } catch (IOException e) {
                        //nothing
                        Bukkit.broadcastMessage("Saving error");
                        e.printStackTrace();
                    }
                //}
            //}.runTaskAsynchronously(MekanicaCore.getInstance());
        }

        public static void SaveGenerator(Location location){

            String ID = (MekanicaCore.getRandomIntegerBetweenRange(2000, 8000)*location.getBlockX()*location.getBlockY()*location.getBlockZ() + " " + java.time.LocalDate.now());

            setLocation(location, ID);
            fileConfiguration.set("Data." + (ID + ".uuid"), (hologramsUpdater.createArmorStand(location, "&5&lStirling Generator")).toString());
            fileConfiguration.set("Data." + (ID + ".Item"), (new ItemStack(Material.AIR)));
            fileConfiguration.set("Data." + (ID + ".RF"), 0);
            fileConfiguration.set("Data." + (ID + ".IsBurning"), false);
            save();
        }

        public static void DestroyGenerator(Location location){
            if (fileConfiguration.getConfigurationSection("Data") != null) {
                for (String s : Objects.requireNonNull(fileConfiguration.getConfigurationSection("Data")).getKeys(false)) {
                    if (Objects.requireNonNull(getLocation(s)).toString().equals(location.toString())) {

                        String uuid = getuuid(s);
                        World world = getLocation(s).getWorld();

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

        public static Location getLocation(String ID){
            Location location = new Location ((Bukkit.getWorld((String) fileConfiguration.get("Data." + ID + ".Location.0"))), fileConfiguration.getInt("Data." + ID + ".Location.1"), fileConfiguration.getInt("Data." + ID + ".Location.2"), fileConfiguration.getInt("Data." + ID + ".Location.3"), 0, 0);
            return location;
        }

        public static void setLocation(Location location, String ID){
            fileConfiguration.set("Data." + (ID + ".Location.0"), location.getWorld().getName());
            fileConfiguration.set("Data." + (ID + ".Location.1"), location.getBlockX());
            fileConfiguration.set("Data." + (ID + ".Location.2"), location.getBlockY());
            fileConfiguration.set("Data." + (ID + ".Location.3"), location.getBlockZ());
            save();
        }

        public static String getuuid(String ID){
            return fileConfiguration.getString("Data." + ID + ".uuid");
        }

        public static void setuuid(String uuid, String ID){
            fileConfiguration.set("Data." + ID + ".uuid", uuid);
            save();
        }

        public static ItemStack getItem(String ID){
            return fileConfiguration.getItemStack("Data." + ID + ".Item");
        }

        public static void setItem(ItemStack item, String ID){

            if (item == null || item.getType().equals(Material.AIR)){
                fileConfiguration.set("Data." + (ID + ".Item"), (new ItemStack(Material.AIR)));
                save();
            } else {
                fileConfiguration.set("Data." + (ID + ".Item"), item);
                save();
            }
        }

        public static Integer getRF(String ID){
            return fileConfiguration.getInt("Data." + ID + ".RF");
        }

        public static void setRF(Integer integer, String ID){
            fileConfiguration.set("Data." + ID + ".RF", integer);
            save();
        }

        public static Boolean getBurning(String ID){
            return fileConfiguration.getBoolean("Data." + ID + ".IsBurning");
        }

        public static void setBurning(Boolean burning, String ID){
            fileConfiguration.set("Data." + ID + ".IsBurning", burning);
            save();
        }
}
