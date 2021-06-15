package com.serversigma.sigmagems.listener;

import com.serversigma.sigmagems.manager.CacheManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

@RequiredArgsConstructor
public class PlayerQuitListener implements Listener {

    private final CacheManager cacheManager;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        cacheManager.save(uuid);
        cacheManager.uncachePlayer(uuid);
    }

}