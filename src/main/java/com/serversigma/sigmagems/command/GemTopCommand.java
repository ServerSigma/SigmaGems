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
            name = "gems.top",
            aliases = {"rank", "ranking"},
            permission = "sigmagems.commands.user",
            target = CommandTarget.PLAYER,
            usage = "/gemas rank"
    )

    public void gemsTopCommand(Context<Player> context) {
        Player player = context.getSender();
        new RankingInventory().openInventory(player);
    }

}