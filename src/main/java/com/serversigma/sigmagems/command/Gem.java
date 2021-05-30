package com.serversigma.sigmagems.command;

import com.serversigma.sigmagems.cache.GemsCache;
import com.serversigma.sigmagems.manager.GemsManager;
import com.serversigma.sigmagems.utilitie.NumberUtils;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class Gem {

    private final GemsCache cache;
    private final GemsManager gemsManager;

    @Command(
            name = "gemas",
            aliases = {"gema", "gems"},
            target = CommandTarget.PLAYER,
            usage = "gemas",
            async = true
    )
    public void cashViewCommand(Context<Player> context, @Optional String[] args) {
        Player player = context.getSender();

        if (args == null) {
            if (!cache.getCachedPlayers().containsKey(player.getUniqueId())) {
                cache.setGems(player.getUniqueId(), gemsManager.getGems(player.getUniqueId()));
            }
            player.sendMessage(String.format("§2[SigmaGemas] §aAtualmente você tem §2%s §agemas",
                    NumberUtils.format(cache.getGems(player.getUniqueId()))));
            return;
        }


        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage("§cEste jogador está offline.");
                return;
            }
            player.sendMessage(String.format("§2[SigmaGemas] §aO jogador §2%s §atem §2%s §agemas.",
                    target.getName(), NumberUtils.format(cache.getGems(target.getUniqueId()))));
        }
    }
}