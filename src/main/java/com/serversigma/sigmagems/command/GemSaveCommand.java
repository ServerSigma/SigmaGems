package com.serversigma.sigmagems.command;

import com.serversigma.sigmagems.manager.GemsManager;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class GemSaveCommand {

    private final GemsManager gemsManager;

    @Command(
            name = "gemas.save",
            aliases = {"save-all"},
            target = CommandTarget.ALL,
            permission = "sigmagems.admin",
            usage = "gemas save-all"
    )
    public void gemsSaveCommand(Context<Player> sender) {
        gemsManager.saveAll();
        sender.getSender().sendMessage("§aDados salvos com sucesso!");
    }

}