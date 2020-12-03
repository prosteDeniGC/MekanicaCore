package me.prostedeni.goodcraft.mekanicacore.events.inventory;

import me.prostedeni.goodcraft.mekanicacore.MekanicaCore;
import me.prostedeni.goodcraft.mekanicacore.configFiles.Generators.stirlingConfigData;
import me.prostedeni.goodcraft.mekanicacore.configFiles.Machines.furnaceConfigData;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

import static me.prostedeni.goodcraft.mekanicacore.getItem.getIDpane;

public class InventoryClick implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void invMove(InventoryClickEvent e) {
        String invTitle = ChatColor.stripColor(e.getView().getTitle());
        if (invTitle.equals("Stirling Generator")){

            if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT){

                try {
                    String ID = getIDpane(e.getView().getTopInventory().getItem(0));

                    if (MekanicaCore.transactionInventory.containsKey(ID)){

                        if (e.getClickedInventory().equals(e.getView().getBottomInventory())) {

                            MekanicaCore.transactionInventory.put(ID, true);
                            new BukkitRunnable() {

                                @Override
                                public void run() {
                                    stirlingConfigData.setItem(e.getView().getTopInventory().getItem(19), ID);
                                    if (MekanicaCore.transactionInventory.containsKey(ID)) {
                                        MekanicaCore.transactionInventory.put(ID, false);
                                    }
                                }
                            }.runTaskLaterAsynchronously(MekanicaCore.getInstance(), 3);

                        } else if (e.getClickedInventory().equals(e.getView().getTopInventory())){
                            if (e.getRawSlot() == 19){

                                MekanicaCore.transactionInventory.put(ID, true);
                                new BukkitRunnable() {

                                    @Override
                                    public void run() {
                                        stirlingConfigData.setItem(e.getView().getTopInventory().getItem(19), ID);
                                        if (MekanicaCore.transactionInventory.containsKey(ID)) {
                                            MekanicaCore.transactionInventory.put(ID, false);
                                        }
                                    }
                                }.runTaskLaterAsynchronously(MekanicaCore.getInstance(), 3);

                            } else {
                                e.setCancelled(true);
                            }
                        }
                    }

                } catch (NullPointerException err){
                    //nothing
                }
            } else {
                    try {
                        if (e.getClickedInventory().getItem(0) != null) {

                            String ID = getIDpane(e.getClickedInventory().getItem(0));
                            if (MekanicaCore.transactionInventory.containsKey(ID)) {
                                //checks if the open inventory is in hashmap of open inventories

                                if (!(e.getWhoClicked().getInventory().equals(e.getClickedInventory()))) {
                                    if (e.getRawSlot() == 19) {

                                        MekanicaCore.transactionInventory.put(ID, true);
                                        new BukkitRunnable() {

                                            @Override
                                            public void run() {
                                                stirlingConfigData.setItem(e.getClickedInventory().getItem(19), ID);
                                                if (MekanicaCore.transactionInventory.containsKey(ID)) {
                                                    MekanicaCore.transactionInventory.put(ID, false);
                                                }
                                            }
                                        }.runTaskLaterAsynchronously(MekanicaCore.getInstance(), 3);

                                    } else {
                                        e.setCancelled(true);
                                    }
                                }
                            }
                        }
                    } catch (NullPointerException err) {
                        //nothing
                    }
            }
        } else if (invTitle.equals("Electric Furnace")){
            if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT){

                try {
                    String ID = getIDpane(e.getView().getTopInventory().getItem(0));

                    if (MekanicaCore.transactionInventory.containsKey(ID)){

                        if (e.getClickedInventory().equals(e.getView().getBottomInventory())) {

                            MekanicaCore.transactionInventory.put(ID, true);
                            new BukkitRunnable() {

                                @Override
                                public void run() {
                                    furnaceConfigData.setItemPre(e.getView().getTopInventory().getItem(28), ID);
                                    furnaceConfigData.setItemPost(e.getView().getTopInventory().getItem(32), ID);
                                    if (MekanicaCore.transactionInventory.containsKey(ID)) {
                                        MekanicaCore.transactionInventory.put(ID, false);
                                    }
                                }
                            }.runTaskLaterAsynchronously(MekanicaCore.getInstance(), 3);

                        } else if (e.getClickedInventory().equals(e.getView().getTopInventory())){
                            if (e.getRawSlot() == 28){

                                MekanicaCore.transactionInventory.put(ID, true);
                                new BukkitRunnable() {

                                    @Override
                                    public void run() {
                                        furnaceConfigData.setItemPre(e.getView().getTopInventory().getItem(28), ID);
                                        if (MekanicaCore.transactionInventory.containsKey(ID)) {
                                            MekanicaCore.transactionInventory.put(ID, false);
                                        }
                                    }
                                }.runTaskLaterAsynchronously(MekanicaCore.getInstance(), 3);

                            } else if (e.getRawSlot() == 32) {

                                MekanicaCore.transactionInventory.put(ID, true);
                                new BukkitRunnable() {

                                    @Override
                                    public void run() {
                                        furnaceConfigData.setItemPost(e.getView().getTopInventory().getItem(32), ID);
                                        if (MekanicaCore.transactionInventory.containsKey(ID)) {
                                            MekanicaCore.transactionInventory.put(ID, false);
                                        }
                                    }
                                }.runTaskLaterAsynchronously(MekanicaCore.getInstance(), 3);

                            } else {
                                e.setCancelled(true);
                            }
                        }
                    }

                } catch (NullPointerException err){
                    //nothing
                }
            } else {

                try {

                    if (e.getClickedInventory().getItem(0) != null) {

                        String ID = getIDpane(e.getClickedInventory().getItem(0));
                        if (MekanicaCore.transactionInventory.containsKey(ID)) {
                            //checks if the open inventory is in hashmap of open inventories

                            if (!(e.getWhoClicked().getInventory().equals(e.getClickedInventory()))) {
                                if (e.getRawSlot() == 28) {

                                    MekanicaCore.transactionInventory.put(ID, true);
                                    new BukkitRunnable() {

                                        @Override
                                        public void run() {
                                            furnaceConfigData.setItemPre(e.getClickedInventory().getItem(28), ID);
                                            if (MekanicaCore.transactionInventory.containsKey(ID)) {
                                                MekanicaCore.transactionInventory.put(ID, false);
                                            }
                                        }
                                    }.runTaskLaterAsynchronously(MekanicaCore.getInstance(), 3);

                                } else if (e.getRawSlot() == 32) {

                                    MekanicaCore.transactionInventory.put(ID, true);
                                    new BukkitRunnable() {

                                        @Override
                                        public void run() {
                                            furnaceConfigData.setItemPost(e.getView().getTopInventory().getItem(32), ID);
                                            if (MekanicaCore.transactionInventory.containsKey(ID)) {
                                                MekanicaCore.transactionInventory.put(ID, false);
                                            }
                                        }
                                    }.runTaskLaterAsynchronously(MekanicaCore.getInstance(), 3);

                                } else {
                                    e.setCancelled(true);
                                }
                            }
                        }
                    }
                } catch (NullPointerException err) {
                    //nothing
                }
            }
        }
    }
    //this event handles items in generators and machines

}
