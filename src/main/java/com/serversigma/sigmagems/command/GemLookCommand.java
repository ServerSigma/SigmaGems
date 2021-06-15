package com.serversigma.sigmagems.command;

import com.serversigma.sigmagems.manager.CacheManager;
import com.serversigma.sigmagems.manager.GemsManager;
import com.serversigma.sigmagems.utilitie.NumberUtils;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class GemLookCommand {

    private final CacheManager cache;
    private final GemsManager gemsManager;

    @Command(
            name = "gems.look",
            aliases = {"ver", "look"},
            target = CommandTarget.PLAYER,
            permission = "sigmagems.commands.user",
            usage = "/gemas ver [jogador]"
    )

    public void gemsViewCommand(Context<Player> context, @Optional Player target) {

        Player player = context.getSender();

        if (target == null) {

            if (!cache.getCachedPlayers().containsKey(player.getUniqueId())) {
                cache.set(player.getUniqueId(), gemsManager.getGems(player.getUniqueId()));
            }

            String amountString = NumberUtils.format(cache.get(player.getUniqueId()));

            player.sendMessage("§5[Gemas] §dAtualmente você tem §7" + amountString + " §dgemas.");
            return;
        }

        if (!cache.getCachedPlayers().containsKey(target.getUniqueId())) {
            cache.set(target.getUniqueId(), gemsManager.getGems(target.getUniqueId()));
        }

        String amountString = NumberUtils.format(cache.get(target.getUniqueId()));
        player.sendMessage("§5[Gemas] §dO jogador §7" + target.getName() + " §dtem §7" + amountString + " §dgemas.");

    }

}