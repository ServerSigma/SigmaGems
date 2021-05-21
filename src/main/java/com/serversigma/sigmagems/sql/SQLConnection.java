package com.serversigma.sigmagems.sql;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.sqlite.JDBC;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

@Getter
public abstract class SQLConnection {

    private Connection con;

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
        if (!getSQLFile().exists()) plugin.saveResource(fileName, false);

        DriverManager.registerDriver(new JDBC());
        con = DriverManager.getConnection("jdbc:sqlite:" + SQLFile);
        return !con.isClosed();
    }

    @SneakyThrows
    public void closeConnection() {
        ConsoleCommandSender sender = Bukkit.getConsoleSender();
        if (hasConnection()) {
            con.close();
            sender.sendMessage("[SigmaPoints] Connection with SQLite closed with sucessfully");
        } else {
            sender.sendMessage("[SigmaPoints] Connection with SQLite already closed.");
        }
    }

    @SneakyThrows
    public boolean hasConnection() {
        return getCon() != null && !getCon().isClosed();
    }
}