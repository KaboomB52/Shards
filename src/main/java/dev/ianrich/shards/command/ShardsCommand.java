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

public class ShardsCommand extends BukkitCommand {

    public ShardsCommand() {

        // This command does the same thing as /shard check (but simpler) - Ian

        super("shards");
        setDescription("Check a player's Shard balance!");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            return false;
        }

        Player player = (Player) commandSender;

        if(strings.length!=1){
            player.sendMessage(ChatColor.AQUA + "Your Shards: " + ChatColor.GREEN + Formatting.addCommasToInteger(ShardsAPI.getProfile(player).getShards()));
        } else {
            if(Bukkit.getOfflinePlayer(strings[0]) != null){
                OfflinePlayer target = Bukkit.getOfflinePlayer(strings[0]);

                player.sendMessage(target.getName() + ChatColor.AQUA + "'s Shards: " + ChatColor.GREEN + Formatting.addCommasToInteger(ShardsAPI.getProfile(target).getShards()));
            } else {
                player.sendMessage(strings[1] + ChatColor.RED + " does not have a profile.");
            }
        }
        return false;
    }

    @Override
    public List tabComplete(CommandSender sender, String alias, String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            List completions = new ArrayList();
            String toComplete = args[0].toLowerCase();
            OfflinePlayer[] var6 = (Bukkit.getOfflinePlayers());
            int var7 = var6.length;

            for (int var8 = 0; var8 < var7; ++var8) {
                OfflinePlayer player = var6[var8];
                if (StringUtil.startsWithIgnoreCase(player.getName(), toComplete)) {
                    completions.add(player.getName());
                }
            }

            return completions;
        } else {
            return ImmutableList.of();
        }
    }
}
