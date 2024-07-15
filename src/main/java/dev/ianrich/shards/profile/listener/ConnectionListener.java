package dev.ianrich.shards.profile.listener;

import dev.ianrich.shards.Shards;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(!Shards.instance.getProfileManager().isLoaded(event.getPlayer())){
            Shards.instance.getProfileManager().getOrCreateProfile(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Shards.instance.getProfileManager().getOrCreateProfile(event.getPlayer()).saveProfile();
    }
}
