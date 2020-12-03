package me.prostedeni.goodcraft.mekanicacore.configFiles.Systems;

import me.prostedeni.goodcraft.mekanicacore.MekanicaCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class conduitConfigData {

    private static File conduitsFile;
    private static FileConfiguration fileConfiguration;

    public static void configSetup(){

        if (!Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore").getDataFolder().exists()) {
            Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore").getDataFolder().mkdir();
        }

        File generalDataFolder = new File(Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore").getDataFolder(), "DataStorage");
        if(!generalDataFolder.exists()) {
            generalDataFolder.mkdirs();
        }

        File Other = new File(generalDataFolder, "Systems");
        if(!Other.exists()) {
            Other.mkdirs();
        }

        conduitsFile = new File(Other, "ConduitsData.yml");
        if (!conduitsFile.exists()){
            try {
                conduitsFile.createNewFile();
            } catch (java.io.IOException e){
                //nothing
            }
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(conduitsFile);

    }

    public FileConfiguration get(){
        return fileConfiguration;
    }

    public static void save(){
        new BukkitRunnable() {
            public void run() {
                try {
                    fileConfiguration.save(conduitsFile);
                } catch (IOException | NullPointerException e) {
                    //nothing
                }
            }
        }.runTaskAsynchronously(MekanicaCore.getInstance());
    }

    public static void saveSynchronously() throws IOException {
        try {
            fileConfiguration.save(conduitsFile);

        } catch (IOException | NullPointerException e) {
            //nothing
        }
    }

    public static void SaveConduit(Location loc){
        fileConfiguration.set(loc.getWorld().getName() + "." + loc.getBlockX()+"/"+loc.getBlockY()+"/"+loc.getBlockZ(), 0);
        save();
    }

    public static void DestroyConduit(Location loc){
        //if (fileConfiguration.getConfigurationSection(loc.getWorld().getName() + "." + loc.getBlockX()+"/"+loc.getBlockY()+"/"+loc.getBlockZ()) != null){
            fileConfiguration.set(loc.getWorld().getName() + "." + loc.getBlockX()+"/"+loc.getBlockY()+"/"+loc.getBlockZ(), null);
        //}
    }

    public boolean existsConduit(Location loc){
        try {
            if (fileConfiguration.get(loc.getWorld().getName() + "." + loc.getBlockX()+"/"+loc.getBlockY()+"/"+loc.getBlockZ()) == null){
                return false;
            } else {
                return true;
            }
        } catch (NullPointerException e){
            return false;
        }
    }

    public int getRF(Location loc){
        return fileConfiguration.getInt(loc.getWorld().getName() + "." + loc.getBlockX()+"/"+loc.getBlockY()+"/"+loc.getBlockZ());
    }

    public void setRF(Location loc, int RF){
        fileConfiguration.set(loc.getWorld().getName() + "." + loc.getBlockX()+"/"+loc.getBlockY()+"/"+loc.getBlockZ(), RF);
    }

    public void addRF(Location loc, int RF){
        fileConfiguration.set(loc.getWorld().getName() + "." + loc.getBlockX()+"/"+loc.getBlockY()+"/"+loc.getBlockZ(), RF + getRF(loc));
    }

    public void takeRF(Location loc, int RF){
        fileConfiguration.set(loc.getWorld().getName() + "." + loc.getBlockX()+"/"+loc.getBlockY()+"/"+loc.getBlockZ(), getRF(loc) - RF);
    }

    public Location getLoc(String s, World world){
        String[] parts = s.split("/");
        return new Location(world, Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    public List<Location> getAllConduits(World world){
        List<Location> locations = new ArrayList<>();
        for (String location : fileConfiguration.getConfigurationSection(world.getName()).getKeys(false)){
            locations.add(getLoc(location, world));
        }
        return locations;
    }

    public List<Location> getAround(Location loc){
        List<Location> list = new ArrayList<>();
        list.add(loc.clone().add(0, 1, 0));
        list.add(loc.clone().add(0, -1, 0));
        list.add(loc.clone().add(0, 0, 1));
        list.add(loc.clone().add(1, 0, 0));
        list.add(loc.clone().add(0, 0, -1));
        list.add(loc.clone().add(-1, 0, 0));
        return list;
    }

    public List<Location> getAroundConduit(Location loc){
        List<Location> finalList = new ArrayList<>();

        List<Location> compareList = new ArrayList<>(getAround(loc));
        for (Location location : compareList) {
            if (existsConduit(location)) {
                finalList.add(location);
            }
        }

        return finalList;
    }

    /*
    public List<Location> getConduitsAround(Location loc){
        List<Location> list = new ArrayList<>();
        for (Location l : getAround(loc)){
            if (fileConfiguration.getConfigurationSection(loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ()) != null) {
                list.add(l);
            }
        }
        return list;
    }

    public List<String> getConduits(Location loc){
        List<String> list = new ArrayList<>();
        add(loc, loc, list);
        return list;
    }


    private void add(Location loc, Location start, List<String> c){
        for(Location l : getAround(loc)){
            if(!c.contains(loc.getBlockX()+"/"+loc.getBlockY()+"/"+loc.getBlockZ())) {
                if (fileConfiguration.getConfigurationSection(loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ()) != null) {
                    if (loc.distance(start) <= 75) {
                        c.add(loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ());
                        add(l, start, c);
                    }
                }
            }
        }
    }
    */

}
