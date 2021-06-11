package com.serversigma.sigmagems.command;


import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class KeyCommand {

    @Command(
            name = "key",
            aliases = {"chave"},
            target = CommandTarget.PLAYER,
            usage = "key"
    )

    public void keyCommand(Context<Player> sender) {

        sender.sendMessage("");
        sender.sendMessage("§3Sigmas Gemas §8- §bMenu de chaves");
        sender.sendMessage("");
        sender.sendMessage("§b /key ativar <key> §7Ativar uma chave de gemas.");

        if (sender.getSender().hasPermission("sigmagemas.admin")) {
            sender.sendMessage("§c /key gerar <quantia>");
            sender.sendMessage("§c /key deletar <chave>");
            sender.sendMessage("§c /key lista");
        }
        sender.sendMessage("");

    }

}

