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
public class GemGiveCommand {

    private final GemsCache gemsCache;

    @Command(
            name = "gemas.give",
            aliases = {"add, adicionar"},
            target = CommandTarget.PLAYER,
            permission = "sigmagems.admin",
            usage = "gemas add <player> <amount>"
    )
    public void gemsGive(Context<Player> context, String[] args) {

        Player player = context.getSender();
        if (args.length != 2) {
            player.sendMessage("§cUse: /gemas add <player> <quantia>");
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

        gemsCache.addGems(target.getUniqueId(), amount);
        player.sendMessage(String.format("§2[SigmaGemas] §aVocê adicionou §2%s gemas §apara §2%s",
                NumberUtils.format(amount), target.getName()));

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