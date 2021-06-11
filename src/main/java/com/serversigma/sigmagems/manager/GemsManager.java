package com.serversigma.sigmagems.manager;

import com.serversigma.sigmagems.cache.GemsCache;
import com.serversigma.sigmagems.sql.SQLProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class GemsManager {

    private final Plugin plugin;
    private final SQLProvider provider;
    private final GemsCache gemsCache;

    public void saveAll() {
        if(gemsCache.getCachedPlayers().isEmpty()) return;
        new Thread(() -> {
            long startTime = System.currentTimeMillis();
            for (Map.Entry<UUID, Double> entry: gemsCache.getCachedPlayers().entrySet()) {
                setGem(entry.getKey(), entry.getValue());
            }
            long time = System.currentTimeMillis() - startTime;
            String log = "Saved " + gemsCache.getCachedPlayers().size() + " accounts in " + time + "ms";
            plugin.getLogger().info(log);
        }).start();
    }

    public void setGem(UUID id, double quantia) {
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

    public double getGems(UUID id) {
        return provider.query(
                "select quantia from gemas where player=?",
                set -> set.getDouble("quantia"),
                id.toString()
        ).orElse(0.0);
    }

    public void addGems(UUID id, double amount) {
        setGem(id, getGems(id) + amount);
    }

    public void removeGems(UUID id, double amount) {
        setGem(id, getGems(id) - amount);
    }

    public int deletePlayer(UUID id) {
        return provider.update("delete from gemas where player=?", id.toString());
    }

    public Stream<TemporaryUser> getGemsTop() {
        return provider.map("SELECT * FROM `gemas` ORDER BY `quantia` DESC LIMIT 3", it -> {
            String id = it.getString("player");
            double gems = it.getDouble("quantia");
            return new TemporaryUser(id, gems);
        }).get();
    }

    @Getter
    @AllArgsConstructor
    public static class TemporaryUser {
        private final String id;
        private final double gems;
    }

}