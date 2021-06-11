package com.serversigma.sigmagems.api;

import com.serversigma.sigmagems.SigmaGems;
import com.serversigma.sigmagems.manager.GemsManager;
import com.serversigma.sigmagems.utilitie.NumberUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.stream.Stream;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class SigmaGemsAPI {

    public static double getGems(Player player) {
        return SigmaGems.instance.getGemsCache().getGems(player.getUniqueId());
    }

    public static String getGemsFormatted(Player player) {
        return NumberUtils.format(getGems(player));
    }

    public static void setGems(Player player, double amount) {
        SigmaGems.instance.getGemsCache().setGems(player.getUniqueId(), amount);
    }

    public static void addGems(Player player, double amount) {
        SigmaGems.instance.getGemsCache().addGems(player.getUniqueId(), amount);
    }

    public static void removeGems(Player player, double amount) {
        SigmaGems.instance.getGemsCache().removeGems(player.getUniqueId(), amount);
    }

    public static Stream<GemsManager.TemporaryUser> getGemsTop() {
        return SigmaGems.instance.getGemsManager().getGemsTop();
    }

}
