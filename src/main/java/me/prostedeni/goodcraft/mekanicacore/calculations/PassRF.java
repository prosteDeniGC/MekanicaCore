package me.prostedeni.goodcraft.mekanicacore.calculations;

import me.prostedeni.goodcraft.mekanicacore.MekanicaCore;
import me.prostedeni.goodcraft.mekanicacore.configFiles.Systems.conduitConfigData;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class PassRF {

    conduitConfigData conduitConfigData = new conduitConfigData();

    //public ArrayList<String> Distributors = new ArrayList<>();
    //public ArrayList<String> Recievers = new ArrayList<>();

    public void moveRF(){
        for (World world : MekanicaCore.getInstance().getServer().getWorlds()){
            if (conduitConfigData.get().getConfigurationSection(world.getName()) != null){
                for (Location conduitLoc : conduitConfigData.getAllConduits(world)){

                    List<Location> listAround = new ArrayList<>(conduitConfigData.getAroundConduit(conduitLoc));
                    if (!listAround.isEmpty()){
                        listAround.add(conduitLoc);

                        int totalRF = 0;
                        for (Location loc : listAround){
                            totalRF = totalRF + conduitConfigData.getRF(loc);
                        }
                        totalRF = totalRF/listAround.size();
                        for (Location loc : listAround){
                            conduitConfigData.setRF(loc, totalRF);
                        }
                    }
                }
            }
        }
    }

}
