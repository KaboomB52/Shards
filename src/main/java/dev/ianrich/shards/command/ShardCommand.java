package dev.ianrich.shards.command;

import com.google.common.collect.ImmutableList;
import dev.ianrich.shards.api.ShardsAPI;
import dev.ianrich.shards.util.Formatting;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ShardCommand extends BukkitCommand {

    public ShardCommand() {
        super("shard");
        super.getAliases().add("shart"); // I'm "mature". - Ian
        setDescription("The main command for the Shards plugin.");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            return false;
        }

        Player player = (Player) commandSender;

        // Switch Statements: More organized than if statements as instead of going through hoops we go directly to the one that matches the value. - Ian
        if(strings.length == 0){
            player.sendMessage("");
            player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "SHARDS " + ChatColor.GRAY + "(help topic):");
            player.sendMessage("");
            player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shards <||player||>" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Shows the balance of any player.");
            player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shards check <||player||>" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Shows the balance of any player.");
            player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shards set <player> [value]" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Sets the balance of any player.");
            player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shards reset <player>" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Resets the balance of any player.");
            player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shards help" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Shows this help message.");
            player.sendMessage("");

            return true;
        }

        switch(strings[0]) {

            // By the way, this is in order of difficulty... I wanted to do the hardest one last... I WANTED to... - Ian
            case "help": {

                player.sendMessage("");
                player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "SHARDS " + ChatColor.GRAY + "(help topic):");
                player.sendMessage("");
                player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shards <||player||>" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Shows the balance of any player.");
                player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shards check <||player||>" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Shows the balance of any player.");
                player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shards set <player> [value]" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Sets the balance of any player.");
                player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shards reset <player>" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Resets the balance of any player.");
                player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "❘ " + ChatColor.AQUA + "/shards help" + ChatColor.DARK_GRAY + " - " + ChatColor.GREEN + "Shows this help message.");
                player.sendMessage("");

                break;
            }

            case "check": {
                if(strings.length!=2){
                    player.sendMessage(ChatColor.AQUA + "Your Shards: " + ChatColor.GREEN + Formatting.addCommasToInteger(ShardsAPI.getProfile(player).getShards()));
                } else {
                    if(Bukkit.getOfflinePlayer(strings[1]) != null){
                        OfflinePlayer target = Bukkit.getOfflinePlayer(strings[1]);

                        player.sendMessage(target.getName() + ChatColor.AQUA + "'s Shards: " + ChatColor.GREEN + Formatting.addCommasToInteger(ShardsAPI.getProfile(target).getShards()));
                    } else {
                        player.sendMessage(strings[1] + ChatColor.RED + " does not have a profile.");
                    }
                }

                break;
            }

            case "set": {
                if(strings.length!=3) player.sendMessage(ChatColor.RED + "/shards set <player> [value]");
                if(!player.hasPermission("shards.admin")) player.sendMessage(ChatColor.RED + "You don't have the required permissions for this command.");

                // We use Offline Players instead of Online Players because they might be offline, dur. - Ian
                if(Bukkit.getOfflinePlayer(strings[1]) != null){
                    OfflinePlayer target = Bukkit.getOfflinePlayer(strings[1]);
                    ShardsAPI.getProfile(target).setShards(Integer.parseInt(strings[2]));
                    player.sendMessage(target.getName() + ChatColor.GREEN + "'s shards have been set to " + ChatColor.WHITE + strings[2] + ChatColor.GREEN + ".");
                } else {
                    player.sendMessage(strings[1] + ChatColor.RED + " does not have a profile.");
                }

                break;

            }

            case "reset": {
                if(strings.length!=2) player.sendMessage(ChatColor.RED + "/shards reset <player>");
                if(!player.hasPermission("shards.admin")) player.sendMessage(ChatColor.RED + "You don't have the required permissions for this command.");

                // We use Offline Players instead of Online Players because they might be offline, dur. - Ian
                if(Bukkit.getOfflinePlayer(strings[1]) != null){
                    OfflinePlayer target = Bukkit.getOfflinePlayer(strings[1]);
                    ShardsAPI.resetShards(ShardsAPI.getProfile(target));
                    player.sendMessage(target.getName() + ChatColor.RED + "'s profile has been reset.");
                } else {
                    player.sendMessage(strings[1] + ChatColor.RED + " does not have a profile.");
                }

                break;

            }

        }

        return true;
    }

    @Override
    public List tabComplete(CommandSender sender, String alias, String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        switch (args.length) {

            case 1: {
                List completions = new ArrayList();
                String toComplete = args[0].toLowerCase();
                String[] var6 = {"check", "set", "reset", "help"};
                int var7 = var6.length;

                for (int var8 = 0; var8 < var7; ++var8) {
                    String str = var6[var8];
                    if (StringUtil.startsWithIgnoreCase(str, toComplete)) {
                        completions.add(str);
                    }
                }

                return completions;
            }

            case 2: {
                List completions = new ArrayList();
                String toComplete = args[1].toLowerCase();
                OfflinePlayer[] var6 = (Bukkit.getOfflinePlayers());
                int var7 = var6.length;

                for (int var8 = 0; var8 < var7; ++var8) {
                    OfflinePlayer player = var6[var8];
                    if (StringUtil.startsWithIgnoreCase(player.getName(), toComplete)) {
                        completions.add(player.getName());
                    }
                }

                return completions;
            }

            default: {
                return ImmutableList.of();

            }
        }
    }
}
