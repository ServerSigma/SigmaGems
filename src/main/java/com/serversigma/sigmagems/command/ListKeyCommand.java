package com.serversigma.sigmagems.command;

import com.serversigma.sigmagems.manager.KeyManager;
import com.serversigma.sigmagems.utilitie.InteractChat;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class ListKeyCommand {

    private final KeyManager keyManager;
    private final InteractChat interactChat;

    @Command(
            name = "key.lista",
            aliases = {"list"},
            target = CommandTarget.PLAYER,
            usage = "key lista",
            async = true
    )
    public void keyListCommand(Context<Player> sender) {
        Player player = sender.getSender();

        player.sendMessage("");
        player.sendMessage("§7Clique em uma dessas para copiar e poder usa-lo. \n ");
        keyManager.getKeys().forEach(key -> interactChat.sendSuggestCommandText(player, "§2" + key.getKey() + " §f| §aPontos: §2" + key.getAmount(), "§7Clique nessa mensagem para usar", "key ativar " + key.getKey()));
        player.sendMessage("");
    }
}