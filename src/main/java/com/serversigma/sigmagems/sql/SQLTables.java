package com.serversigma.sigmagems.sql;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;

@RequiredArgsConstructor
public class SQLTables {

    private final SQLProvider provider;

    public SQLTables createTables() {

        int keyResult = provider.update("CREATE TABLE IF NOT EXISTS keys (" +
                "key TEXT PRIMARY KEY, " +
                "quantia INT " +
                ")");

        int cashResult = provider.update("CREATE TABLE IF NOT EXISTS gemas (" +
                "player varchar(50) PRIMARY KEY, " +
                "quantia INT" +
                ")");

        String keyMsg = keyResult == -1 ?
                "§cNão foi possivel carregar a tabela 'keys' ou ela já existe."
                : "§aTabela carregada";

        String cashMsg = cashResult == -1 ?
                "§cNão foi possivel carregar a tabela 'gemas' ou ela já existe."
                : "§aTabela carregada";

        Bukkit.getConsoleSender().sendMessage(keyMsg);
        Bukkit.getConsoleSender().sendMessage(cashMsg);
        return this;
    }
}