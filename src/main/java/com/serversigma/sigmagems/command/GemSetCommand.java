package com.serversigma.sigmagems.command;

import com.serversigma.sigmagems.manager.CacheManager;
import com.serversigma.sigmagems.utilitie.NumberUtils;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class GemSetCommand {

    private final CacheManager cacheManager;

    @Command(
            name = "gems.set",
            aliases = {"setar"},
            target = CommandTarget.PLAYER,
            permission = "sigmagems.commands.admin",
            usage = "/gemas setar <jogador> <quantia>"
    )

    public void gemsSet(Context<Player> context, Player target, String amount) {

        Player player = context.getSender();

        double parsed = NumberUtils.parse(amount);

        if (parsed < 0) {
            player.sendMessage("§cDigite uma quantia valida.");
            return;
        }

        cacheManager.set(target.getUniqueId(), parsed);

        player.sendMessage(String.format("§5[Gemas] §aVocê definiu as gemas de §7%s §apara §7%s§a.",
                target.getName(), NumberUtils.format(parsed)));

    }

}