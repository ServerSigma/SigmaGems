package com.serversigma.sigmagems.listener;

import com.serversigma.sigmagems.cache.GemsCache;
import com.serversigma.sigmagems.manager.GemsManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class PlayerQuitListener implements Listener {

    private final GemsCache gemsCache;
    private final GemsManager gemsManager;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        gemsManager.setGem(player.getUniqueId(), gemsCache.getGems(player.getUniqueId()));
    }
}