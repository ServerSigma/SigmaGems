package com.serversigma.sigmagems.sql;

import com.serversigma.sigmagems.SigmaGems;
import lombok.SneakyThrows;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

public class SQLProvider extends SQLConnection {

    public SQLProvider(Plugin plugin, String fileName) {
        super(plugin, fileName);
    }

    @SneakyThrows
    public <K> Optional<K> query(String sql, SQLConsumer<ResultSet, K> consumer, Object... objects){
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            syncObjects(statement, objects);

            try (ResultSet set = statement.executeQuery()) {
                K result = set != null && set.next() ? consumer.apply(set) : null;
                return Optional.ofNullable(result);
            }
        }
    }

    @SneakyThrows
    public <K> Optional<Stream<K>> map(String sql, SQLConsumer<ResultSet, K> consumer, Object... objects) {
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            syncObjects(statement, objects);
            try (ResultSet set = statement.executeQuery()) {
                List<K> objectsResult = new ArrayList<>();
                while (set.next()) {
                    objectsResult.add(consumer.apply(set));
                }
                return Optional.ofNullable(objectsResult.stream());
            }
        }
    }

    @SneakyThrows
    public int update(String sql, Object... objects){
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            syncObjects(statement, objects);
            return statement.executeUpdate();
        }
    }

    private void syncObjects(PreparedStatement statement, Object... objects) throws SQLException {
        Iterator<Object> iterator = Arrays.stream(objects).iterator();
        for (int i = 1; iterator.hasNext(); i++) {
            statement.setObject(i, iterator.next());
        }
    }

    public void createTables() {

        int gemsResult = update("CREATE TABLE IF NOT EXISTS gems (" +
                "player varchar(100) PRIMARY KEY, " +
                "amount INT" +
                ")");

        SigmaGems.getInstance().getLogger().info(gemsResult == -1 ?
                "Não foi possível criar ou carregar os dados do SQLite."
                : "Dados do SQLite carregados com sucesso!");

    }

}