package me.prostedeni.goodcraft.mekanicacore;

import me.prostedeni.goodcraft.mekanicacore.configFiles.Configuration.MainConfig;
import org.bukkit.Bukkit;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileWatch {

    private static boolean JarReloadCheck;
    private static String JarName;
    private static Date JarDate;

    private static Date newJarDate;

    MainConfig mainConfig = new MainConfig();

    public void InitiateCheck() throws IOException {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSearching for PlugMan"));
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlugMan")){
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aFound PlugMan"));

            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        JarReloadCheck();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskTimerAsynchronously(MekanicaCore.getInstance(), 100, 200);
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aDid not find plugman"));
        }
    }

    private void JarReloadCheck() throws IOException {
        if (mainConfig.get().get("JarReloadCheck") == null){
            mainConfig.get().set("JarReloadCheck", true);
            //gets #1
            JarReloadCheck = true;

            JarName();
        } else {
            //gets #1
            JarReloadCheck = mainConfig.get().getBoolean("JarReloadCheck");

            if (JarReloadCheck){

                JarName();
            }
        }
    }

    private void JarName() throws IOException {
        if (mainConfig.get().get("JarName") == null){
            mainConfig.get().set("JarName", "MekanicaCore-1.0.jar");
            //gets #2
            JarName = "MekanicaCore-1.0.jar";

            JarDate();
        } else {
            //gets #2
            JarName = mainConfig.get().getString("JarName");

            JarDate();
        }
    }

    private void JarDate() throws IOException {
        if (mainConfig.get().get("JarDate") == null){
            CheckSize(true);
        } else {
            CheckSize(false);
        }
    }

    private void CheckSize(boolean FirstCheck) throws IOException {

        if (FirstCheck) {
            JarFile jf = new JarFile(new File(Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore").getDataFolder().getParentFile() + "/" + JarName));
            Enumeration e = jf.entries();

            while (e.hasMoreElements()) {
                JarEntry je = (JarEntry) e.nextElement();

                Date firstDate = new Date(je.getLastModifiedTime().toMillis());

                if (newJarDate == null){
                    newJarDate = firstDate;
                }
                if (firstDate.compareTo(newJarDate) > 0){
                    newJarDate = firstDate;
                }
            }
            JarDate = newJarDate;
            mainConfig.get().set("JarDate", JarDate);

        } else {

            JarFile jf = new JarFile(new File(Bukkit.getServer().getPluginManager().getPlugin("MekanicaCore").getDataFolder().getParentFile() + "/" + JarName));
            Enumeration e = jf.entries();

            while (e.hasMoreElements()) {
                JarEntry je = (JarEntry) e.nextElement();

                Date firstDate = new Date(je.getLastModifiedTime().toMillis());

                if (newJarDate == null){
                    newJarDate = firstDate;
                }
                if (firstDate.compareTo(newJarDate) > 0){
                    newJarDate = firstDate;
                }
            }

            JarDate = (Date) mainConfig.get().get("JarDate");

            if (JarDate != null){
                if (newJarDate.compareTo(JarDate) > 0){

                    JarDate = newJarDate;
                    mainConfig.get().set("JarDate", JarDate);
                    MainConfig.saveSynchronously();

                    reloadCall();
                }
            } else {
                JarDate = newJarDate;
                mainConfig.get().set("JarDate", JarDate);
                MainConfig.saveSynchronously();
            }

        }
    }

    private void reloadCall(){
        new BukkitRunnable(){
            @Override
            public void run() {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "plugman reload MekanicaCore");
            }
        }.runTask(MekanicaCore.getInstance());
    }
}