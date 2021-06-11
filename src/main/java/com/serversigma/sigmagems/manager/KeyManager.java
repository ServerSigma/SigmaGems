package com.serversigma.sigmagems.manager;

import com.serversigma.sigmagems.model.Key;
import com.serversigma.sigmagems.sql.SQLProvider;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
public class KeyManager {

    private final SQLProvider provider;

    public void addKey(String key, double amount) {
        provider.update("insert into keys(key,quantia) values (?,?)", key, amount);
    }

    public void deleteKey(String key) {
        provider.update("delete from keys where key=?", key);
    }

    public int getKeyValue(String key) {
        return provider.query(
                "select * from keys where key=?",
                set -> set.getInt("quantia"),
                key
        ).orElse(0);
    }

    public boolean contains(String key) {
        return provider.query(
                "select * from keys where key=?",
                set -> true,
                key
        ).orElse(false);
    }

    public Stream<Key> getKeys() {
        return provider.map("SELECT * FROM `keys` ", it -> {
            String key = it.getString("key");
            double amount = it.getDouble("quantia");
            return new Key(key, amount);
        }).get();
    }

}