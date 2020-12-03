package me.prostedeni.goodcraft.mekanicacore.events.inventory;

import me.prostedeni.goodcraft.mekanicacore.MekanicaCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import static me.prostedeni.goodcraft.mekanicacore.getItem.getIDpane;

public class InventoryClose implements Listener {

    @EventHandler
    public void invClose(InventoryCloseEvent e){
        try {
            if (!(e.getInventory().equals(e.getViewers().get(0).getInventory()))) {
                if (e.getInventory().getItem(0) != null){
                    if (e.getInventory().getItem(0).hasItemMeta()) {
                        String ID = getIDpane(e.getInventory().getItem(0));

                        if (MekanicaCore.transactionInventory.containsKey(ID)){
                            MekanicaCore.transactionInventory.remove(ID);
                        }
                    }
               }
            }
        } catch (NullPointerException err){
            //nothing
        }
    }
    //this event removes closed inventory from HashMap

}
