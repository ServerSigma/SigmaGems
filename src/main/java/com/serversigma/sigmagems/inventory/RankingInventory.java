package com.serversigma.sigmagems.inventory;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.serversigma.sigmagems.api.SigmaGemsAPI;
import com.serversigma.sigmagems.utilitie.ItemComposer;
import com.serversigma.sigmagems.utilitie.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class RankingInventory extends SimpleInventory {

    public RankingInventory() {
        super(
                "sigmagems.inventory",
                "§8Jogadores com mais gemas",
                9 * 3
        );
    }

    @Override
    protected void configureInventory(Viewer viewer, InventoryEditor editor) {

        AtomicInteger position = new AtomicInteger();
        AtomicInteger slot = new AtomicInteger(11);

        SigmaGemsAPI.getGemsTop().forEach(r -> {

            position.getAndIncrement();
            UUID uuid = UUID.fromString(r.getId());

            String playerName = Bukkit.getOfflinePlayer(uuid).getName();

            ItemStack headItem = new ItemComposer(Material.SKULL_ITEM, 1, (short) 3)
                    .setSkullOwner(playerName)
                    .setName("§7(" + position + ") §5" + playerName)
                    .setLore(
                            "§r",
                            "§fQuantidade: §d◆ " + NumberUtils.format(r.getGems())).build();
            InventoryItem inventoryItem = InventoryItem.of(headItem);
            slot.getAndIncrement();
            editor.setItem(slot.get(), inventoryItem);
        });

    }

}