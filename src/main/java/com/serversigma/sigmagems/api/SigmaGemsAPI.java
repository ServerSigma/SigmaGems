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
        return SigmaGems.getInstance().getCacheManager().get(player.getUniqueId());
    }

    public static String getGemsFormatted(Player player) {
        return NumberUtils.format(getGems(player));
    }

    public static void setGems(Player player, double amount) {
        SigmaGems.getInstance().getCacheManager().set(player.getUniqueId(), amount);
    }

    public static void addGems(Player player, double amount) {
        SigmaGems.getInstance().getCacheManager().increase(player.getUniqueId(), amount);
    }

    public static void removeGems(Player player, double amount) {
        SigmaGems.getInstance().getCacheManager().take(player.getUniqueId(), amount);
    }

    public static Stream<GemsManager.TemporaryUser> getGemsTop() {
        return SigmaGems.getInstance().getGemsManager().getGemsTop();
    }

}
