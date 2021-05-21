package com.serversigma.sigmagems;

import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.serversigma.sigmagems.cache.GemsCache;
import com.serversigma.sigmagems.command.*;
import com.serversigma.sigmagems.listener.InventoryClickListener;
import com.serversigma.sigmagems.listener.PlayerJoinListener;
import com.serversigma.sigmagems.listener.PlayerQuitListener;
import com.serversigma.sigmagems.manager.GemsManager;
import com.serversigma.sigmagems.manager.KeyManager;
import com.serversigma.sigmagems.placeholder.PointsPlaceHolderHook;
import com.serversigma.sigmagems.runnable.GemsRunnable;
import com.serversigma.sigmagems.sql.SQLProvider;
import com.serversigma.sigmagems.sql.SQLTables;
import com.serversigma.sigmagems.utilitie.InteractChat;
import com.serversigma.sigmagems.utilitie.MessageUtils;
import me.bristermitten.pdm.PluginDependencyManager;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.bukkit.command.executor.BukkitSchedulerExecutor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SigmaGems extends JavaPlugin {

    private SQLProvider provider;
    private GemsManager gemsManager;

    @Override
    public void onEnable() {
        provider = new SQLProvider(this, "storage.db");
        GemsCache gemsCache = new GemsCache(provider);
        gemsManager = new GemsManager(provider, gemsCache);
        PointsPlaceHolderHook placeholder = new PointsPlaceHolderHook(this, gemsCache);
        placeholder.register();
        KeyManager keyManager = new KeyManager(provider);
        MessageUtils messageUtils = new MessageUtils();

        getLogger().info("Placeholder registered with sucessfully.");
        InteractChat interactChat = new InteractChat();

        PluginDependencyManager.of(this)
                .loadAllDependencies()
                .exceptionally(throwable -> {
                    getLogger().severe(throwable.getMessage());
                    return null;
                }).join();

        if (provider.openConnection()) {
            getLogger().info("Connection with SQLite opened with sucessfully.");
            SQLTables tables = new SQLTables(provider);
            tables.createTables();

        }
        gemsManager.saveAll();
        new GemsRunnable(gemsManager).runTaskTimer(this, 20 * 30, 20 * 30);
        BukkitFrame frame = new BukkitFrame(this);
        frame.setExecutor(new BukkitSchedulerExecutor(this));


        registerListeners(
                new PlayerQuitListener(gemsCache, gemsManager),
                new PlayerJoinListener(gemsManager, gemsCache),
                new InventoryClickListener()
        );

        frame.registerCommands(
                new Gem(gemsCache, gemsManager),
                new GemGiveCommand(gemsCache),
                new GemHelpCommand(),
                new GemResetCommand(gemsCache),
                new GemSetCommand(gemsCache),
                new GemTopCommand(gemsManager),

                new KeyCommand(),
                new ListKeyCommand(keyManager, interactChat),
                new NewKeyCommand(keyManager, interactChat),
                new UseKeyCommand(keyManager, gemsCache),
                new DeleteKeyCommand(keyManager)

        );
        InventoryManager.enable(this);

    }

    @Override
    public void onDisable() {
        gemsManager.saveAll();
        provider.closeConnection();
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }
}
