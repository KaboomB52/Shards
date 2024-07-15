package dev.ianrich.shards;

import dev.ianrich.shards.command.ShardCommand;
import dev.ianrich.shards.command.ShardsCommand;
import dev.ianrich.shards.profile.ProfileManager;
import dev.ianrich.shards.profile.ShardProfile;
import dev.ianrich.shards.redis.RedisHandler;
import dev.ianrich.shards.task.AutoSaveTask;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Shards extends JavaPlugin {

    public static Shards instance;

    public ProfileManager profileManager;
    public RedisHandler redisHandler;

    public AutoSaveTask autoSaveTask;

    @Override
    public void onEnable(){
        instance = this;
        saveDefaultConfig();

        redisHandler = new RedisHandler();
        profileManager = new ProfileManager();

        // We register a really cool way...
        Bukkit.getCommandMap().register("shards", new ShardCommand());
        Bukkit.getCommandMap().register("shards", new ShardsCommand());

        autoSaveTask = new AutoSaveTask(); // Guess who forgot to initialize this the first time... - Ian
        autoSaveTask.runTaskTimer(this, 20L, 20L*getConfig().getInt("auto-save.time")); // I don't actually know how long this is, probably fine tho...
    }

    @Override
    public void onDisable(){
        Bukkit.getLogger().info("Profile-Save started...");
        Shards.instance.getProfileManager().getProfiles().forEach(ShardProfile::saveProfile);
        Bukkit.getLogger().info("Profile-Save finished (" + Shards.instance.getProfileManager().getProfiles().size() + " profiles!)");

        getRedisHandler().close();
        Bukkit.getLogger().info("Disconnected from the Redis Database!");
    }

}
