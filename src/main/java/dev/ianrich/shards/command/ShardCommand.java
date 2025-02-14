package dev.ianrich.shards.command;

import com.google.common.collect.ImmutableList;
import dev.ianrich.shards.Shards;
import dev.ianrich.shards.api.ShardsAPI;
import dev.ianrich.shards.util.Formatting;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ShardCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {

        // Switch Statements: More organized than if statements as instead of going through hoops we go directly to the one that matches the value. - Ian
        if(strings.length == 0){
            commandSender.sendMessage("");
            commandSender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "SHARDS " + ChatColor.GRAY + "(help topic):");
            commandSender.sendMessage("");
            commandSender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shards <||player||>" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Shows the balance of any player.");
            commandSender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shard check <||player||>" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Shows the balance of any player.");
            commandSender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shard set <player> [value]" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Sets the balance of any player.");
            commandSender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shard reset <player>" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Resets the balance of any player.");
            commandSender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shard help" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Shows this help message.");
            commandSender.sendMessage("");

            return true;
        }

        switch(strings[0]) {

            // By the way, this is in order of difficulty... I wanted to do the hardest one last... I WANTED to... - Ian
            case "help": {

                commandSender.sendMessage("");
                commandSender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "SHARDS " + ChatColor.GRAY + "(help topic):");
                commandSender.sendMessage("");
                commandSender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shards <||player||>" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Shows the balance of any player.");
                commandSender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shard check <||player||>" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Shows the balance of any player.");
                commandSender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shard set <player> [value]" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Sets the balance of any player.");
                commandSender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shard reset <player>" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Resets the balance of any player.");
                commandSender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shard help" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Shows this help message.");
                commandSender.sendMessage("");

                break;
            }

            case "check": {
                if(!(commandSender instanceof Player)) {
                        if (Bukkit.getOfflinePlayer(strings[1]) != null) {
                            OfflinePlayer target = Bukkit.getOfflinePlayer(strings[1]);

                            commandSender.sendMessage(target.getName() + ChatColor.AQUA + "'s Shards: " + ChatColor.GREEN + Formatting.addCommasToInteger(ShardsAPI.getProfile(target).getShards()));
                        } else {
                            commandSender.sendMessage(strings[1] + ChatColor.RED + " does not have a profile.");
                        }
                } else {
                    Player sender = (Player) commandSender;
                    if(strings.length!=2){
                        commandSender.sendMessage(ChatColor.AQUA + "Your Shards: " + ChatColor.GREEN + Formatting.addCommasToInteger(ShardsAPI.getProfile(sender).getShards()));
                    } else {
                        if(Bukkit.getOfflinePlayer(strings[1]) != null){
                            OfflinePlayer target = Bukkit.getOfflinePlayer(strings[1]);

                            commandSender.sendMessage(target.getName() + ChatColor.AQUA + "'s Shards: " + ChatColor.GREEN + Formatting.addCommasToInteger(ShardsAPI.getProfile(target).getShards()));
                        } else {
                            commandSender.sendMessage(strings[1] + ChatColor.RED + " does not have a profile.");
                        }
                    }
                }


                break;
            }

            case "set": {
                if(strings.length!=3) {commandSender.sendMessage(ChatColor.RED + "/shards set <player> [value]"); return false;}
                if(!commandSender.hasPermission("shards.admin")) {commandSender.sendMessage(ChatColor.RED + "You don't have the required permissions for this command."); return false;}

                // We use Offline Players instead of Online Players because they might be offline, dur. - Ian
                if(Bukkit.getOfflinePlayer(strings[1]) != null){
                    OfflinePlayer target = Bukkit.getOfflinePlayer(strings[1]);
                    ShardsAPI.getProfile(target).setShards(Integer.parseInt(strings[2]));
                    commandSender.sendMessage(target.getName() + ChatColor.GREEN + "'s shards have been set to " + ChatColor.WHITE + strings[2] + ChatColor.GREEN + ".");
                } else {
                    commandSender.sendMessage(strings[1] + ChatColor.RED + " does not have a profile.");
                }

                break;

            }

            case "reset": {
                if(strings.length!=2) {commandSender.sendMessage(ChatColor.RED + "/shards reset <player>"); return false;}
                if(!commandSender.hasPermission("shards.admin")) {commandSender.sendMessage(ChatColor.RED + "You don't have the required permissions for this command."); return false;}

                // We use Offline Players instead of Online Players because they might be offline, dur. - Ian
                if(Bukkit.getOfflinePlayer(strings[1]) != null){
                    OfflinePlayer target = Bukkit.getOfflinePlayer(strings[1]);
                    ShardsAPI.resetShards(ShardsAPI.getProfile(target));
                    commandSender.sendMessage(target.getName() + ChatColor.RED + "'s profile has been reset.");
                } else {
                    commandSender.sendMessage(strings[1] + ChatColor.RED + " does not have a profile.");
                }

                break;

            }

            default: {
                commandSender.sendMessage("");
                commandSender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "SHARDS " + ChatColor.GRAY + "(help topic):");
                commandSender.sendMessage("");
                commandSender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shards <||player||>" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Shows the balance of any player.");
                commandSender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shard check <||player||>" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Shows the balance of any player.");
                commandSender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shard set <player> [value]" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Sets the balance of any player.");
                commandSender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shard reset <player>" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Resets the balance of any player.");
                commandSender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shard help" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Shows this help message.");
                commandSender.sendMessage("");
                break;
            }

        }

        return true;
    }
}
