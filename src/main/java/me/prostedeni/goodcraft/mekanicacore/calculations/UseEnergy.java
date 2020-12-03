package me.prostedeni.goodcraft.mekanicacore.calculations;

import me.prostedeni.goodcraft.mekanicacore.configFiles.Machines.furnaceConfigData;
import me.prostedeni.goodcraft.mekanicacore.configFiles.Systems.conduitConfigData;
import me.prostedeni.goodcraft.mekanicacore.getItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class UseEnergy {

    conduitConfigData conduitConfigData = new conduitConfigData();
    PassRF PassRF = new PassRF();

    public void use(){
        try {
            burnFurnace();
        } catch (NullPointerException e){
            System.out.print("UseEnergy.java Error");
        }

    }

    public void burnFurnace(){

        final int limit = 100000;
        final int RFitem = 1000;
        //how much RF does it take to smelt an item
        final int RFsecond = 125;
        //how much RF does it take for one operation

            if (!furnaceConfigData.getAllFurnaces().isEmpty()) {
                for (String furn : furnaceConfigData.getAllFurnaces()) {

                    //nrg transfer

                    if (furnaceConfigData.getRF(furn) > 0) {
                        //furnace has power

                        if (!(getItem.getBurnable(furnaceConfigData.getItemPre(furn)).equals(new ItemStack(Material.AIR)))) {
                            //the item can burn

                            if (getItem.getBurnable(furnaceConfigData.getItemPre(furn)).getType().equals(furnaceConfigData.getItemPost(furn).getType())) {
                                //the item in Post slot is burned item from Pre slot
                                if (furnaceConfigData.getAmountPost(furn) < 64) {
                                    //the output isn't full

                                    furnaceConfigData.setBurning(true, furn);

                                    if (furnaceConfigData.getBurnedRF(furn) >= RFitem) {
                                        //the item was burned

                                        furnaceConfigData.addOnePost(furn);
                                        furnaceConfigData.takeOnePre(furn);
                                        furnaceConfigData.setBurnedRF(0, furn);

                                    } else if (furnaceConfigData.getBurnedRF(furn) < RFitem) {
                                        //the item is burning

                                        if (furnaceConfigData.getRF(furn) >= RFsecond) {
                                            //furnace has enough RF for operation

                                            furnaceConfigData.addBurnedRF(RFsecond, furn);
                                            furnaceConfigData.takeRF(RFsecond, furn);
                                        } else if (furnaceConfigData.getRF(furn) < RFsecond) {
                                            //furnace doesnt have enough RF

                                            furnaceConfigData.addBurnedRF(furnaceConfigData.getRF(furn), furn);
                                            furnaceConfigData.takeRF(furnaceConfigData.getRF(furn), furn);
                                        }
                                    }
                                }
                            } else if (furnaceConfigData.getItemPost(furn).equals(new ItemStack(Material.AIR))) {
                                //there is no item in Post slot

                                furnaceConfigData.setBurning(true, furn);

                                if (furnaceConfigData.getBurnedRF(furn) >= RFitem) {
                                    //the item was burned

                                    furnaceConfigData.setItemPost(getItem.getBurnable(furnaceConfigData.getItemPre(furn)), furn);
                                    furnaceConfigData.takeOnePre(furn);
                                    furnaceConfigData.setBurnedRF(0, furn);

                                } else if (furnaceConfigData.getBurnedRF(furn) < RFitem) {
                                    //the item is burning

                                    if (furnaceConfigData.getRF(furn) >= RFsecond) {
                                        //furnace has enough RF for operation

                                        furnaceConfigData.addBurnedRF(RFsecond, furn);
                                        furnaceConfigData.takeRF(RFsecond, furn);
                                    } else if (furnaceConfigData.getRF(furn) < RFsecond) {
                                        //furnace doesnt have enough RF

                                        furnaceConfigData.addBurnedRF(furnaceConfigData.getRF(furn), furn);
                                        furnaceConfigData.takeRF(furnaceConfigData.getRF(furn), furn);
                                    }
                                }

                            } else {
                                //neither of previous statements is true

                                furnaceConfigData.setBurning(false, furn);
                            }
                        } else {
                            furnaceConfigData.setBurning(false, furn);
                        }
                    } else {
                        furnaceConfigData.setBurning(false, furn);
                    }
                }
            }
        }

}
