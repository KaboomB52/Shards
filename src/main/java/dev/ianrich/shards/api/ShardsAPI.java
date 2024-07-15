package dev.ianrich.shards.api;

import dev.ianrich.shards.Shards;
import dev.ianrich.shards.profile.ShardProfile;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

// Easy implementation for other plugins. - Ian
public class ShardsAPI {

    /**
     * Returns the profile of a queried player.
     *
     * @param player
     * @return ShardProfile
     */

    public static ShardProfile getProfile(Player player){
        return Shards.instance.getProfileManager().getOrCreateProfile(player);
    }

    /**
     * Returns the profile of a queried offline player.
     *
     * @param offlinePlayer
     * @return ShardProfile
     */

    public static ShardProfile getProfile(OfflinePlayer offlinePlayer){
        return Shards.instance.getProfileManager().getOrCreateProfile(offlinePlayer);
    }

    /**
     * Sets the amount of shards a profile has.
     *
     * @param profile
     * @param integer
     * @return
     */

    public static Integer setShards(ShardProfile profile, Integer integer){
        profile.setShards(integer);
        return profile.getShards();
    }

    /**
     * Adds an amount of shards to a profile.
     *
     * @param profile
     * @param integer
     * @return
     */

    public static Integer addShards(ShardProfile profile, Integer integer){
        profile.setShards(profile.getShards() + integer);
        return profile.getShards();
    }

    /**
     * Removes an amount of shards from a profile, returns 0 if there is a negative balance.
     *
     * @param profile
     * @param integer
     * @return
     */

    public static Integer removeShards(ShardProfile profile, Integer integer){
        if(profile.getShards() - integer < 0) {
            profile.setShards(0);
            return 0;
        }

        profile.setShards(profile.getShards() - integer);
        return profile.getShards();
    }

    /**
     * Resets the amount of shards on a profile.
     *
     * @param profile
     */

    public static void resetShards(ShardProfile profile){
        profile.setShards(0);
    }

}
