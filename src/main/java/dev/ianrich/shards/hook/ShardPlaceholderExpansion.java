package dev.ianrich.shards.hook;

import com.avaje.ebean.validation.NotNull;
import dev.ianrich.shards.api.ShardsAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class ShardPlaceholderExpansion extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "Shard";
    }

    @Override
    public String getAuthor() {
        return "Ian Rich";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player p, String i) {
        switch(i){
            case "balance":{
                return ShardsAPI.getProfile(p).getShards().toString();
            }
        }

        return null;
    }
}
