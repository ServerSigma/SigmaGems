package com.serversigma.sigmagems.utilitie;

import com.google.common.base.Strings;
import org.bukkit.ChatColor;

import java.util.concurrent.TimeUnit;

public class MessageUtils {


    public String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,
                                 ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

    public String formatTime(long time) {
        if (time == 0L)
            return "never";
        long day = TimeUnit.MILLISECONDS.toDays(time);
        long hours = TimeUnit.MILLISECONDS.toHours(time) - day * 24L;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.MILLISECONDS.toHours(time) * 60L;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MILLISECONDS.toMinutes(time) * 60L;
        StringBuilder sb = new StringBuilder();
        if (day > 0L)
            sb.append(day).append(" ").append((day == 1L) ? "dia" : "dias").append(" ");
        if (hours > 0L)
            sb.append(hours).append(" ").append((hours == 1L) ? "hora" : "horas").append(" ");
        if (minutes > 0L)
            sb.append(minutes).append(" ").append((minutes == 1L) ? "minuto" : "minutos").append(" ");
        if (seconds > 0L)
            sb.append(seconds).append(" ").append((seconds == 1L) ? "segundo" : "segundos");
        String diff = sb.toString();
        return diff.isEmpty() ? "0 segundos" : diff;
    }
}