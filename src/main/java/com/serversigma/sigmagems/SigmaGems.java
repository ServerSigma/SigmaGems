package com.serversigma.sigmagems;

import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.serversigma.sigmagems.cache.GemsCache;
import com.serversigma.sigmagems.command.*;
import com.serversigma.sigmagems.listener.PlayerJoinListener;
import com.serversigma.sigmagems.listener.PlayerQuitListener;
import com.serversigma.sigmagems.manager.GemsManager;
import com.serversigma.sigmagems.manager.KeyManager;
import com.serversigma.sigmagems.placeholder.GemsPlaceHolder;
import com.serversigma.sigmagems.runnable.GemsRunnable;
import com.serversigma.sigmagems.sql.SQLProvider;
import com.serversigma.sigmagems.sql.SQLTables;
import com.serversigma.sigmagems.utilitie.InteractChat;
import lombok.Getter;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.bukkit.command.executor.BukkitSchedulerExecutor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class SigmaGems extends JavaPlugin {

    @Getter
    public static SigmaGems instance;
    @Getter
    private GemsManager gemsManager;
    @Getter
    private GemsCache gemsCache;

    private SQLProvider provider;
    private BukkitTask gemsRunnable;
    private GemsPlaceHolder placeholder;

    @Override
    public void onEnable() {

        instance = this;

        // SQL
        provider = new SQLProvider(this, "storage.db");
        if (provider.openConnection()) {
            getLogger().info("Connection with SQLite opened with sucessfully.");
            SQLTables tables = new SQLTables(this, provider);
            tables.createTables();
        }


        // Managers
        gemsCache = new GemsCache();
        gemsManager = new GemsManager(this, provider, gemsCache);
        KeyManager keyManager = new KeyManager(provider);

        // Utilities
        InteractChat interactChat = new InteractChat();

        // Frameworks
        BukkitFrame frame = new BukkitFrame(this);
        frame.setExecutor(new BukkitSchedulerExecutor(this));

        // Dependencies
        InventoryManager.enable(this);
        placeholder = new GemsPlaceHolder(this, gemsCache);
        placeholder.register();

        // A redundant save
        gemsManager.saveAll();

        // AutoSave
        gemsRunnable = new GemsRunnable(gemsManager).runTaskTimerAsynchronously(this, 300 * 20, 300 * 20);

        // Listenrs
        registerListeners(
                new PlayerQuitListener(gemsCache, gemsManager),
                new PlayerJoinListener(gemsManager, gemsCache)
        );

        // Commands
        frame.registerCommands(

                // Gems Commands
                new GemTopCommand(),
                new GemHelpCommand(),
                new GemSetCommand(gemsCache),
                new GemPayCommand(gemsCache),
                new GemGiveCommand(gemsCache),
                new GemResetCommand(gemsCache),
                new Gem(gemsCache, gemsManager),

                // Keys Commands
                new KeyCommand(),
                new DeleteKeyCommand(keyManager),
                new UseKeyCommand(keyManager, gemsCache),
                new NewKeyCommand(keyManager, interactChat),
                new ListKeyCommand(keyManager, interactChat)

        );
    }

    @Override
    public void onDisable() {
        try {
            gemsManager.saveAll();
            gemsRunnable.cancel();
            placeholder.unregister();
        } catch (NullPointerException exception) {
            getLogger().severe(exception.getMessage());
        } finally {
            provider.closeConnection();
        }
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

}