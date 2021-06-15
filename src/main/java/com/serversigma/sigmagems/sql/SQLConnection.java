package com.serversigma.sigmagems.sql;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public abstract class SQLConnection {

    @Getter private Connection connection;

    private final Plugin plugin;
    private final File SQLFile;
    private final String fileName;

    public SQLConnection(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.SQLFile = new File(plugin.getDataFolder(), fileName);
    }

    @SneakyThrows
    public boolean openConnection() {
        if (hasConnection()) return true;
        if (!SQLFile.exists()) plugin.saveResource(fileName, false);

        connection = DriverManager.getConnection("jdbc:sqlite:" + SQLFile);
        return !connection.isClosed();
    }

    @SneakyThrows
    public void closeConnection() {
        if (hasConnection()) {
            connection.close();
        }
    }

    @SneakyThrows
    public boolean hasConnection() {
        return connection != null && !connection.isClosed();
    }

}