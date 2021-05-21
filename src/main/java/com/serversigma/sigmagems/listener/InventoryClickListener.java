package com.serversigma.sigmagems.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        String inventoryName = event.getInventory().getName();

        if (inventoryName.equalsIgnoreCase("§aRanking de Gemas")) {
            event.setCancelled(true);
        }
    }
}