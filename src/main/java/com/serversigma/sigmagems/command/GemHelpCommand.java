package com.serversigma.sigmagems.command;

import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

public class GemHelpCommand {

    @Command(
            name = "gemas.ajuda",
            aliases = {"help"},
            target = CommandTarget.PLAYER,
            usage = "gemas ajuda",
            async = true
    )
    public void cashGiveCommand(Context<Player> sender) {

        Player player = sender.getSender();


        player.sendMessage("");
        player.sendMessage("§2Sigmas Gemas - Menu de Ajuda");
        player.sendMessage("");
        player.sendMessage("§2- /gemas §7(Use para olhar os seus gemas)");
        player.sendMessage("§2- /gemas [jogador] §7(Use para olhar os pontos do jogador.");
        player.sendMessage("§2- /gemas enviar [jogador] [quantia] §7(Use para enviar certa quantia de gemas para jogadores)");
        player.sendMessage("§2- /gemas top §7(Use para ver o top 5 gemas do servidor)");
        player.sendMessage("§2- /key ativar [key] §7(Use para ativar gemas)");
        if (player.hasPermission("sigmapoints.admin")) {
            player.sendMessage("§4- §c/gemas setar [jogador] [quantia]");
            player.sendMessage("§4- §c/gemas resetar [jogador]");
            player.sendMessage("§4- §c/key criar [quantia]");
            player.sendMessage("§4- §c/key lista");
            player.sendMessage("§4- §c/key deletar [key]");
            player.sendMessage("");
        }

    }
}