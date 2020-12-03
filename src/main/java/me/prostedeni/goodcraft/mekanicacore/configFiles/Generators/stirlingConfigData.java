package me.prostedeni.goodcraft.mekanicacore.configFiles.Generators;

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

            fileConfiguration.set("Type", 0);
        }

        public static FileConfiguration get(){
            return fileConfiguration;
        }

        public static void save(){
            new BukkitRunnable(){
                @Override
                public void run(){
                    try {
                        fileConfiguration.save(generatorsFile);

                    } catch (IOException | NullPointerException e) {
                        //nothing
                    }
                }
            }.runTaskAsynchronously(MekanicaCore.getInstance());
        }

        public static void saveSynchronously() throws IOException {
            try {
                fileConfiguration.save(generatorsFile);

            } catch (IOException | NullPointerException e) {
                //nothing
            }
        }

        public static void SaveGenerator(Location location){

            String ID = (location.getBlockX()*location.getBlockY()*location.getBlockZ() + " " + java.time.LocalDate.now());

            setLocation(location, ID);
            fileConfiguration.set("Data." + (ID + ".uuid"), (hologramsUpdater.createArmorStand(location, "&5&lStirling Generator")).toString());
            fileConfiguration.set("Data." + (ID + ".Item"), (new ItemStack(Material.AIR)));
            fileConfiguration.set("Data." + (ID + ".RF"), 0);
            fileConfiguration.set("Data." + (ID + ".IsBurning"), false);
            fileConfiguration.set("Data." + (ID + ".BurnedRF"), 0);
        }

        public static void DestroyGenerator(Location location){
            if (fileConfiguration.getConfigurationSection("Data") != null) {
                for (String s : fileConfiguration.getConfigurationSection("Data").getKeys(false)) {
                    if (Objects.requireNonNull(getLocation(s)).toString().equals(location.toString())) {

                        String uuid = getuuid(s);
                        World world = getLocation(s).getWorld();

                        for (Entity e : world.getEntities()){
                            if (e.getUniqueId().toString().equals(uuid)){
                                e.remove();
                            }
                        }

                        fileConfiguration.set("Data." + s, null);
                        break;
                    }
                }
            }
        }

        public static ArrayList<String> getAllStirlings(){
            try {
                return new ArrayList<>(fileConfiguration.getConfigurationSection("Data").getKeys(false));
            } catch (NullPointerException e){
                //nothing
            }
            return null;
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
        }

        public static String getuuid(String ID){
            return fileConfiguration.getString("Data." + ID + ".uuid");
        }

        public static void setuuid(String uuid, String ID){
            fileConfiguration.set("Data." + ID + ".uuid", uuid);
        }

        public static ItemStack getItem(String ID){
            return fileConfiguration.getItemStack("Data." + ID + ".Item");
        }

        public static void setItem(ItemStack item, String ID){

            if (item == null || item.getType().equals(Material.AIR)){
                fileConfiguration.set("Data." + (ID + ".Item"), (new ItemStack(Material.AIR)));
            } else {
                fileConfiguration.set("Data." + (ID + ".Item"), item);
            }
        }

        public static Integer getAmount(String ID){
            try {
                ItemStack itemStack = fileConfiguration.getItemStack("Data." + ID + ".Item");
                return itemStack.getAmount();
            } catch (NullPointerException err){
                return 1;
            }
        }

        public static void takeOne(String ID){

            int amount;
            ItemStack itemStack = fileConfiguration.getItemStack("Data." + ID + ".Item");

            try {
                amount = itemStack.getAmount();
            } catch (NullPointerException err){
                amount = 1;
            }

            if (amount == 1){
                fileConfiguration.set("Data." + (ID + ".Item"), (new ItemStack(Material.AIR)));
            } else if (amount > 1){
                itemStack.setAmount(amount - 1);
                fileConfiguration.set("Data." + (ID + ".Item"), itemStack);
            }
        }

        public static Integer getRF(String ID){
            return fileConfiguration.getInt("Data." + ID + ".RF");
        }

        public static void setRF(Integer integer, String ID){
            fileConfiguration.set("Data." + ID + ".RF", integer);
        }

        public static void addRF(Integer integer, String ID){
            fileConfiguration.set("Data." + ID + ".RF", (getRF(ID) + integer));
        }

        public static void takeRF(Integer integer, String ID){
            fileConfiguration.set("Data." + ID + ".RF", (getRF(ID) - integer));
        }

        public static Boolean getBurning(String ID){
            return fileConfiguration.getBoolean("Data." + ID + ".IsBurning");
        }

        public static void setBurning(Boolean burning, String ID){
            fileConfiguration.set("Data." + ID + ".IsBurning", burning);
        }

        public static Integer getBurnedRF(String ID){
            return fileConfiguration.getInt("Data." + ID + ".BurnedRF");
        }

        public static void setBurnedRF(Integer RF, String ID){
            fileConfiguration.set("Data." + ID + ".BurnedRF", RF);
        }

        public static void takeBurnedRF(Integer RF, String ID){
            fileConfiguration.set("Data." + ID + ".BurnedRF", (fileConfiguration.getInt("Data." + ID + ".BurnedRF") - RF));
        }
}
