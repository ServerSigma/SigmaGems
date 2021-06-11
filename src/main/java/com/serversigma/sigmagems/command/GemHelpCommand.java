package com.serversigma.sigmagems.command;

import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class GemHelpCommand {

    @Command(
            name = "gemas.ajuda",
            aliases = {"help"},
            target = CommandTarget.PLAYER,
            usage = "gemas ajuda"
    )

    public void gemsHelpCommand(Context<Player> sender) {

        sender.sendMessage("");
        sender.sendMessage(" §5SigmaGemas §8- §dMenu de ajuda");
        sender.sendMessage("");
        sender.sendMessage("§5  /gemas §7Visualizar suas gemas.");
        sender.sendMessage("§5  /gemas <jogador> §7Visualizar as gemas de outro jogador.");
        sender.sendMessage("§5  /gemas enviar <jogador> <quantia> §7Enviar gemas para alguém");
        sender.sendMessage("§5  /gemas top §7Visualizar os jogadores com mais gemas.");
        sender.sendMessage("§5  /key ativar <key> §7Ativar uma key.");

        if (sender.getSender().hasPermission("sigmapoints.admin")) {
            sender.sendMessage("§4  §c/gemas setar <jogador> <quantia>");
            sender.sendMessage("§4  §c/gemas add <jogador> <quantia>");
            sender.sendMessage("§4  §c/gemas resetar <jogador]");
            sender.sendMessage("§4  §c/key criar <quantia>");
            sender.sendMessage("§4  §c/key lista");
            sender.sendMessage("§4  §c/key deletar <key>");
        }
        sender.sendMessage("");
    }

}