package com.serversigma.sigmagems.command;


import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

public class KeyCommand {

    @Command(
            name = "key",
            aliases = {"chave"},
            target = CommandTarget.PLAYER,
            usage = "key"
    )

    public void keyCommand(Context<Player> context) {
        Player player = context.getSender();

        player.sendMessage("");
        player.sendMessage("§2Sigmas Gemas - Menu de Chaves");
        player.sendMessage("");
        player.sendMessage("§2- /key ativar [key] §7(Use para ativar pontos)");
        if (player.hasPermission("sigmapoints.admin")) {
            player.sendMessage("§4- §c/key gerar [quantia]");
            player.sendMessage("§4- §c/key deletar [key]");
            player.sendMessage("§4- §c/key lista ");
            player.sendMessage("");
        }
    }
}

