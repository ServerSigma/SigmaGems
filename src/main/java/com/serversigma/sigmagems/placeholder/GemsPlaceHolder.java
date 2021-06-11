package com.serversigma.sigmagems.placeholder;

import com.serversigma.sigmagems.cache.GemsCache;
import com.serversigma.sigmagems.utilitie.NumberUtils;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class GemsPlaceHolder extends PlaceholderExpansion {

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
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        return NumberUtils.format(gemsCache.getGems(player.getUniqueId()));
    }

}