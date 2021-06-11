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
@SuppressWarnings("unused")
public class GemSetCommand {

    private final GemsCache gemsCache;

    @Command(
            name = "gemas.set",
            aliases = {"setar"},
            target = CommandTarget.PLAYER,
            permission = "sigmagems.admin",
            usage = "gemas setar <player> <amount>"
    )

    public void gemsSet(Context<Player> context, String[] args) {

        Player player = context.getSender();

        if (args.length != 2) {
            player.sendMessage("§cUtilize: §7/gemas setar <player> <quantia>");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage("§cJogador não encontrado.");
            return;
        }
        if (NumberUtils.isInvalid(args[1])) {
            player.sendMessage("§cDigite uma quantia valida.");
            return;
        }

        double amount = Double.parseDouble(args[1]);

        gemsCache.setGems(target.getUniqueId(), amount);
        player.sendMessage(String.format("§5[Gemas] §aVocê setou §7%s §agemas para §7%s.",
                NumberUtils.format(amount), target.getName()));

    }

}