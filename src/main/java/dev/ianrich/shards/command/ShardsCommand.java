package dev.ianrich.shards.command;

import com.google.common.collect.ImmutableList;
import dev.ianrich.shards.api.ShardsAPI;
import dev.ianrich.shards.util.Formatting;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

// This command does the same thing as /shard check (but simpler) - Ian

public class ShardsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {

        if(!(commandSender instanceof Player)) {
            if (Bukkit.getOfflinePlayer(strings[0]) != null) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(strings[0]);

                commandSender.sendMessage(target.getName() + ChatColor.AQUA + "'s Shards: " + ChatColor.GREEN + Formatting.addCommasToInteger(ShardsAPI.getProfile(target).getShards()));
            } else {
                commandSender.sendMessage(strings[0] + ChatColor.RED + " does not have a profile.");
            }
        } else {
            Player sender = (Player) commandSender;
            if(strings.length!=1){
                commandSender.sendMessage(ChatColor.AQUA + "Your Shards: " + ChatColor.GREEN + Formatting.addCommasToInteger(ShardsAPI.getProfile(sender).getShards()));
            } else {
                if(Bukkit.getOfflinePlayer(strings[0]) != null){
                    OfflinePlayer target = Bukkit.getOfflinePlayer(strings[0]);

                    commandSender.sendMessage(target.getName() + ChatColor.AQUA + "'s Shards: " + ChatColor.GREEN + Formatting.addCommasToInteger(ShardsAPI.getProfile(target).getShards()));
                } else {
                    commandSender.sendMessage(strings[0] + ChatColor.RED + " does not have a profile.");
                }
            }
        }

        return false;
    }
}
