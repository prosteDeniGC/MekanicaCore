package me.prostedeni.goodcraft.mekanicacore.events;

import me.prostedeni.goodcraft.mekanicacore.calculations.PassRF;
import me.prostedeni.goodcraft.mekanicacore.configFiles.Generators.stirlingConfigData;
import me.prostedeni.goodcraft.mekanicacore.configFiles.Machines.furnaceConfigData;
import me.prostedeni.goodcraft.mekanicacore.configFiles.Systems.conduitConfigData;
import me.prostedeni.goodcraft.mekanicacore.getItem;
import me.prostedeni.goodcraft.mekanicacore.gui.generators.Stirling;
import me.prostedeni.goodcraft.mekanicacore.gui.machines.Furnace;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class PlayerInteract implements Listener {

    PassRF PassRF = new PassRF();
    conduitConfigData conduitConfigData = new conduitConfigData();

    @EventHandler
    public void onInteract(PlayerInteractEvent e){

        if (e.getPlayer().getInventory().getItemInHand().equals(getItem.getItemFromID(002))){
            if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                if (conduitConfigData.existsConduit(e.getClickedBlock().getLocation())) {
                    conduitConfigData.addRF(e.getClickedBlock().getLocation(), 100);
                    e.getPlayer().sendMessage("added 100RF");
                }
            } else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (conduitConfigData.existsConduit(e.getClickedBlock().getLocation())) {
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&lRF: " + conduitConfigData.getRF(e.getClickedBlock().getLocation())));
                } else {
                    e.getPlayer().sendMessage("not a conduit");
                }
            }

        } else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){

            if (!(e.getPlayer().isSneaking())){
                    try {
                        for (String s : stirlingConfigData.get().getConfigurationSection("Data.").getKeys(false)) {

                            if (stirlingConfigData.getLocation(s).equals((e.getClickedBlock().getLocation()))) {

                                e.setCancelled(true);
                                e.getPlayer().openInventory(Objects.requireNonNull(Stirling.createGUI(s)));

                                break;
                            }
                        }

                        for (String s : furnaceConfigData.get().getConfigurationSection("Data.").getKeys(false)) {

                            if (furnaceConfigData.getLocation(s).equals((e.getClickedBlock().getLocation()))) {

                                e.setCancelled(true);
                                e.getPlayer().openInventory(Objects.requireNonNull(Furnace.createGUI(s)));

                                break;
                            }
                        }
                    } catch (NullPointerException e1){
                        //nothing
                    }
            }
        }
    }

}
