package com.serversigma.sigmagems.runnable;

import com.serversigma.sigmagems.manager.GemsManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class GemsRunnable extends BukkitRunnable {

    private final GemsManager gemsManager;

    @Override
    public void run() {
        gemsManager.saveAll();
    }
}