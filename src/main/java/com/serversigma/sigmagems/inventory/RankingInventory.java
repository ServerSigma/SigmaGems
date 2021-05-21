package com.serversigma.sigmagems.inventory;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.serversigma.sigmagems.manager.GemsManager;
import com.serversigma.sigmagems.utilitie.ItemComposer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class RankingInventory extends SimpleInventory {

    private final GemsManager gemsManager;

    public RankingInventory(GemsManager gemsManager) {
        super(
                "sigmagems.inventory",
                "§aRanking de Gemas",
                27
        );
        this.gemsManager = gemsManager;
    }

    @Override
    protected void configureInventory(Viewer viewer, InventoryEditor editor) {


        AtomicInteger slot = new AtomicInteger(11);
        gemsManager.getTops().forEach(r -> {

            UUID uuid = UUID.fromString(r.getId());
            String playerName = Bukkit.getOfflinePlayer(uuid).getName();

            ItemStack headItem = new ItemComposer(Material.SKULL_ITEM, 1, (short) 3)
                    .setSkullOwner(playerName)
                    .setName("§a" + playerName)
                    .setLore(
                            "§r",
                            "§aGemas: §2" + r.getGems(),
                            "§r").build();
            InventoryItem inventoryItem = InventoryItem.of(headItem);
            slot.getAndIncrement();
            editor.setItem(slot.get(), inventoryItem);
        });
    }
}