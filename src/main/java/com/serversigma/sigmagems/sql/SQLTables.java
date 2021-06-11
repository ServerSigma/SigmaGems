package com.serversigma.sigmagems.sql;

import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor
public class SQLTables {

    private final Plugin plugin;
    private final SQLProvider provider;

    public SQLTables createTables() {

        int keyResult = provider.update("CREATE TABLE IF NOT EXISTS keys (" +
                "key TEXT PRIMARY KEY, " +
                "quantia INT" +
                ")");

        int gemsResult = provider.update("CREATE TABLE IF NOT EXISTS gemas (" +
                "player varchar(100) PRIMARY KEY, " +
                "quantia INT" +
                ")");

        String keyMsg = keyResult == -1 ?
                "Não foi possivel carregar a tabela 'keys' ou ela já existe."
                : "Tabela KEYS carregada com sucesso!";

        String gemsMsg = gemsResult == -1 ?
                "Não foi possivel carregar a tabela 'gemas' ou ela já existe."
                : "Tabela GEMAS carregada com sucesso!";

        plugin.getLogger().info(keyMsg);
        plugin.getLogger().info(gemsMsg);
        return this;
    }

}