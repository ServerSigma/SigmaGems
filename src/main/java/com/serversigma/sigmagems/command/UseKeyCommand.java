package com.serversigma.sigmagems.command;

import com.serversigma.sigmagems.cache.GemsCache;
import com.serversigma.sigmagems.manager.KeyManager;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class UseKeyCommand {

    private final KeyManager keyManager;
    private final GemsCache gemsCache;

    @Command(
            name = "key.ativar",
            aliases = {"use"},
            target = CommandTarget.PLAYER,
            usage = "key ativar",
            async = true
    )
    public void keyListCommand(Context<Player> sender, String[] args) {

        Player player = sender.getSender();
        if(args.length != 1) {
            player.sendMessage("§cUse: /key ativar [key]");
        }

        String key = args[0];
        if(keyManager.contains(key)) {

            int amount = keyManager.getKeyValue(key);
            gemsCache.addGems(player.getUniqueId(), amount);
            player.sendMessage("§aParabéns, você ativou uma chave de gemas!");
            player.playSound(player.getLocation(), Sound.ENDERDRAGON_DEATH, 1f, 1f);
            keyManager.deleteKey(key);
        } else {
            player.sendMessage("§cKey inválida");
        }
    }
}
