package com.serversigma.sigmagems.placeholder;

import com.serversigma.sigmagems.cache.GemsCache;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

@RequiredArgsConstructor
public class PointsPlaceHolderHook extends PlaceholderExpansion {

    private final Plugin plugin;
    private final GemsCache gemsCache;

    @Override
    public @NotNull String getIdentifier() {
        return plugin.getName();
    }

    @Override
    public @NotNull String getAuthor() {
        return "SigmaNetwork";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if (params.equalsIgnoreCase("gemas")) {
            return format(gemsCache.getGems(player.getUniqueId()));
        }
        return "Placeholder inválida";

    }

    public String format(Number number) {
        char[] suffix = {' ', 'k', 'm', 'b', 't', 'p', 'e'};
        long numValue = number.longValue();
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return new DecimalFormat("#0.0").format(numValue / Math.pow(10, base * 3)) + suffix[base];
        } else {
            return new DecimalFormat("#,##0").format(numValue);
        }
    }
}