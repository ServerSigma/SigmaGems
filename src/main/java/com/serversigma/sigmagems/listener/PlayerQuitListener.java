package com.serversigma.sigmagems.listener;

import com.serversigma.sigmagems.cache.GemsCache;
import com.serversigma.sigmagems.manager.GemsManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

@RequiredArgsConstructor
public class PlayerQuitListener implements Listener {

    private final GemsCache gemsCache;
    private final GemsManager gemsManager;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UUID playerUniqueId = event.getPlayer().getUniqueId();
        gemsManager.setGem(playerUniqueId, gemsCache.getGems(playerUniqueId));
        gemsCache.getCachedPlayers().remove(playerUniqueId);
    }

}