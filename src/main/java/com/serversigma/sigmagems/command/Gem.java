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
@SuppressWarnings("unused")
public class Gem {

    private final GemsCache cache;
    private final GemsManager gemsManager;

    @Command(
            name = "gemas",
            aliases = {"gema", "gems"},
            target = CommandTarget.PLAYER,
            usage = "gemas"
    )

    public void gemsViewCommand(Context<Player> context, @Optional String[] args) {

        Player player = context.getSender();

        if (args == null) {
            if (!cache.getCachedPlayers().containsKey(player.getUniqueId())) {
                cache.setGems(player.getUniqueId(), gemsManager.getGems(player.getUniqueId()));
            }
            player.sendMessage(String.format("§5[Gemas] §dAtualmente você tem §7%s §dgemas.",
                    NumberUtils.format(cache.getGems(player.getUniqueId()))));
            return;
        }

        if (args.length == 1) {

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                player.sendMessage("§cJogador não encontrado.");
                return;
            }

            if (!cache.getCachedPlayers().containsKey(target.getUniqueId())) {
                cache.setGems(target.getUniqueId(), gemsManager.getGems(target.getUniqueId()));
            }

            player.sendMessage(String.format("§5[Gemas] §dO jogador §7%s §dtem §7%s §dgemas.",
                    target.getName(), NumberUtils.format(cache.getGems(target.getUniqueId()))));
        }
    }

}