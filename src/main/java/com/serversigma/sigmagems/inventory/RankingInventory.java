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

import java.text.DecimalFormat;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class RankingInventory extends SimpleInventory {

    private final GemsManager gemsManager;

    public RankingInventory(GemsManager gemsManager) {
        super(
                "sigmagems.inventory",
                "§8Jogadores com mais gemas",
                27
        );
        this.gemsManager = gemsManager;
    }

    @Override
    protected void configureInventory(Viewer viewer, InventoryEditor editor) {

        AtomicInteger position = new AtomicInteger();
        AtomicInteger slot = new AtomicInteger(11);
        gemsManager.getTops().forEach(r -> {

            position.getAndIncrement();
            UUID uuid = UUID.fromString(r.getId());

            String playerName = Bukkit.getOfflinePlayer(uuid).getName();

            ItemStack headItem = new ItemComposer(Material.SKULL_ITEM, 1, (short) 3)
                    .setSkullOwner(playerName)
                    .setName("§7(" + position + ") §5" + playerName)
                    .setLore(
                            "§r",
                            "§fQuantidade: §d◆ " + format(r.getGems())).build();
            InventoryItem inventoryItem = InventoryItem.of(headItem);
            slot.getAndIncrement();
            editor.setItem(slot.get(), inventoryItem);
        });
    }

    public String format(Number number) {
        char[] suffix = {' ', 'k', 'M', 'B', 'T', 'Q'};
        long numValue = number.longValue();
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return new DecimalFormat("#0.0").format(numValue / Math.pow(10, base * 3)) + suffix[base];
        } else {
            return new DecimalFormat("#,##0").format(numValue);
        }
    }
}