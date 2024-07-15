package dev.ianrich.shards.command;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShardTabCompleter implements TabCompleter {

    @Override
    public List onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");

        List completions = new ArrayList();

        if(command.getName().equalsIgnoreCase("shards")) {
            switch (args.length) {
                case 1: {
                    String toComplete = args[0].toLowerCase();
                    Player[] var6 = (Bukkit.getOnlinePlayers()).toArray(new Player[0]);
                    int var7 = var6.length;

                    for (int var8 = 0; var8 < var7; ++var8) {
                        OfflinePlayer player = var6[var8];
                        if (StringUtil.startsWithIgnoreCase(player.getName(), toComplete)) {
                            completions.add(player.getName());
                        }
                    }

                    break;
                }

                default: {
                    return ImmutableList.of();

                }
            }
        } else {

            switch (args.length) {

                case 1: {

                    String toComplete = args[0].toLowerCase();
                    String[] var6 = {"check", "set", "reset", "help"};
                    int var7 = var6.length;

                    for (int var8 = 0; var8 < var7; ++var8) {
                        String str = var6[var8];
                        if (StringUtil.startsWithIgnoreCase(str, toComplete)) {
                            completions.add(str);
                        }
                    }

                    break;
                }

                case 2: {
                    String toComplete = args[1].toLowerCase();
                    Player[] var6 = (Bukkit.getOnlinePlayers()).toArray(new Player[0]);
                    int var7 = var6.length;

                    for (int var8 = 0; var8 < var7; ++var8) {
                        OfflinePlayer player = var6[var8];
                        if (StringUtil.startsWithIgnoreCase(player.getName(), toComplete)) {
                            completions.add(player.getName());
                        }
                    }

                    break;
                }

                default: {
                    return ImmutableList.of();

                }
            }
        }

        return completions;
    }
}
