package com.serversigma.sigmagems.command;

import com.serversigma.sigmagems.manager.KeyManager;
import com.serversigma.sigmagems.utilitie.InteractChat;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class NewKeyCommand {

    private final KeyManager keyManager;
    private final InteractChat messageUtils;

    @Command(
            name = "key.gerar",
            target = CommandTarget.PLAYER,
            permission = "sigmapoints.admin",
            usage = "key gerar <quantia>"
    )

    public void keyCreate(Context<Player> context, String[] args) {

        Player player = context.getSender();
        if (args.length != 1) {
            player.sendMessage("§cUse: /key gerar <quantia>");
            return;
        }

        if (hasLetter(args[0])) {
            player.sendMessage("§cDigite uma quantia valida.");
            return;
        }
        int amount = Integer.parseInt(args[0]);

        String key = RandomStringUtils.random(20, true, true);
        keyManager.addKey(key, amount);
        player.sendMessage("");
        player.sendMessage("§2[SigmaGemas] §aVocê gerou uma chave. \n ");
        messageUtils.sendSuggestCommandText(player, "           " +
                "§a(Clique AQUI para usar)", "§7Clique nessa mensagem para usar", "key ativar " + key);

        messageUtils.sendSuggestCommandText(player, "           " +
                "§c(Clique AQUI para deletar)", "§7Clique nessa mensagem para deletar", "key deletar " + key);
        player.sendMessage("");

    }

    public boolean hasLetter(String data) {
        try {
            Integer.parseInt(data);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}

