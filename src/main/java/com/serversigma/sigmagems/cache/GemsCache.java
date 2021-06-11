package com.serversigma.sigmagems.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class GemsCache {

    private final Map<UUID, Double> cachedPlayers = new HashMap<>();

    public double getGems(UUID uuid) {
        return cachedPlayers.get(uuid);
    }

    public void setGems(UUID id, double amount) {
        cachedPlayers.put(id, amount);
    }

    public void addGems(UUID id, double amount) {
       setGems(id, (getGems(id) + amount));
    }

    public void removeGems(UUID id, double amount) {
        setGems(id, (getGems(id) - amount));
    }

}
