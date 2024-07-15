package dev.ianrich.shards.profile;

import dev.ianrich.shards.Shards;
import dev.ianrich.shards.profile.listener.ConnectionListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProfileManager {

    List<ShardProfile> profiles;

    public ProfileManager(){
        Bukkit.getLogger().info("[Shards] Profile Manager initializing...");

        profiles = new ArrayList<>();
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(), Shards.instance);

        Bukkit.getLogger().info("[Shards] Profile Manager initialized!");
    }

    public ShardProfile loadProfile(Player player){
        ShardProfile profile = getOrCreateProfile(player);

        profiles.add(profile);

        return profile;
    }

    public ShardProfile getOrCreateProfile(Player player){
        if(profiles.stream().anyMatch(sp->sp.getUuid()==player.getUniqueId())){
            return profiles.stream().filter(sp->sp.getUuid()==player.getUniqueId()).collect(Collectors.toList()).get(0);
        }

        return new ShardProfile(player.getUniqueId());
    }

    public ShardProfile getOrCreateProfile(OfflinePlayer offlinePlayer){
        if(profiles.stream().anyMatch(sp->sp.getUuid()==offlinePlayer.getUniqueId())){
            return profiles.stream().filter(sp->sp.getUuid()==offlinePlayer.getUniqueId()).collect(Collectors.toList()).get(0);
        }

        return new ShardProfile(offlinePlayer.getUniqueId());
    }

    public Boolean isLoaded(Player player){
        return profiles.stream().anyMatch(shardProfile -> shardProfile.uuid.equals(player.getUniqueId()));
    }

}
