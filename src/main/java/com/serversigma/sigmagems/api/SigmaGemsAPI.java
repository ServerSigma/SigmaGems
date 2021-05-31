package com.serversigma.sigmagems.api;

import com.serversigma.sigmagems.SigmaGems;
import com.serversigma.sigmagems.utilitie.NumberUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class SigmaGemsAPI {

    public static int getGems(Player player) {
        return SigmaGems.instance.getGemsCache().getGems(player.getUniqueId());
    }

    public static String getGemsFormatted(Player player) {
        return NumberUtils.format(getGems(player));
    }

    public static void setGems(Player player, int amount) {
        SigmaGems.instance.getGemsCache().setGems(player.getUniqueId(), amount);
    }

    public static void addGems(Player player, int amount) {
        SigmaGems.instance.getGemsCache().addGems(player.getUniqueId(), amount);
    }

    public static void removeGems(Player player, int amount) {
        SigmaGems.instance.getGemsCache().removeGems(player.getUniqueId(), amount);
    }

}
