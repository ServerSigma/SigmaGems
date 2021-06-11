package com.serversigma.sigmagems.command;

import com.serversigma.sigmagems.manager.KeyManager;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class DeleteKeyCommand {

    private final KeyManager keyManager;

    @Command(
            name = "key.deletar",
            target = CommandTarget.PLAYER,
            permission = "sigmapoints.admin",
            usage = "key deletar <key>"
    )

    public void deleteKey(Context<Player> sender, String[] args) {

        Player player = sender.getSender();

        if (args.length != 1) {
            player.sendMessage("§cUtilize: §7/key deletar <chave>");
            return;
        }

        if (keyManager.contains(args[0])) {
            keyManager.deleteKey(args[0]);
            player.sendMessage("§aVocê deletou a chave: " + args[0]);
        } else {
            player.sendMessage("§cChave inválida.");
        }
    }

}