package com.serversigma.sigmagems.command;

import com.serversigma.sigmagems.manager.CacheManager;
import com.serversigma.sigmagems.utilitie.NumberUtils;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
@RequiredArgsConstructor
public class GemPayCommand {

    private final CacheManager cacheManager;

    @Command(
            name = "gems.pay",
            aliases = {"enviar", "send"},
            target = CommandTarget.PLAYER,
            permission = "sigmagems.commands.user",
            usage = "/gemas enviar <jogador> <quantia>"
    )

    public void gemsPayCommand(Context<Player> context, Player target, String amount) {

        Player player = context.getSender();

        double parsed = NumberUtils.parse(amount);

        if (parsed < 0) {
            player.sendMessage("§cDigite uma quantia válida.");
            return;
        }

        if (player.equals(target)) {
            player.sendMessage("§cVocê não pode enviar gemas para você mesmo.");
            return;
        }

        if (cacheManager.get(player.getUniqueId()) < parsed) {
            player.sendMessage("§cVocê não tem gemas suficientes.");
            return;
        }

        cacheManager.take(player.getUniqueId(), parsed);
        cacheManager.increase(target.getUniqueId(), parsed);

        player.sendMessage(String.format("§5[Gemas] §aVocê enviou §7%s §agemas para §7%s§a.",
                NumberUtils.format(parsed), target.getName()));

        target.sendMessage(String.format("§5[Gemas] §aVocê recebeu §7%s §agemas de §7%s§a.",
                NumberUtils.format(parsed), player.getName()));
    }

}