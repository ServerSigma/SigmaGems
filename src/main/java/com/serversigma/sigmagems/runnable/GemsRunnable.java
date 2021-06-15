package com.serversigma.sigmagems.runnable;

import com.serversigma.sigmagems.manager.CacheManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class GemsRunnable extends BukkitRunnable {

    private final CacheManager cache;

    @Override
    public void run() {
        cache.saveAll();
    }

}