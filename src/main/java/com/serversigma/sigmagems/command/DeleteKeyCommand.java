package com.serversigma.sigmagems.command;

import com.serversigma.sigmagems.manager.KeyManager;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class DeleteKeyCommand {

    private final KeyManager keyManager;

    @Command(
            name = "key.deletar",
            target = CommandTarget.PLAYER,
            permission = "sigmapoints.admin",
            usage = "key deletar <quantia>",
            async = true
    )
    public void deleteKey(Context<Player> sender, String[] args) {

        Player player = sender.getSender();

        if (args.length != 1) {
            player.sendMessage("§cUse: /key deletar <key>");
            return;
        }
        if (keyManager.contains(args[0])) {
            keyManager.deleteKey(args[0]);
            player.sendMessage("§cVocê deletou a key: " + args[0]);
        } else {
            player.sendMessage("§cKey inválida.");
        }
    }
}
