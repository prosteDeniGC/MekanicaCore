package me.prostedeni.goodcraft.mekanicacore.calculations;

import org.bukkit.Material;

public class fuelManager {

    public boolean isFuel(Material material, Integer ID){
        if (material.equals(Material.COAL)){
            return true;
        } else {
            return false;
        }
    }

    public Integer getTotalRF(Material material){
        if (material.equals(Material.COAL)){
            return 8000;
        } else {
            return 0;
        }
    }

}
