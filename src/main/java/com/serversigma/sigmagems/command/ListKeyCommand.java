package com.serversigma.sigmagems.command;

import com.serversigma.sigmagems.manager.KeyManager;
import com.serversigma.sigmagems.utilitie.InteractChat;
import com.serversigma.sigmagems.utilitie.NumberUtils;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class ListKeyCommand {

    private final KeyManager keyManager;
    private final InteractChat interactChat;

    @Command(
            name = "key.lista",
            aliases = {"list"},
            target = CommandTarget.PLAYER,
            usage = "key lista"
    )

    public void keyListCommand(Context<Player> sender) {

        Player player = sender.getSender();

        if  (keyManager.getKeys().count() == 0) {
            player.sendMessage("§cNão há nenhuma chave gerada.");
            return;
        }

        player.sendMessage("");
        player.sendMessage("§7Clique em uma dessas para copiar e poder usa-lo. \n ");

        keyManager.getKeys().forEach(
                key -> interactChat.sendSuggestCommandText
                        (player, "§2" + key.getKey() + " §f| §aGemas: §2" + NumberUtils.format(key.getAmount()),
                                "§7Clique nessa mensagem para usar", "key ativar " + key.getKey()));
        player.sendMessage("");
    }

}