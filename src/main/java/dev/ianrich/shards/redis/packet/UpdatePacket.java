package dev.ianrich.shards.redis.packet;

import dev.ianrich.shards.api.ShardsAPI;
import dev.ianrich.hermes.model.RedisPacket;
import org.bukkit.Bukkit;

import java.util.UUID;

public class UpdatePacket implements RedisPacket {

    UUID uuid;
    Integer shards;

    public UpdatePacket(UUID uuid, Integer shards){
        this.uuid = uuid;
        this.shards = shards;
    }

    @Override
    public void onReceive() {
        ShardsAPI.getProfile(Bukkit.getOfflinePlayer(uuid)).setShards(shards);
    }

    @Override
    public void onSend() {

    }

}
