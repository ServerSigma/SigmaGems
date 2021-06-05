package com.serversigma.sigmagems;

import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.serversigma.sigmagems.cache.GemsCache;
import com.serversigma.sigmagems.command.*;
import com.serversigma.sigmagems.listener.InventoryClickListener;
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

    @Getter public static SigmaGems instance;
    @Getter private GemsCache gemsCache;

    SQLProvider provider;
    KeyManager keyManager;
    BukkitTask gemsRunnable;
    GemsManager gemsManager;
    GemsPlaceHolder placeholder;

    @Override
    public void onEnable() {
        instance = this;
        provider = new SQLProvider(this, "storage.db");
        gemsCache = new GemsCache(provider);
        gemsManager = new GemsManager(provider, gemsCache);
        keyManager = new KeyManager(provider);

        InteractChat interactChat = new InteractChat();

        if (provider.openConnection()) {
            getLogger().info("Connection with SQLite opened with sucessfully.");
            SQLTables tables = new SQLTables(provider);
            tables.createTables();

        }
        gemsManager.saveAll();
        gemsRunnable = new GemsRunnable(gemsManager).runTaskTimer(this, 300 * 20, 300 * 20);
        BukkitFrame frame = new BukkitFrame(this);
        frame.setExecutor(new BukkitSchedulerExecutor(this));

        InventoryManager.enable(this);
        placeholder = new GemsPlaceHolder(this, gemsCache);
        placeholder.register();

        registerListeners(
                new PlayerQuitListener(gemsCache, gemsManager),
                new PlayerJoinListener(gemsManager, gemsCache),
                new InventoryClickListener()
        );

        frame.registerCommands(
                new Gem(gemsCache, gemsManager),
                new GemPayCommand(gemsCache),
                new GemHelpCommand(),
                new GemSaveCommand(gemsManager),
                new GemGiveCommand(gemsCache),
                new GemResetCommand(gemsCache),
                new GemSetCommand(gemsCache),
                new GemTopCommand(gemsManager),

                new KeyCommand(),
                new ListKeyCommand(keyManager, interactChat),
                new NewKeyCommand(keyManager, interactChat),
                new UseKeyCommand(keyManager, gemsCache),
                new DeleteKeyCommand(keyManager)
        );
    }

    @Override
    public void onDisable() {
        gemsRunnable.cancel();
        gemsManager.saveAll();
        placeholder.unregister();

        try {
            provider.closeConnection();
        } catch (Exception exception) {
            getLogger().severe("Não foi possível fechar a conexão: " + exception.getLocalizedMessage());
        }
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

}