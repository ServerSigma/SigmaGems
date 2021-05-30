package com.serversigma.sigmagems.cache;

import com.serversigma.sigmagems.sql.SQLProvider;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class GemsCache {

    private final Map<UUID, Integer> cachedPlayers = new HashMap<>();
    private final SQLProvider sqlProvider;

    public int getGems(UUID uuid) {
        return cachedPlayers.get(uuid);
    }

    public void setGems(UUID id, int amount) {
        cachedPlayers.put(id, amount);
    }

    public void addGems(UUID id, int amount) {
       setGems(id, (getGems(id) + amount));
    }

    public void removeGems(UUID id, int amount) {
        setGems(id, (getGems(id) - amount));
    }

}
