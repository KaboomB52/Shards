package dev.ianrich.shards.task;

import dev.ianrich.shards.Shards;
import dev.ianrich.shards.profile.ShardProfile;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

// Quick AutoSave for Profiles (async baby) - Ian

public class AutoSaveTask extends BukkitRunnable {

    @Override
    public void run() {

        Bukkit.getLogger().info("Auto-Save starting...");
        Shards.instance.getProfileManager().getProfiles().forEach(ShardProfile::saveProfile);
        Bukkit.getLogger().info("Auto-Save finished (" + Shards.instance.getProfileManager().getProfiles().size() + " profiles!)");
    }

}
