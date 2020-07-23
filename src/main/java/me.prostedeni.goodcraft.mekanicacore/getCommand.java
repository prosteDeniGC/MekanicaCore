package me.prostedeni.goodcraft.mekanicacore;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

import static org.bukkit.ChatColor.*;

public class getCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("mget")) {
            if (sender instanceof Player) {
                if (sender.hasPermission("MekanicaCore.admin")){
                    if (args.length == 0){
                        sender.sendMessage(DARK_RED + "Invalid number of arguments");

                    } else if (args.length == 1){

                        try {
                            int ID = Integer.parseInt(args[0]);
                            //tries to parse the args into ID

                            ItemStack item = getItem.getItemFromID(ID);
                            //gets specified item

                            if (!(item.getType().equals(Material.DIRT))){
                                //if the item material isn't dirt, it means it is a valid item in the plugin

                                ((Player) sender).getInventory().addItem(item);
                                sender.sendMessage(translateAlternateColorCodes('&', "&2You have been given &r" + Objects.requireNonNull(item.getItemMeta()).getDisplayName()));

                            } else if (item.getType().equals(Material.DIRT)){
                                sender.sendMessage(DARK_RED + "Item with this ID doesn't exist");
                            }

                        } catch (NumberFormatException e){
                            sender.sendMessage(DARK_RED + "Invalid arguments. Please enter numerical ID");
                        }

                    } else if (args.length == 2){
                        try {
                            int ID = Integer.parseInt(args[0]);
                            int amount = Integer.parseInt(args[1]);
                            //tries to convert strings to ints

                            if (amount <= 64) {

                                ItemStack item = getItem.getItemFromID(ID);
                                //gets specified item

                                item.setAmount(amount);

                                if (!(item.getType().equals(Material.DIRT))) {
                                    //if the item material isn't dirt, it means it is a valid item in the plugin

                                    ((Player) sender).getInventory().addItem(item);
                                    sender.sendMessage(translateAlternateColorCodes('&', "&2You have been given &6" + amount + " &2of &r" + Objects.requireNonNull(item.getItemMeta()).getDisplayName()));

                                } else if (item.getType().equals(Material.DIRT)) {
                                    sender.sendMessage(DARK_RED + "Item with this ID doesn't exist");
                                }
                            } else {
                                sender.sendMessage(DARK_RED + "Invalid amount");
                            }

                        } catch (NumberFormatException e){
                            sender.sendMessage(DARK_RED + "Invalid arguments. Please enter numerical ID and amount");
                        }
                    } else if (args.length > 2){
                        sender.sendMessage(DARK_RED + "Invalid number of arguments");
                    }
                } else {
                    sender.sendMessage(DARK_RED + "You don't have required permissions");
                }
            } else {
                sender.sendMessage(DARK_RED + "Only players can execute this command");
            }
        }
        return false;
    }
}
