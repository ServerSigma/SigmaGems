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
public class GemGiveCommand {

    private final CacheManager cacheManager;

    @Command(
            name = "gems.give",
            aliases = {"add", "adicionar"},
            target = CommandTarget.PLAYER,
            permission = "sigmagems.commands.admin",
            usage = "/gemas adicionar <jogador> <quantia>"
    )

    public void gemsGive(Context<Player> context, Player target, String amount) {

        Player player = context.getSender();
        double parsed = NumberUtils.parse(amount);

        if (parsed < 0) {
            player.sendMessage("§cDigite uma quantia válida.");
            return;
        }

        cacheManager.increase(target.getUniqueId(), parsed);

        String parsedString = NumberUtils.format(parsed);

        player.sendMessage(String.format("§5[Gemas] §aVocê adicionou §7%s §agemas para §7%s§a.",
                parsedString, target.getName()));

        target.sendMessage(String.format("§5[Gemas] §aForam adicionadas §7%s §agemas em sua conta.",
                parsedString));

    }

}