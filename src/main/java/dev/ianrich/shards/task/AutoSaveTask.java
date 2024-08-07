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
        Bukkit.getOnlinePlayers().forEach(p-> Shards.instance.getProfileManager().getOrCreateProfile(p).saveProfile());
        Bukkit.getLogger().info("Auto-Save finished (" + Shards.instance.getProfileManager().getProfiles().size() + " profiles!)");
    }

}
