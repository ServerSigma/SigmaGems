package com.serversigma.sigmagems.command;

import com.serversigma.sigmagems.cache.GemsCache;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class GemGiveCommand {

    private final GemsCache gemsCache;

    @Command(
            name = "gemas.enviar",
            aliases = {"send", "pagar", "transferir"},
            target = CommandTarget.PLAYER,
            usage = "gemas enviar <player> <amount>",
            async = true
    )
    public void cashGiveCommand(Context<Player> context, String[] args) {

        Player player = context.getSender();
        if (args.length != 2) {
            player.sendMessage("§cUse: /gemas enviar <player> <quantia>");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage("§cEste jogador está offline.");
            return;
        }
        if (hasLetter(args[1])) {
            player.sendMessage("§cDigite uma quantia valida.");
            return;
        }
        int amount = Integer.parseInt(args[1]);

        if (gemsCache.getGems(player.getUniqueId()) < amount) {
            player.sendMessage("§cVocê não tem gemas suficientes.");
            return;
        }


        gemsCache.addGems(target.getUniqueId(), amount);
        gemsCache.removeGems(player.getUniqueId(), amount);
        player.sendMessage(String.format("§2[SigmaGemas] §aVocê enviou §2%d §agemas para §2%s", amount, target.getName()));
        target.sendMessage(String.format("§2[SigmaGemas] §aVocê recebeu %d §agemas de §2%s", amount, player.getName()));

    }

    public boolean hasLetter(String data) {
        try {
            Integer.parseInt(data);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}
