package com.serversigma.sigmagems.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class CacheManager {

    private final GemsManager gemsManager;
    private final Map<UUID, Double> cachedPlayers = new HashMap<>();

    public double get(UUID uuid) {
        return cachedPlayers.get(uuid);
    }

    public void cachePlayer(UUID uuid) {
        cachedPlayers.put(uuid, gemsManager.getGems(uuid));
    }

    public void save(UUID uuid) {
        gemsManager.setGems(uuid, cachedPlayers.get(uuid));
    }

    public void uncachePlayer(UUID uuid) {
        cachedPlayers.remove(uuid);
    }

    public void set(UUID id, double amount) {
        cachedPlayers.put(id, amount);
    }

    public void increase(UUID id, double amount) {
       set(id, (get(id) + amount));
    }

    public void take(UUID id, double amount) {
        set(id, (get(id) - amount));
    }

    public void saveAll() {
        if (cachedPlayers.size() > 0) {
            gemsManager.saveAll(cachedPlayers);
        }
    }

    public void saveAndClose() {
        if (cachedPlayers.size() > 0) {
            gemsManager.saveAndClose(cachedPlayers);
        }
    }

}
