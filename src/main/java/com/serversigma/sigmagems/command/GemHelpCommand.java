package com.serversigma.sigmagems.command;

import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class GemHelpCommand {

    @Command(
            name = "gemas.ajuda",
            aliases = {"help"},
            target = CommandTarget.ALL,
            usage = "gemas ajuda"
    )
    public void gemsHelpCommand(Context<Player> sender) {

        sender.sendMessage("");
        sender.sendMessage("§2Sigma Gemas - Menu de Ajuda");
        sender.sendMessage("");
        sender.sendMessage("§2- /gemas §7(Use para olhar os seus gemas)");
        sender.sendMessage("§2- /gemas [jogador] §7(Use para olhar os pontos do jogador.");
        sender.sendMessage("§2- /gemas enviar [jogador] [quantia] §7(Enviar uma quantia de gemas para alguém)");
        sender.sendMessage("§2- /gemas top §7(Use para ver o top 5 gemas do servidor)");
        sender.sendMessage("§2- /key ativar [key] §7(Use para ativar gemas)");

        if (sender instanceof ConsoleCommandSender
                || sender.getSender().hasPermission("sigmapoints.admin")) {
            
            sender.sendMessage("§4- §c/gemas setar [jogador] [quantia]");
            sender.sendMessage("§4- §c/gemas add [jogador] [quantia]");
            sender.sendMessage("§4- §c/gemas save-all");
            sender.sendMessage("§4- §c/gemas resetar [jogador]");
            sender.sendMessage("§4- §c/key criar [quantia]");
            sender.sendMessage("§4- §c/key lista");
            sender.sendMessage("§4- §c/key deletar [key]");
        }
        sender.sendMessage("");

    }
}