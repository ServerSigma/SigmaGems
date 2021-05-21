package com.serversigma.sigmagems.command;

import com.serversigma.sigmagems.cache.GemsCache;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class GemSetCommand {

    private final GemsCache gemsCache;

    @Command(
            name = "gemas.set",
            aliases = {"setar"},
            target = CommandTarget.PLAYER,
            permission = "sigmagems.admin",
            usage = "gemas setar <player> <amount>",
            async = true
    )
    public void cashSet(Context<Player> context, String[] args) {

        Player player = context.getSender();
        if (args.length != 2) {
            player.sendMessage("§cUse: /gemas setar <player> <quantia>");
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

        gemsCache.setGems(target.getUniqueId(), amount);
        player.sendMessage(String.format("§2[SigmaGemas] §aVocê setou §2%d §agemas para §2%s", amount, target.getName()));

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

