package com.serversigma.sigmagems.command;

import com.serversigma.sigmagems.cache.GemsCache;
import com.serversigma.sigmagems.utilitie.NumberUtils;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class GemPayCommand {

    private final GemsCache gemsCache;

    @Command(
            name = "gemas.enviar",
            aliases = {"pay", "send", "pagar", "transferir"},
            target = CommandTarget.PLAYER,
            usage = "gemas enviar <player> <amount>"
    )

    public void gemsPayCommand(Context<Player> context, String[] args) {

        Player player = context.getSender();
        if (args.length != 2) {
            player.sendMessage("§cUse: /gemas enviar <player> <quantia>");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage("§cJogador offline ou não encontrado.");
            return;
        }

        if (NumberUtils.hasLetter(args[1])) {
            player.sendMessage("§cA quantia digitada é inválida.");
            return;
        }

        int amount = Integer.parseInt(args[1]);

        if (gemsCache.getGems(player.getUniqueId()) < amount) {
            player.sendMessage("§cVocê não tem gemas suficientes.");
            return;
        }

        gemsCache.addGems(target.getUniqueId(), amount);
        gemsCache.removeGems(player.getUniqueId(), amount);

        player.sendMessage(String.format("§2[SigmaGemas] §aVocê enviou §2%s §agemas para §2%s",
                NumberUtils.format(amount), target.getName()));

        target.sendMessage(String.format("§2[SigmaGemas] §aVocê recebeu %s §agemas de §2%s",
                NumberUtils.format(amount), player.getName()));

    }

}