package me.prostedeni.goodcraft.mekanicacore.calculations;

import me.prostedeni.goodcraft.mekanicacore.configFiles.Generators.stirlingConfigData;
import me.prostedeni.goodcraft.mekanicacore.configFiles.Systems.conduitConfigData;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BurnFuel {

    fuelManager fuelManager = new fuelManager();
    conduitConfigData conduitConfigData = new conduitConfigData();
    PassRF PassRF = new PassRF();

    public void burn(){

        try {
            burnStirling();
        } catch (NullPointerException e){
            System.out.print("BurnFuel.java Error");
        }
    }

    public void burnStirling() {

        final int limit = 100000;
        final int RFsecond = 200;
        //how many RF per second does it generate

        if (!stirlingConfigData.getAllStirlings().isEmpty()){
            for (String gen : stirlingConfigData.getAllStirlings()) {

                //nrg transfer

                if (stirlingConfigData.getRF(gen) < limit) {
                    if (stirlingConfigData.getBurning(gen)) {
                        //something is/was burning

                        int BurnedRF = stirlingConfigData.getBurnedRF(gen);

                        if (BurnedRF > 0) {
                            //burns

                            if (BurnedRF >= RFsecond) {
                                //there is more RF to burn next call

                                if (stirlingConfigData.getRF(gen) + RFsecond <= limit) {
                                    //generator hasn't reached it's limit

                                    stirlingConfigData.takeBurnedRF(RFsecond, gen);
                                    stirlingConfigData.addRF(RFsecond, gen);
                                } else if (stirlingConfigData.getRF(gen) + RFsecond > limit) {
                                    //generator is full
                                    stirlingConfigData.takeBurnedRF(limit - stirlingConfigData.getRF(gen), gen);
                                    stirlingConfigData.setRF(limit, gen);
                                }
                            } else if (BurnedRF < RFsecond) {
                                //this is the last remaining RF

                                if (stirlingConfigData.getRF(gen) + BurnedRF <= limit) {
                                    //generator hasn't reached it's limit

                                    stirlingConfigData.setBurnedRF(0, gen);
                                    stirlingConfigData.addRF(BurnedRF, gen);

                                } else if (stirlingConfigData.getRF(gen) + BurnedRF > limit) {
                                    //generator is full

                                    stirlingConfigData.takeBurnedRF(limit - stirlingConfigData.getRF(gen), gen);
                                    stirlingConfigData.setRF(limit, gen);
                                }
                            }
                        } else if (BurnedRF <= 0) {
                            //now it checks if there are more items to burn

                            if (!(stirlingConfigData.getItem(gen).equals(new ItemStack(Material.AIR)))) {
                                //there is an item inside

                                if (fuelManager.isFuel(stirlingConfigData.getItem(gen).getType(), 003)) {
                                    //the item inside is burnable

                                    if (stirlingConfigData.getAmount(gen) > 1) {

                                        stirlingConfigData.setBurning(true, gen);
                                        stirlingConfigData.setBurnedRF(fuelManager.getTotalRF(stirlingConfigData.getItem(gen).getType()), gen);
                                        stirlingConfigData.takeOne(gen);
                                        //takes first item and sets amout of RF to be burned

                                    } else {
                                        stirlingConfigData.setBurning(false, gen);
                                    }
                                } else {
                                    stirlingConfigData.setBurning(false, gen);
                                }
                            } else {
                                stirlingConfigData.setBurning(false, gen);
                            }
                        }

                    } else {
                        //the generator was not working before

                        if (!(stirlingConfigData.getItem(gen).equals(new ItemStack(Material.AIR)))) {
                            //there is an item inside

                            if (fuelManager.isFuel(stirlingConfigData.getItem(gen).getType(), 003)) {
                                //the item inside is burnable

                                stirlingConfigData.setBurning(true, gen);
                                stirlingConfigData.setBurnedRF(fuelManager.getTotalRF(stirlingConfigData.getItem(gen).getType()), gen);
                                stirlingConfigData.takeOne(gen);
                                //takes first item and sets amout of RF to be burned

                            }
                        }
                    }
                } else {
                    stirlingConfigData.setBurning(false, gen);
                }
            }
        }

    }

}
