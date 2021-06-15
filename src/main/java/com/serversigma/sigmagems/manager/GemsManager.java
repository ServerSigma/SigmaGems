package com.serversigma.sigmagems.manager;

import com.serversigma.sigmagems.SigmaGems;
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


    public void saveAll(Map<UUID, Double> cachedPlayers) {
        new Thread(() -> {
            long startTime = System.currentTimeMillis();
            for (Map.Entry<UUID, Double> entry : cachedPlayers.entrySet()) {
                setGems(entry.getKey(), entry.getValue());
            }
            long time = System.currentTimeMillis() - startTime;
            String log = "Saved " + cachedPlayers.size() + " accounts in " + time + "ms";
            SigmaGems.getInstance().getLogger().info(log);
        }).start();
    }

    public void saveAndClose(Map<UUID, Double> cachedPlayers) {
        for (Map.Entry<UUID, Double> entry : cachedPlayers.entrySet()) {
            setGems(entry.getKey(), entry.getValue());
        }
        provider.closeConnection();
    }

    public void setGems(UUID id, double quantia) {
        if (hasAccount(id)) {
            provider.update("update gems set amount = ? where player = ?", quantia, id.toString());
        } else {
            provider.update("insert into gems(player, amount) values (?,?)", id.toString(), quantia);
        }
    }

    public boolean hasAccount(UUID id) {
        return provider.query(
                "select player from gems where player=?",
                set -> true,
                id.toString()
        ).orElse(false);
    }

    public double getGems(UUID id) {
        return provider.query(
                "select amount from gems where player=?",
                set -> set.getDouble("amount"),
                id.toString()
        ).orElse(0.0);
    }

    public void addGems(UUID id, double amount) {
        setGems(id, getGems(id) + amount);
    }

    public void removeGems(UUID id, double amount) {
        setGems(id, getGems(id) - amount);
    }

    public int deletePlayer(UUID id) {
        return provider.update("delete from gems where player=?", id.toString());
    }

    public Stream<TemporaryUser> getGemsTop() {
        return provider.map("SELECT * FROM `gems` ORDER BY `amount` DESC LIMIT 5", it -> {
            String id = it.getString("player");
            double gems = it.getDouble("amount");
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