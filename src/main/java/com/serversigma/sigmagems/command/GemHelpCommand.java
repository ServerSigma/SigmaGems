package com.serversigma.sigmagems.command;

import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.command.CommandSender;

@SuppressWarnings("unused")
public class GemHelpCommand {

    @Command(
            name = "gems",
            aliases = {"gemas"},
            target = CommandTarget.PLAYER,
            permission = "sigmagems.commands.user",
            usage = "/gemas"
    )

    public void gemsHelpCommand(Context<CommandSender> context) {

        CommandSender sender = context.getSender();

        sender.sendMessage("");
        sender.sendMessage(" §5SigmaGemas §8- §dMenu de ajuda");
        sender.sendMessage("");
        sender.sendMessage("§5  /gemas top");
        sender.sendMessage("§5  /gemas ver [jogador]");
        sender.sendMessage("§5  /gemas enviar <jogador> <quantia>");

        if (sender.hasPermission("sigmagems.commands.admin")) {
            sender.sendMessage("  §c/gemas set <jogador> <quantia>");
            sender.sendMessage("  §c/gemas add <jogador> <quantia>");
        }

        sender.sendMessage("");

    }

}