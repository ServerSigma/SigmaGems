package com.serversigma.sigmagems.command;

import com.serversigma.sigmagems.inventory.RankingInventory;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class GemTopCommand {

    @Command(
            name = "gemas.top",
            aliases = {"rank", "ranking"},
            target = CommandTarget.PLAYER,
            usage = "gemas top"
    )

    public void gemsTopCommand(Context<Player> contexts) {
        Player player = contexts.getSender();
        new RankingInventory().openInventory(player);
        player.sendMessage("§7Abrindo menu...");
    }

}