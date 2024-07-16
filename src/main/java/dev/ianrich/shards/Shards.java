package dev.ianrich.shards;

import dev.ianrich.shards.bstats.Metrics;
import dev.ianrich.shards.command.ShardCommand;
import dev.ianrich.shards.command.ShardTabCompleter;
import dev.ianrich.shards.command.ShardsCommand;
import dev.ianrich.shards.hook.ShardPlaceholderExpansion;
import dev.ianrich.shards.profile.ProfileManager;
import dev.ianrich.shards.profile.ShardProfile;
import dev.ianrich.shards.redis.RedisHandler;
import dev.ianrich.shards.task.AutoSaveTask;
import lombok.Getter;
import me.kaboom.hermes.Hermes;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Shards extends JavaPlugin {

    public static Shards instance;

    public ProfileManager profileManager;
    public RedisHandler redisHandler;

    public AutoSaveTask autoSaveTask;

    public Metrics metrics;

    @Override
    public void onEnable(){
        instance = this;
        saveDefaultConfig();

        redisHandler = new RedisHandler();
        profileManager = new ProfileManager();

        // We don't register a really cool way anymore :(
        getCommand("shard").setExecutor(new ShardCommand());
        getCommand("shard").setTabCompleter(new ShardTabCompleter());

        getCommand("shards").setExecutor(new ShardsCommand());
        getCommand("shards").setTabCompleter(new ShardTabCompleter());

        autoSaveTask = new AutoSaveTask(); // Guess who forgot to initialize this the first time... - Ian
        autoSaveTask.runTaskTimer(this, 20L, 20L*getConfig().getInt("auto-save.time")); // I don't actually know how long this is, probably fine tho...

        metrics = new Metrics(this, 22660);

        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            new ShardPlaceholderExpansion().register(); // We will find a solution when the time comes... - Ian
        }

    }

    @Override
    public void onDisable(){
        Bukkit.getLogger().info("[Shards] Profile-Save started...");
        Shards.instance.getProfileManager().getProfiles().forEach(ShardProfile::saveProfile);
        Bukkit.getLogger().info("[Shards] Profile-Save finished (" + Shards.instance.getProfileManager().getProfiles().size() + " profiles!)");
        Hermes.jedisPool.close();
        Bukkit.getLogger().info("[Hermes] Disconnected from the Redis Database!");
    }

}
