package com.serversigma.sigmagems;

import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.serversigma.sigmagems.command.*;
import com.serversigma.sigmagems.listener.PlayerJoinListener;
import com.serversigma.sigmagems.listener.PlayerQuitListener;
import com.serversigma.sigmagems.manager.CacheManager;
import com.serversigma.sigmagems.manager.GemsManager;
import com.serversigma.sigmagems.placeholder.GemsPlaceHolder;
import com.serversigma.sigmagems.runnable.GemsRunnable;
import com.serversigma.sigmagems.sql.SQLProvider;
import lombok.Getter;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.bukkit.command.executor.BukkitSchedulerExecutor;
import me.saiintbrisson.minecraft.command.message.MessageHolder;
import me.saiintbrisson.minecraft.command.message.MessageType;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class SigmaGems extends JavaPlugin {

    private BukkitTask gemsRunnable;
    private GemsPlaceHolder placeholder;

    @Getter private GemsManager gemsManager;
    @Getter private CacheManager cacheManager;

    @Override
    public void onEnable() {

        // SQL
        SQLProvider provider = new SQLProvider(this, "storage.db");
        if (provider.openConnection()) {
            provider.createTables();
            getLogger().info("Connection with SQLite opened with sucessfully.");
        }


        // Managers
        gemsManager = new GemsManager(this, provider);
        cacheManager = new CacheManager(gemsManager);

        // Frameworks
        BukkitFrame frame = new BukkitFrame(this);
        frame.setExecutor(new BukkitSchedulerExecutor(this));

        MessageHolder messageHolder = frame.getMessageHolder();
        messageHolder.setMessage(MessageType.ERROR, "§cOcorreu um erro ao executar o comando.");
        messageHolder.setMessage(MessageType.INCORRECT_USAGE, "§cUso incorreto, utilize: §f{usage}");
        messageHolder.setMessage(MessageType.INCORRECT_TARGET, "§cVocê não pode executar esse comando.");
        messageHolder.setMessage(MessageType.NO_PERMISSION, "§cVocê não tem permissão para utilizar esse comando.");

        // Dependencies
        InventoryManager.enable(this);
        placeholder = new GemsPlaceHolder(this, cacheManager);
        placeholder.register();

        // AutoSave
        gemsRunnable = new GemsRunnable(cacheManager).runTaskTimerAsynchronously(this, 300 * 20, 300 * 20);

        // Listenrs
        registerListeners(
                new PlayerQuitListener(cacheManager),
                new PlayerJoinListener(cacheManager)
        );

        // Commands
        frame.registerCommands(

                // Gems Commands
                new GemTopCommand(),
                new GemHelpCommand(),
                new GemSetCommand(cacheManager),
                new GemPayCommand(cacheManager),
                new GemGiveCommand(cacheManager),
                new GemLookCommand(cacheManager, gemsManager)

        );
    }

    @Override
    public void onDisable() {
        gemsRunnable.cancel();
        placeholder.unregister();
        cacheManager.saveAndClose();
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    public static SigmaGems getInstance() {
        return getPlugin(SigmaGems.class);
    }

}