package com.serversigma.sigmagems.manager;

import com.serversigma.sigmagems.cache.GemsCache;
import com.serversigma.sigmagems.sql.SQLProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;


@RequiredArgsConstructor
public class GemsManager {

    private final SQLProvider provider;
    private final GemsCache gemsCache;

    public void saveAll() {

        if(gemsCache.getCachedPlayers().isEmpty()) return;

        new Thread(() -> {
            long startTime = System.currentTimeMillis();
            for (Map.Entry<UUID, Integer> map : gemsCache.getCachedPlayers().entrySet()) {
                UUID uuid = map.getKey();
                int amount = map.getValue();
                setGem(uuid, amount);
            }
            long time = System.currentTimeMillis() - startTime;
            Bukkit.getLogger().info("Gems cache saved to database. (" + time + "ms)");
        }).start();
    }


    public void setGem(UUID id, int quantia) {
        if (hasAccount(id)) provider.update("update gemas set quantia=? where player=?", quantia, id.toString());
        else provider.update("insert into gemas(player,quantia) values (?,?)", id.toString(), quantia);
    }

    public boolean hasAccount(UUID id) {
        return provider.query(
                "select player from gemas where player=?",
                set -> true,
                id.toString()
        ).orElse(false);
    }

    public int getGems(UUID id) {
        return provider.query(
                "select quantia from gemas where player=?",
                set -> set.getInt("quantia"),
                id.toString()
        ).orElse(0);
    }

    public void addGems(UUID id, int amount) {
        setGem(id, getGems(id) + amount);
    }

    public void removeGems(UUID id, int amount) {
        setGem(id, getGems(id) - amount);
    }

    public int deletePlayer(UUID id) {
        return provider.update("delete from gemas where player=?", id.toString());
    }

    public Stream<TemporaryUser> getTops() {
        return provider.map("SELECT * FROM `gemas` ORDER BY `quantia` DESC LIMIT 3", it -> {
            String id = it.getString("player");
            int gems = it.getInt("quantia");
            return new TemporaryUser(id, gems);
        }).get();
    }

    @AllArgsConstructor
    @Getter
    public static class TemporaryUser {
        private final String id;
        private final int gems;
    }
}