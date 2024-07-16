package dev.ianrich.shards.redis;

import dev.ianrich.shards.Shards;
import me.kaboom.hermes.Hermes;
import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisHandler {

    public Hermes hermes;

    public RedisHandler(){
        Bukkit.getLogger().info("[Shards] Redis Handler initializing...");

        // Hermes - Our Savior (from shitty code) - Ian

        if(Shards.instance.getConfig().getBoolean("redis-auth.enabled")) {
            hermes = new Hermes(Shards.instance.getConfig().getString("redis.host"), Shards.instance.getConfig().getInt("redis.port"), Shards.instance.getConfig().getString("redis-auth.username"), Shards.instance.getConfig().getString("redis-auth.password"), Shards.instance.getConfig().getInt("redis.id"));
        } else {
            hermes = new Hermes(Shards.instance.getConfig().getString("redis.host"), Shards.instance.getConfig().getInt("redis.port"), Shards.instance.getConfig().getInt("redis.id"));
        }

        Bukkit.getLogger().info("[Shards] Redis Handler initialized!");
    }
}
