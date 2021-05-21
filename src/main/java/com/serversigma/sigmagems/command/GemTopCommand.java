package com.serversigma.sigmagems.command;

import com.serversigma.sigmagems.inventory.RankingInventory;
import com.serversigma.sigmagems.manager.GemsManager;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@RequiredArgsConstructor
public class GemTopCommand {

    private final GemsManager gemsManager;
    private final Inventory inventory = Bukkit.createInventory(null, 27, "§aRanking de Gemas");

    @Command(
            name = "gemas.top",
            aliases = {"rank", "ranking"},
            target = CommandTarget.PLAYER,
            usage = "gemas top",
            async = true
    )
    public void cashGiveCommand(Context<Player> contexts) {
        Player player = contexts.getSender();

        RankingInventory rankingInventory = new RankingInventory(gemsManager);
        rankingInventory.openInventory(player);
    }
}