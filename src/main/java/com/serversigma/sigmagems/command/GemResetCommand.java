package com.serversigma.sigmagems.command;

import com.serversigma.sigmagems.cache.GemsCache;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class GemResetCommand {

    private final GemsCache gemsCache;

    @Command(
            name = "gemas.reset",
            aliases = {"resetar"},
            target = CommandTarget.PLAYER,
            permission = "sigmagems.admin",
            usage = "gemas resetar <player>",
            async = true
    )
    public void cashReset(Context<Player> context, String[] args) {

        Player player = context.getSender();
        if (args.length != 1) {
            player.sendMessage("§cUse: /gemas resetar <player> ");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage("§cEste jogador está offline.");
            return;
        }

        gemsCache.setGems(target.getUniqueId(), 0);
        player.sendMessage(String.format("§2[SigmaGemas] §aVocê resetou as gemas de %s", target.getName()));

    }
}