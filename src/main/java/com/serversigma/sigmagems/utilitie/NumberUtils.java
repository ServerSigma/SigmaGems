package com.serversigma.sigmagems.utilitie;

import java.text.DecimalFormat;

public class NumberUtils {

    public static String format(Number number) {

        char[] suffix = {' ', 'k', 'M', 'B', 'T', 'Q'};
        long numValue = number.longValue();
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return new DecimalFormat("#0.0").format(numValue / Math.pow(10, base * 3)) + suffix[base];
        } else {
            return new DecimalFormat("#,##0").format(numValue);
        }
    }

    public static boolean hasLetter(String data) {
        try {
            Integer.parseInt(data);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

}