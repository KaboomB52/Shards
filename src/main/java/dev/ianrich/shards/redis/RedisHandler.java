package dev.ianrich.shards.redis;

import dev.ianrich.shards.Shards;
import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisHandler {

    JedisPool jedisPool;

    public RedisHandler(){
        Bukkit.getLogger().info("Redis Handler initializing...");

        connect();

        Bukkit.getLogger().info("Redis Handler initialized!");
    }

    private void connect() {
        try {
            if (!Shards.instance.getConfig().getBoolean("redis-auth.enabled")) {
                jedisPool = new JedisPool(new JedisPoolConfig(), Shards.instance.getConfig().getString("redis.host"), Shards.instance.getConfig().getInt("redis.port"), 20000, null, Shards.instance.getConfig().getInt("redis.id", 1));
            } else {
                jedisPool = new JedisPool(new JedisPoolConfig(), Shards.instance.getConfig().getString("redis.host"), Shards.instance.getConfig().getInt("redis.port"), 20000, Shards.instance.getConfig().getString("redis-auth.username"), Shards.instance.getConfig().getString("redis-auth.password"), Shards.instance.getConfig().getInt("redis.id", 1));
            }
        } catch (Exception e) {
            jedisPool = null;
            e.printStackTrace();
            Shards.instance.getLogger().warning("Couldn't connect to a Redis database at " + Shards.instance.getConfig().getString("redis.host") + ".");
        }
    }

    public void close(){
        jedisPool.close();
    }

    public <T> T runRedisCommand(RedisCommand<T> redisCommand) {

        T result;
        try (Jedis jedis = this.jedisPool.getResource()) {
            result = redisCommand.execute(jedis);
        }

        return result;
    }

    public void setData(String key, String value){
        runRedisCommand(r -> {
            r.set(key, value);
            return null;
        });
    }

    public String getData(String key){
        return runRedisCommand(r -> r.get(key));
    }

    public Boolean ifExists(String key){
        return runRedisCommand(r -> r.exists(key));
    }
}
