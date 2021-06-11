package com.serversigma.sigmagems.listener;

import com.serversigma.sigmagems.cache.GemsCache;
import com.serversigma.sigmagems.manager.GemsManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {

    private final GemsManager gemsManager;
    private final GemsCache gemsCache;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        Server server = player.getServer();

        if (!gemsManager.hasAccount(player.getUniqueId())) {
            gemsManager.setGem(player.getUniqueId(), 0);
            server.getLogger().info("New player created in database.");
        }
        gemsCache.setGems(player.getUniqueId(), gemsManager.getGems(player.getUniqueId()));
    }

}
