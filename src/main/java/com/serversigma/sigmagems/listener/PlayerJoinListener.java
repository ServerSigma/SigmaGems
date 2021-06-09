package com.serversigma.sigmagems.listener;

import com.serversigma.sigmagems.cache.GemsCache;
import com.serversigma.sigmagems.manager.GemsManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {

    private final GemsManager gemsManager;
    private final GemsCache gemsCache;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!gemsManager.hasAccount(event.getPlayer().getUniqueId())) {
            gemsManager.setGem(event.getPlayer().getUniqueId(), 0);
            Bukkit.getConsoleSender().sendMessage("[SigmaGems] New player created in database.");
        }
        gemsCache.setGems(event.getPlayer().getUniqueId(), gemsManager.getGems(event.getPlayer().getUniqueId()));
    }
}
